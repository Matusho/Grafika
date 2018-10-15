package main;

public class Line {
    private Renderer renderer;

    public Line(Renderer renderer) {
        this.renderer = renderer;
    }

    public void drawLineDDA(int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        float k, g, h;

        k = dy / (float) dx;
        if (Math.abs(dx) > Math.abs(dy)) {
            g = 1;
            h = k;
            if (x1 > x2) {
                x1 = x2;
                y1 = y2;
            }
        } else {
            g = 1 / k;
            h = 1;
            if (y1 > y2) {
                x1 = x2;
                y1 = y2;
            }
        }

        float x = x1;
        float y = y1;

        for (int i = 0; i <= Math.max(Math.abs(dx), Math.abs(dy)); i++) {
            renderer.drawPixel(Math.round(x), Math.round(y), color);
            x += g;
            y += h;
        }
    }
}
