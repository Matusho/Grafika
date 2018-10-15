package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class App {
    private JFrame window;
    private BufferedImage img; // objekt pro zápis pixelů
    private Canvas canvas; // plátno pro vykreslení BufferedImage
    private Renderer renderer;
    private Line line;
    private Polygon polygon;
    private RegularPolygon regPoly;
    private List<Point> points = new ArrayList<>();
    private List<Point> regPoints = new ArrayList<>();
    private int x, y;
    private int clicks = 1;
    private int sides = 3;
    private boolean sideCheck = false;


    public App() {
        window = new JFrame();
        // bez tohoto nastavení se okno zavře, ale aplikace stále běží na pozadí

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(800, 600); // velikost okna
        window.setLocationRelativeTo(null);// vycentrovat okno
        window.setTitle("PGRF1 cvičení"); // titulek okna
        window.setResizable(false);
        window.requestFocus();

        // inicializace image, nastavení rozměrů (nastavení typu - pro nás nedůležité)
        img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        // inicializace plátna, do kterého budeme kreslit img
        canvas = new Canvas();

        window.add(canvas); // vložit plátno do okna
        window.setVisible(true); // zobrazit okno

        img.getGraphics().drawString("Left click to draw irregular polygon.", 10, img.getHeight() - 65);
        img.getGraphics().drawString("Right click to set center of regular polygon, again for radius and scroll wheel to set number of sides, click again to draw selected polygon.", 10, img.getHeight() - 50);
        img.getGraphics().drawString("Press Backspace to clear and start again and escape to exit.", 10, img.getHeight() - 35);

        renderer = new Renderer(img, canvas);
        line = new Line(renderer);
        polygon = new Polygon(renderer, line);
        regPoly = new RegularPolygon(line);


        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    renderer.clear();
                    points.clear();
                    regPoints.clear();
                    clicks = 1;//vrati pocitadlo kliku zpet na puvodni hodnotu aby nenastaval index out of bounds
                    sides = 3;
                }
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    points.add(new Point(e.getX(), e.getY())); //prida bod do listu
                    polygon.drawPolygon(points);//nakresli n-uhlenik
                    regPoints.clear();
                    clicks = 1;//vrati pocitadlo kliku zpet na puvodni hodnotu aby nenastaval index out of bounds
                    sides = 3;
                }
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (points.size() > 0) {
                        polygon.drawPolygon(points);//kresli n-uhelnik aby nezmizel pri kresleni pomocne cary
                        //nahledova cara
                        line.drawLineDDA((int) points.get(points.size() - 1).getX(), (int) points.get(points.size() - 1).getY(), e.getX(), e.getY(), 0x00bfff);
                        line.drawLineDDA((int) points.get(0).getX(), (int) points.get(0).getY(), e.getX(), e.getY(), 0x00bfff);
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) { //kresli n-uhlnik pri pohybu mysi kolem dokola
                if (clicks == 2) {
                    x = e.getX();
                    y = e.getY();
                    renderer.clear();
                    line.drawLineDDA((int) regPoints.get(0).getX(), (int) regPoints.get(0).getY(), x, y, 0xff0000);//kresli caru ktera ukazuje radius
                    regPoly.drawRegPoly((int) regPoints.get(0).getX(), (int) regPoints.get(0).getY(), x, y, sides);//nahled n-uhelniku kdyz se nastavuje radius
                }
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    renderer.clear();
                    if (clicks == 1) {//nastavi stred
                        x = e.getX();
                        y = e.getY();
                        regPoints.add(new Point(x, y));
                        clicks += 1;
                        sideCheck = true;
                    } else if (clicks == 2) {//nastavi radius
                        x = e.getX();
                        y = e.getY();
                        line.drawLineDDA((int) regPoints.get(0).getX(), (int) regPoints.get(0).getY(), x, y, 0xff0000);//kresli caru ktera ukazuje radius
                        regPoly.drawRegPoly((int) regPoints.get(0).getX(), (int) regPoints.get(0).getY(), x, y, sides);//kresli "nahled" n-uhelniku
                        clicks += 1;
                    } else if (clicks == 3) {//nakresli n-uhelnik
                        regPoly.drawRegPoly((int) regPoints.get(0).getX(), (int) regPoints.get(0).getY(), x, y, sides);//nakresli finalni n-uhelnik
                        clicks = 1;//vrati pocitadlo kliku zpet na puvodni hodnotu aby nenastaval index out of bounds
                        sides = 3;//nastaví pocet stran n-uhelniku zpet na 3 aby se neobjevoval posledne vykresleny
                    }
                }
            }
        });

        canvas.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0 && sideCheck == true && clicks == 3) { //nastavuje pocet stran n-uhleniku
                    if (sides >= 3) {
                        sides++;//pridava strany
                        //cisti platno a prekresluje n-uhelnik
                        renderer.clear();
                        line.drawLineDDA((int) regPoints.get(0).getX(), (int) regPoints.get(0).getY(), x, y, 0xff0000);
                        regPoly.drawRegPoly((int) regPoints.get(0).getX(), (int) regPoints.get(0).getY(), x, y, sides);
                    }
                } else {
                    if (sides > 3) {
                        sides--;//ubira strany
                        renderer.clear();
                        line.drawLineDDA((int) regPoints.get(0).getX(), (int) regPoints.get(0).getY(), x, y, 0xff0000);
                        regPoly.drawRegPoly((int) regPoints.get(0).getX(), (int) regPoints.get(0).getY(), x, y, sides);
                    }
                }
            }
        });

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}

