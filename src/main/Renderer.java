package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Renderer {
    private BufferedImage img;
    private Canvas canvas;
    private static final int FPS = 1000 / 30;

    public Renderer(BufferedImage img, Canvas canvas) {
        this.img = img;
        this.canvas = canvas;
        setTimer();
    }

    private void setTimer() {
        // časovač, který 30 krát za vteřinu obnoví obsah plátna aktuálním img
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // říct plátnu, aby zobrazil aktuální img
                canvas.getGraphics().drawImage(img, 0, 0, null);
            }
        }, 0, FPS);
    }

    public void clear() {
        Graphics g = img.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        img.getGraphics().drawString("Left click to draw irregular polygon.", 10, img.getHeight() - 65);
        img.getGraphics().drawString("Right click to set center of regular polygon, again for radius and scroll wheel to set number of sides, click again to draw selected polygon.", 10, img.getHeight() - 50);
        img.getGraphics().drawString("Press Backspace to clear and start again and escape to exit.", 10, img.getHeight() - 35);
    }

    public void drawPixel(int x, int y, int color) {
        // nastavit pixel do img
        if (x > 0 && x < img.getWidth() && y > 0 && y < img.getHeight()) {
            img.setRGB(x, y, color);
        }
    }
}
