package org.example.drawing;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    private final MainFrame frame;
    public Point translation;

    public CenterPanel(MainFrame frame) {
        super();
        this.frame = frame;
        this.translation = new Point(0, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        //g2d.setFont(Const.BOLD_FONT);

        drawAllNodes(g2d);
        ///drawAllArcs(g2d);
        //drawAllLengths(g2d);
        drawPath(g2d);
    }

    public void drawAllNodes(Graphics2D g2d) {
        g2d.setColor(Const.NODE_COLOR);
        for (int i = 0; i < frame.nodes.size(); i++)
            frame.nodes.get(i).drawNode(g2d, translation);
        drawSelectedNodes(g2d);
    }

    public void drawSelectedNodes(Graphics2D g2d) {
        g2d.setColor(Const.SELECTED_NODE_COLOR);
        if(frame.pathStart != -1) {
            frame.nodes.get(frame.pathStart).drawNode(g2d, translation);
        }
        if(frame.pathEnd != -1) {
            frame.nodes.get(frame.pathEnd).drawNode(g2d, translation);
        }
    }

    public void drawAllArcs(Graphics2D g2d) {
        g2d.setColor(Const.ARC_COLOR);
        g2d.setStroke(Const.ARC_STROKE);

        for (int i = 0; i < frame.adjLists.size() && i < Const.MAX_NODES_DRAW; i++) {
            for (int j = 0; j < frame.adjLists.get(i).size()
                    && frame.adjLists.get(i).get(j).end < Const.MAX_NODES_DRAW; j++) {
                drawArc(g2d, frame.nodes.get(i), frame.nodes.get(frame.adjLists.get(i).get(j).end));
            }
        }
    }

    private void drawArc(Graphics2D g2d, Node start, Node end) {
        final double angle = Math.atan2(end.y - start.y, end.x - start.x) - Math.PI;

        final Point retractedStart = new Point(
                (int) (start.x - Const.NODE_RADIUS * Math.cos(angle)),
                (int) (start.y - Const.NODE_RADIUS * Math.sin(angle)));
        final Point retractedEnd = new Point(
                (int) (end.x + Const.NODE_RADIUS * Math.cos(angle)),
                (int) (end.y + Const.NODE_RADIUS * Math.sin(angle)));

        g2d.drawLine(retractedStart.x + translation.x, retractedStart.y + translation.y, retractedEnd.x + translation.x, retractedEnd.y + translation.y);
        //g2d.fillPolygon(makeArrow(retractedEnd, angle));
    }

    private Polygon makeArrow(Point dest, double angle) {
        Polygon arrow = new Polygon();
        Point arrowEnd1 = new Point(
                (int) (dest.x + Const.ARROW_LENGTH * Math.cos(angle - Const.ARROW_ANGLE_OFFSET)),
                (int) (dest.y + Const.ARROW_LENGTH * Math.sin(angle - Const.ARROW_ANGLE_OFFSET)));

        Point arrowEnd2 = new Point(
                (int) (dest.x + Const.ARROW_LENGTH * Math.cos(angle + Const.ARROW_ANGLE_OFFSET)),
                (int) (dest.y + Const.ARROW_LENGTH * Math.sin(angle + Const.ARROW_ANGLE_OFFSET)));

        arrow.addPoint(dest.x, dest.y);
        arrow.addPoint(arrowEnd1.x, arrowEnd1.y);
        arrow.addPoint(arrowEnd2.x, arrowEnd2.y);

        return arrow;
    }

    public void drawAllLengths(Graphics2D g2d) {
        if (frame.adjLists.size() > Const.MAX_LENGTHS_TO_ENABLE_DRAWING)
            return;

        g2d.setColor(Color.BLACK);

        for (int i = 0; i < frame.adjLists.size() && i < 100; i++) {
            for (int j = 0; j < frame.adjLists.get(i).size()
                    && frame.adjLists.get(i).get(j).end < Const.MAX_NODES_DRAW; j++) {
                drawLength(g2d, i, j);
            }
        }
    }

    public void drawLength(Graphics2D g2d, int start, int stop) {
        Node startNode = frame.nodes.get(start);
        Node stopNode = frame.nodes.get(stop);

        int avgX = (startNode.x + stopNode.x) / 2;
        int avgY = (startNode.y + stopNode.y) / 2;

        g2d.drawString(String.valueOf(frame.adjLists.get(start)), avgX, avgY);
    }

    public void drawPath(Graphics2D g2d) {
        if(frame.path == null) {
            return;
        }

        g2d.setColor(Const.NODE_PATH_COLOR);
        for(int i = 0; i < frame.path.size(); i++) {
            Node node = frame.nodes.get(frame.path.get(i));
            node.drawNode(g2d, translation);
        }
    }
}
