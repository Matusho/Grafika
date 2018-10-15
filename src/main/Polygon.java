package main;

import java.awt.*;
import java.util.List;

public class Polygon {
    private Renderer renderer;
    private Line line;

    public Polygon(Renderer renderer, Line line) {
        this.renderer = renderer;
        this.line = line;
    }

    public void drawPolygon(List<Point> points) {
        renderer.clear();
        for (int i = 0; i < points.size() - 1; i++) {
            line.drawLineDDA((int) points.get(i).getX(), (int) points.get(i).getY(), (int) points.get(i + 1).getX(), (int) points.get(i + 1).getY(), 0xffffff); //kresli caru mezi body s indexem i a i+1
        }
        line.drawLineDDA((int) points.get(0).getX(), (int) points.get(0).getY(), (int) points.get(points.size() - 1).getX(), (int) points.get(points.size() - 1).getY(), 0xffffff); //spojeni prvniho a posledniho bodu
    }
}
