package ru.cs.vsu;

import ru.cs.vsu.circledrawers.BresenhemCircleDrawer;
import ru.cs.vsu.linedrawers.DDALineDrawer;
import ru.cs.vsu.linedrawers.LineDrawer;
import ru.cs.vsu.models.Line;
import ru.cs.vsu.models.Sun;
import ru.cs.vsu.pixeldrawers.BufferedImagePixelDrawer;
import ru.cs.vsu.pixeldrawers.PixelDrawer;
import ru.cs.vsu.points.RealPoint;
import ru.cs.vsu.points.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConvertor sc = new ScreenConvertor(
            -2, 2, 4, 4, 800, 600
    );
    private Line xAxis = new Line(-2, 0, 2, 0);
    private Line yAxis = new Line(0, -4, 0, 4);
    private Sun sun = new Sun(0, 0, 1, 1, 20);

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D bi_g = bi.createGraphics();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        BresenhemCircleDrawer cd = new BresenhemCircleDrawer(pd);
        sc.setScreenH(getHeight());
        sc.setScreenW(getWidth());
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        bi_g.setColor(Color.black);
//        drawAxes(ld);
//        drawAll(ld);
        drawSun(cd, ld, sc, sun);
        if (currentLine != null) {
            drawLine(ld, currentLine);
        }
        bi_g.dispose();
        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawAll(LineDrawer ld) {
//        int w = this.getWidth()/2;
//        int h = this.getHeight()/2;
//
//        Graphics2D g1 = (Graphics2D) g;
//        g1.setStroke(new BasicStroke(2));
//        g1.setColor(Color.black);
//        g1.drawLine(0,h,w*2,h);
//        g1.drawLine(w,0,w,h*2);
//        g1.drawString("0", w - 7, h + 13);
//
//        int scale = 10;
//        for (int x = 0; x <= 4; x++) {
//            p.addPoint(w+scale*x, h - scale*((x*x*x) + x - 3));
//        }
////...lines skipped
//        Polygon p1 = new Polygon();
//        for (int x = -10; x <= 10; x++) {
//            p1.addPoint(w + scale*x, h - scale*((x*x*x)/100) - x + 10);
//        }
//
        drawAxes(ld);
    }
   /* public void drawSun(Graphics gr, Color color, int x, int y, int l, int r1, int r2) {
        gr.setColor(color);
        gr.fillOval(x - r1, y - r1, 2 * r1, 2 * r1);
        double rad = 2 * Math.PI / l;
        for (int i = 0; i < l; i++) {
            double dx1 = r1 * Math.sin(rad * i);
            double dy1 = r1 * Math.cos(rad * i);
            double dy2 = r2 * Math.cos(rad * i);
            gr.drawLine((int) dx1 + x, (int) dy1 + y, (int) dx2 + x, (int) dy2 + y);
        }
    }*/

    private void drawSun(BresenhemCircleDrawer cd, LineDrawer ld, ScreenConvertor sc, Sun sun) {
        ScreenPoint o_S = sc.r2s(sun.getO());
        RealPoint r_R_point = new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY());
        ScreenPoint r_S_point = sc.r2s(r_R_point);
        int r_S = r_S_point.getX() - o_S.getX();

        cd.drawCircle(sc, o_S, r_S);
        cd.fillCircle(sc, ld, o_S, r_S);

        double rad = 2 * Math.PI / sun.getL();
        for (int i = 0; i < sun.getL(); i++) {
            double dx1 = o_S.getX() * Math.sin(rad * i);
            double dy1 = o_S.getY() * Math.cos(rad * i);
            double dx2 = r_S * Math.sin(rad * i);
            double dy2 = r_S * Math.cos(rad * i);
            ScreenPoint screenPoint_x = new ScreenPoint((int) dx1 + o_S.getX(), (int) dy1 + o_S.getY());
            ScreenPoint screenPoint_y = new ScreenPoint((int) dx2 + o_S.getX(), (int) dy2 + o_S.getY());
            ld.drawLine(screenPoint_x, screenPoint_y);

        }
    }

    private void drawAxes(LineDrawer ld) {
        ld.drawLine(sc.realToScreen(xAxis.getP1()), sc.realToScreen(xAxis.getP2()));
        ld.drawLine(sc.realToScreen(yAxis.getP1()), sc.realToScreen(yAxis.getP2()));
        for (Line l : lines) {
            ld.drawLine(sc.realToScreen(l.getP1()),
                    sc.realToScreen(l.getP2()));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private ScreenPoint prevDrag;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        moveScreen(e, current, sun);
        if (currentLine != null) {
            currentLine.setP2(sc.s2r(current));
        }
        repaint();
    }

    private void moveScreen(MouseEvent e, ScreenPoint current, Sun sun) {
        ScreenPoint delta;
        ScreenPoint o_S = sc.r2s(sun.getO());
        if (prevDrag != null) {
            delta = new ScreenPoint(
                    current.getX() - prevDrag.getX(),
                    current.getY() - prevDrag.getY());


            RealPoint deltaReal = sc.s2r(delta);
            RealPoint zeroReal = sc.s2r(o_S);
            RealPoint vector = new RealPoint(
                    deltaReal.getX() - zeroReal.getX(),
                    deltaReal.getY() - zeroReal.getY()
            );

//            sun.setO(new RealPoint(sun.getO().getX() - vector.getX(),
//                    sun.getO().getY() - vector.getY()));
            sun.setO(new RealPoint(sun.getO().getX() - vector.getX(), sun.getO().getY() - vector.getY()));
            sc.setY(sc.getY() - vector.getY());
            prevDrag = current;
            repaint();
        }
    }

    private Line currentLine = null;

    @Override
    public void mousePressed(MouseEvent e) {
        ScreenPoint o_S = sc.r2s(sun.getO());
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = new ScreenPoint(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            currentLine = new Line(
                    sc.s2r(new ScreenPoint(e.getX(), e.getY())),
                    sc.s2r(new ScreenPoint(e.getX(), e.getY()))
            );
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            lines.add(currentLine);
            currentLine = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks <= 0 ? 0.9 : 1.1;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        sc.setW(sc.getW() * scale);
        sc.setH(sc.getH() * scale);
        repaint();
    }
//    @Override
//    public void mouseWheelMoved(MouseWheelEvent e) {
//        int clicks = e.getWheelRotation();
//        double scale = 1;
//        double coef = clicks > 0 ? 0.9 : 1.1;
//        for (int i = 0; i < Math.abs(clicks); i++) {
//            scale *= coef;
//        }
//        sc.setW(sc.getW() * scale);
//        sc.setH(sc.getH() * scale);
//        double r = (sun.getrOfSun() / scale);
//            sun.setrOfSun(r);
//        repaint();
//    }

}
