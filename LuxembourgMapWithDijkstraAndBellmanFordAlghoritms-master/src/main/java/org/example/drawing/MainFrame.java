package org.example.drawing;

import org.example.Algorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    public List<Node> nodes;
    public List<List<EdgeEnd>> adjLists;
    public List<Integer> path;
    public int mouseX = 0, mouseY = 0;
    public int pathStart = -1, pathEnd = -1;

    public MainFrame() {
        initScreen();
        nodes = new ArrayList<>();
        adjLists = new ArrayList<>();
        path = null;
    }

    public MainFrame(List<Node> nodes, List<List<EdgeEnd>> adjLists) {
        initScreen();
        this.nodes = nodes;
        this.adjLists = adjLists;
        path = null;
    }

    private void initScreen() {

        initFrame();
        CenterPanel centerPanel = initCenterPanel();

        add(centerPanel, BorderLayout.CENTER);
    }

    private void initFrame() {
        this.setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }

    private CenterPanel initCenterPanel() {
        final CenterPanel centerPanel = new CenterPanel(this);

        centerPanel.setBackground(Const.BACKGROUND_COLOR);

        centerPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int nodeClicked = findNode(e.getX(), e.getY(), centerPanel.translation);
                if (nodeClicked == -1) {
                    return;
                }

                if (pathStart == -1 || pathEnd != -1) {
                    pathStart = nodeClicked;
                    pathEnd = -1;
                    path = null;
                } else {
                    pathEnd = nodeClicked;
                    path = Algorithms.Dijkstra(nodes, adjLists, pathStart, pathEnd, false);
                    path = Algorithms.Bellman_Ford(nodes, adjLists, pathStart, pathEnd, false);
                }

                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        centerPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                centerPanel.translation.x += e.getX() - mouseX;
                centerPanel.translation.y += e.getY() - mouseY;

                mouseX = e.getX();
                mouseY = e.getY();

                repaint();
            }
        });

        return centerPanel;
    }

    private int findNode(int x, int y, Point translation) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).mouseInsideNode(x - translation.x, y - translation.y))
                return i;
        }
        return -1;
    }
}

