package org.example.drawing;

import java.awt.*;

public class Node {
    public int x;
    public int y;
    public int id;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = -1;
    }

    public Node(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void drawNode(Graphics2D g2d) {
        int diam = 2 * Const.NODE_RADIUS;
        g2d.fillRoundRect(x - Const.NODE_RADIUS, y - Const.NODE_RADIUS,
                diam, diam, diam, diam);
    }

    public void drawNode(Graphics2D g2d, Point translation) {
        int diam = 2 * Const.NODE_RADIUS;
        g2d.fillRoundRect(x - Const.NODE_RADIUS + translation.x, y - Const.NODE_RADIUS + translation.y,
                diam, diam, diam, diam);
    }

    public boolean mouseInsideNode(int x, int y) {
        return Math.abs(this.x - x) < Const.NODE_RADIUS
                && Math.abs(this.y - y) < Const.NODE_RADIUS;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }
}
