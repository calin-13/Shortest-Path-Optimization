package org.example.drawing;

public class Edge extends EdgeEnd {
    public int start;

    public Edge() {
        super();
        this.start = 0;
    }

    public Edge(int start, int end, int length) {
        super(end, length);
        this.start = start;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", end=" + end +
                ", length=" + length +
                '}';
    }
}
