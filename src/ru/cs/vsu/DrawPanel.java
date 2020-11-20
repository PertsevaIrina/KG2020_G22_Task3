package ru.cs.vsu;

import ru.cs.vsu.circledrawers.BresenhemCircleDrawer;
import ru.cs.vsu.circledrawers.CircleDrawer;
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
            -4, 4, 8, 8, 600, 600
    );
    private Line xAxis = new Line(-4, 0, 4, 0);
    private Line yAxis = new Line(0, -4, 0, 4);
    private Sun sun = new Sun(2, 2, 1, 2, 20);
//    private Sun sun1 = new Sun(-2, -2, 1, 2, 20);

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
        drawSun(cd, pd, ld, sc, sun);
        drawSquareOfSun(ld, cd, pd);
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
//        drawAxes(ld);
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

    private void drawSun(BresenhemCircleDrawer cd, PixelDrawer pd, LineDrawer ld, ScreenConvertor sc, Sun sun) {
        ScreenPoint o_S = sc.r2s(sun.getO());

        RealPoint rs_R_point = new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY());
        ScreenPoint rs_S_point = sc.r2s(rs_R_point);
        int rs_S = rs_S_point.getX() - o_S.getX();

        RealPoint rr_R_point = new RealPoint(sun.getO().getX() + sun.getrOfRay(), sun.getO().getY());
        ScreenPoint rr_S_point = sc.r2s(rr_R_point);
        int rr_R = rr_S_point.getX() - o_S.getX();

        cd.drawCircle(sc, pd, o_S, rs_S);
//        double rad = 2 * Math.PI / sun.getL();
//        for (int i = 0; i < sun.getL(); i++) {
//            double dx1 = sun.getrOfSun() * Math.sin(rad * i);
//            double dy1 = sun.getrOfSun() * Math.cos(rad * i);
//            double dx2 = sun.getrOfRay() * Math.sin(rad * i);
//            double dy2 = sun.getrOfRay() * Math.cos(rad * i);
//            RealPoint realPoint_x = new RealPoint(dx1 + sun.getO().getX(), dy1 + sun.getO().getY());
//            RealPoint realPoint_y = new RealPoint(dx2 + sun.getO().getX(), dy2 + sun.getO().getY());
//            ld.drawLine(sc.realToScreen(realPoint_x), sc.realToScreen(realPoint_y));
//        }
        cd.fillCircle(sc, ld, o_S, rs_S);

        double rad = 2 * Math.PI / sun.getL();
        for (int i = 0; i < sun.getL(); i++) {
            double dx1 = rs_S * Math.sin(rad * i);
            double dy1 = rs_S * Math.cos(rad * i);
            double dx2 = rr_R * Math.sin(rad * i);
            double dy2 = rr_R * Math.cos(rad * i);
            ScreenPoint screenPoint_x = new ScreenPoint((int) dx1 + o_S.getX(), (int) dy1 + o_S.getY());
            ScreenPoint screenPoint_y = new ScreenPoint((int) dx2 + o_S.getX(), (int) dy2 + o_S.getY());
            ld.drawLine(o_S, screenPoint_y);

        }
    }

    private void drawSquareOfSun(LineDrawer ld, CircleDrawer cd, PixelDrawer pd) {
        if (isSelected) {
            RealPoint p1 = new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun());
            RealPoint p2 = new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun());
            RealPoint p3 = new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun());
            RealPoint p4 = new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun());
            ld.drawLine(sc.r2s(p1), sc.r2s(p2));
            ld.drawLine(sc.r2s(p2), sc.r2s(p4));
            ld.drawLine(sc.r2s(p3), sc.r2s(p4));
            ld.drawLine(sc.r2s(p3), sc.r2s(p1));
            cd.drawCircle(sc, pd, sc.r2s(p1), 5);
            cd.drawCircle(sc, pd, sc.r2s(p2), 5);
            cd.drawCircle(sc, pd, sc.r2s(p3), 5);
            cd.drawCircle(sc, pd, sc.r2s(p4), 5);


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
        if (e.getButton() == MouseEvent.BUTTON1) {
            ScreenPoint screenPoint = new ScreenPoint(e.getX(), e.getY());
            isSelected = !isSelected;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());

        if(clickedMarker != null) {
            RealPoint realCurrent = sc.s2r(current);
            System.out.println(sun.getrOfSun());
            sun.setrOfSun(sun.getrOfSun() - (realCurrent.getX() - clickedMarker.getX())/100);
//            System.out.println(sun.getrOfSun());
        } else {
            moveScreen(e, current, sun);
        }

//        if (currentLine != null) {
//            currentLine.setP2(sc.s2r(current));
//        }
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
//            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(
                    deltaReal.getX() - zeroReal.getX(),
                    deltaReal.getY() - zeroReal.getY()
            );

//            sun.setO(new RealPoint(sun.getO().getX() - vector.getX(),
//                    sun.getO().getY() - vector.getY()));
//            sun.setO(new RealPoint(sun.getO().getX() - vector.getX(), sun.getO().getY() - vector.getY()));
            sc.setX(sun.getO().getX() + vector.getX());
            sc.setY(sun.getO().getY() + vector.getY());
            prevDrag = current;
            repaint();
        }
    }

    private Line currentLine = null;
    private RealPoint clickedMarker = null;

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = new ScreenPoint(e.getX(), e.getY());

        } else if (e.getButton() == MouseEvent.BUTTON1) {
            clickedMarker = getMarker(new ScreenPoint(e.getX(), e.getY()));
            System.out.println(clickedMarker);
//            currentLine = new Line(
//                    sc.s2r(new ScreenPoint(e.getX(), e.getY())),
//                    sc.s2r(new ScreenPoint(e.getX(), e.getY()))
//            );
        }

        repaint();
    }

    private RealPoint getMarker(ScreenPoint screenPoint) {
        ScreenPoint p1S = sc.r2s(new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun()));
        ScreenPoint p2S = sc.r2s(new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun()));
        ScreenPoint p3S = sc.r2s(new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun()));
        ScreenPoint p4S = sc.r2s(new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun()));
        if (Math.abs(p1S.getX() - screenPoint.getX()) < 4 && Math.abs(p1S.getY() - screenPoint.getY()) < 4) {
            return sc.s2r(p1S);
        } else if (Math.abs(p2S.getX() - screenPoint.getX()) < 4 && Math.abs(p2S.getY() - screenPoint.getY()) < 4) {
            return sc.s2r(p2S);
        } else if (Math.abs(p3S.getX() - screenPoint.getX()) < 4 && Math.abs(p3S.getY() - screenPoint.getY()) < 4) {
            return sc.s2r(p3S);
        } else if (Math.abs(p4S.getX() - screenPoint.getX()) < 4 && Math.abs(p4S.getY() - screenPoint.getY()) < 4) {
            return sc.s2r(p4S);
        } else {
            return null;
        }
    }

    //    мышка отпущена
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            clickedMarker = null;
//            lines.add(currentLine);
//            currentLine = null;
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
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            sun.setrOfSun(sun.getrOfSun() / scale);
        }
        repaint();

        sun.setrOfSun(sun.getrOfSun() / scale);
        sun.setrOfRay(sun.getrOfRay() / scale);
//        sc.setW(sc.getW() * scale);
//        sc.setH(sc.getH() * scale);
        repaint();
    }

    private boolean isSelected = false;
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
