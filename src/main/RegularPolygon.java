package main;

public class RegularPolygon {
    private Line line;

    public RegularPolygon(Line line) {
        this.line = line;
    }

    public void drawRegPoly(int x1, int y1, int x2, int y2, int sides) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double radius = 2 * Math.PI;
        for (double i = 0; i < radius; i += radius / (double) sides) {
            //otaceni bodu
            double x = dx * Math.cos(radius / (double) sides) + dy * Math.sin(radius / (double) sides);
            double y = dy * Math.cos(radius / (double) sides) - dx * Math.sin(radius / (double) sides);
            line.drawLineDDA((int) dx + x1, (int) dy + y1, (int) x + x1, (int) y + y1, 0x00ff00);
            dx = x;
            dy = y;
        }
    }


}
