package main;

public class Circle {
    private Line line;

    public Circle(Line line) {
        this.line = line;
    }

    public void drawCircle(int x1, int y1, double radius, double angle1, double angle2, int color) {
        double a1 = angle1;
        double a2 = angle2;
        if (angle2 < angle1) {
            a2 = angle2 + 360.0D;
        }
        double x = x1 + radius * Math.cos(Math.toRadians(a1));
        double y = y1 + radius * Math.sin(Math.toRadians(a1));
        double xo = x;
        double yo = y;
        double step = 10.0D;
        for (; a1 + step < a2; yo = y) {
            a1 += step;
            x = x1 + radius * Math.cos(Math.toRadians(a1));
            y = y1 + radius * Math.sin(Math.toRadians(a1));
            line.drawLineDDA((int) xo, (int) yo, (int) x, (int) y, color);
            xo = x;
        }
        x = x1 + radius * Math.cos(Math.toRadians(a2));
        y = y1 + radius * Math.sin(Math.toRadians(a2));
        line.drawLineDDA((int) xo, (int) yo, (int) x, (int) y, color);
    }
}
