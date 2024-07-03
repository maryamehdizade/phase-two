package controller;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public class Constant {
    public static final int FPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
    public static final double FRAME_UPDATE_TIME=(double) TimeUnit.SECONDS.toMillis(1)/FPS;
    public static final int UPS = 100;
    public static final double MODEL_UPDATE_TIME=(double) TimeUnit.SECONDS.toMillis(1)/UPS;
    public static final Dimension FRAME_DIMENSION = new Dimension(800,800);
    public static final Dimension MAX_SIZE = new Dimension(800,800);
    public static final Dimension MIN_SIZE = new Dimension(300,300);
    public static final Point2D FRAME_LOCATON = new Point2D.Double(10,10);

    public static final int BALL_SIZE = 20;
    public static final int BULLET_SIZE = 8;
    public static final int RECT_SIZE = 30;
    public static final int TRI_SIZE = 40;
    public static final int COLLECTABLE_SIZE = 8;

}
