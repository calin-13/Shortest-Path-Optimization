package org.example.drawing;

public class EdgeEnd {
    public int end, length;

    public EdgeEnd() {
        this.end = 0;
        this.length = 1;
    }

    public EdgeEnd(int end, int length) {
        this.end = end;
        this.length = length;
    }

    @Override
    public String toString() {
        return "EdgeEnd{" +
                "end=" + end +
                ", length=" + length +
                '}';
    }
}
