package org.example.drawing;

import java.awt.*;

public class Const {
    public static final int MAX_NODES_DRAW = 1000000;
    public static final int MAX_LENGTHS_TO_ENABLE_DRAWING = 20;
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 800;
    public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;
    public static final Color NODE_COLOR = Color.MAGENTA;
    public static final Color NODE_PATH_COLOR = Color.CYAN;
    public static final Color SELECTED_NODE_COLOR = Color.BLUE;
    public static final Color ARC_COLOR = Color.BLACK;
    public static final int NODE_RADIUS = 5;
    public static final float ARC_WIDTH = 1f;
    public static final Stroke ARC_STROKE = new BasicStroke(Const.ARC_WIDTH);
    public static final int ARROW_LENGTH = 10;
    public static final double ARROW_ANGLE_OFFSET = Math.PI / 8;
    public static final Font BOLD_FONT = new Font(Font.MONOSPACED, Font.BOLD, 20);
}
