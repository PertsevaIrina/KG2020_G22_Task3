package ru.cs.vsu;

//import ru.cs.vsu.circledrawers.BresenhemCircleDrawer;

import java.util.List;

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
    private ScreenConvertor sc = new ScreenConvertor(
            -4, 4, 8, 8, 600, 600
    );
    private Sun sun = new Sun(0, 0, 1, 2, 20);
    private Sun sun1 = new Sun(-2, -2, 1, 2, 20);
    private List<Sun> listOfSuns = new ArrayList<>();

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

        drawSun(cd, pd, ld, sc, sun, bi_g);
        drawSun(cd, pd, ld, sc, sun1, bi_g);
//        for (int i = 0; i <listOfSuns.size() - 1; i++) {
//            drawSun(cd, pd, ld, sc, listOfSuns.get(i), bi_g);
//        }

        drawSquareOfSun(cd, ld, pd);


        if (currentLine != null) {
            drawLine(ld, currentLine);
        }
        bi_g.dispose();
        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()), Color.ORANGE);
    }


    private void drawSun(BresenhemCircleDrawer cd, PixelDrawer pd, LineDrawer ld, ScreenConvertor sc, Sun sun, Graphics2D bi_g) {
        ScreenPoint o_S = sc.r2s(sun.getO());

        RealPoint rs_R_point = new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY());
        ScreenPoint rs_S_point = sc.r2s(rs_R_point);
        int rs_S = rs_S_point.getX() - o_S.getX();

        RealPoint rr_R_point = new RealPoint(sun.getO().getX() + sun.getrOfRay(), sun.getO().getY());
        ScreenPoint rr_S_point = sc.r2s(rr_R_point);
        int rr_R = rr_S_point.getX() - o_S.getX();

        bi_g.setColor(Color.ORANGE);
        bi_g.fillOval(o_S.getX() - rs_S, o_S.getY() - rs_S, rs_S * 2, rs_S * 2);
//        cd.drawCircle(pd, o_S, rs_S, Color.ORANGE);
//        cd.fillCircle(sc, ld, o_S, rs_S, Color.ORANGE);

        List<RealPoint> listPointOfRay = getListPointOfRay(sun);
        for (int i = 0; i < listPointOfRay.size(); i++) {
            ScreenPoint screenPoint_y = new ScreenPoint((int) listPointOfRay.get(i).getX() + o_S.getX(),
                    (int) listPointOfRay.get(i).getY() + o_S.getY());
            ld.drawLine(o_S, screenPoint_y, Color.ORANGE);
        }


    }

    private List getListPointOfRay(Sun sun) {
        ScreenPoint o_S = sc.r2s(sun.getO());

        List<RealPoint> list = new ArrayList<>();

        RealPoint rr_R_point = new RealPoint(sun.getO().getX() + sun.getrOfRay(), sun.getO().getY());
        ScreenPoint rr_S_point = sc.r2s(rr_R_point);
        int rr_R = rr_S_point.getX() - o_S.getX();

        double rad = 2 * Math.PI / sun.getL();
        for (int i = 0; i < sun.getL(); i++) {
            double dx2 = rr_R * Math.sin(rad * i);
            double dy2 = rr_R * Math.cos(rad * i);
            list.add(new RealPoint(dx2, dy2));
        }
        return list;
    }

    private void drawSquareOfSun(BresenhemCircleDrawer cd, LineDrawer ld, PixelDrawer pd) {
        if (isSelected) {
            RealPoint p1 = new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun());
            RealPoint p2 = new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun());
            RealPoint p3 = new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun());
            RealPoint p4 = new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun());
            ld.drawLine(sc.r2s(p1), sc.r2s(p2), Color.GRAY);
            ld.drawLine(sc.r2s(p2), sc.r2s(p4), Color.GRAY);
            ld.drawLine(sc.r2s(p3), sc.r2s(p4), Color.GRAY);
            ld.drawLine(sc.r2s(p3), sc.r2s(p1), Color.GRAY);

            cd.drawCircle(pd, sc.r2s(p1), 5, Color.BLUE);
            cd.drawCircle(pd, sc.r2s(p2), 5, Color.BLUE);
            cd.drawCircle(pd, sc.r2s(p3), 5, Color.BLUE);
            cd.drawCircle(pd, sc.r2s(p4), 5, Color.BLUE);

            cd.drawCircle(pd, sc.r2s(sun.getO()), 8, Color.BLUE);

        }
    }

    private Line currentLine = null;
    private RealPoint clickedMarker = null;
    private int markerId = -1;
    private boolean isSelected = false;
    private ScreenPoint prevDrag;

    @Override
    public void mouseMoved(MouseEvent e) {
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            isSelected = !isSelected;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        RealPoint realCurrent = sc.s2r(current);
        ScreenPoint oS = sc.r2s(sun.getO());
        RealPoint center;
        if (markerId == 0) {
            center = sc.s2r(new ScreenPoint(e.getXOnScreen() - 7,
                    e.getYOnScreen() - 28));
            sun.setO(center.getX(), center.getY());
        } else if (markerId == 1) {
            sun.setrOfSun(Math.sqrt(Math.pow(sun.getO().getX() - realCurrent.getX(), 2)
                    + Math.pow(sun.getO().getY() - realCurrent.getY(), 2)) - 9 * sun.getrOfSun() / 20);
        } else {
            moveScreen(e, current);
        }
        repaint();
    }

    //    мышка отпущена

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            clickedMarker = null;
            markerId = -1;
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = new ScreenPoint(e.getX(), e.getY());


        } else if (e.getButton() == MouseEvent.BUTTON1) {
            clickedMarker = getMarker(new ScreenPoint(e.getX(), e.getY()));
            markerId = getMarkerId(new ScreenPoint(e.getX(), e.getY()));
        }

        repaint();

    }

    private void moveScreen(MouseEvent e, ScreenPoint current) {
        ScreenPoint delta;
        if (prevDrag != null) {
            delta = new ScreenPoint(
                    current.getX() - prevDrag.getX(),
                    current.getY() - prevDrag.getY());


            RealPoint deltaReal = sc.s2r(delta);
            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(
                    deltaReal.getX() - zeroReal.getX(),
                    deltaReal.getY() - zeroReal.getY()
            );
            sc.setX(sc.getX() + vector.getX());
            sc.setY(sc.getY() + vector.getY());
            prevDrag = current;
            repaint();
        }
    }


    private int getMarkerId(ScreenPoint screenPoint) {
        ScreenPoint p1S = sc.r2s(new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun()));
        ScreenPoint p2S = sc.r2s(new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun()));
        ScreenPoint p3S = sc.r2s(new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun()));
        ScreenPoint p4S = sc.r2s(new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun()));

        ScreenPoint oScreen = sc.r2s(sun.getO());

        if (Math.abs(p1S.getX() - screenPoint.getX()) < 4 && Math.abs(p1S.getY() - screenPoint.getY()) < 4) {
            return 1;
        } else if (Math.abs(p2S.getX() - screenPoint.getX()) < 4 && Math.abs(p2S.getY() - screenPoint.getY()) < 4) {
            return 1;
        } else if (Math.abs(p3S.getX() - screenPoint.getX()) < 4 && Math.abs(p3S.getY() - screenPoint.getY()) < 4) {
            return 1;
        } else if (Math.abs(p4S.getX() - screenPoint.getX()) < 4 && Math.abs(p4S.getY() - screenPoint.getY()) < 4) {
            return 1;
        } else if (Math.abs(oScreen.getX() - screenPoint.getX()) < 4 && Math.abs(oScreen.getY() - screenPoint.getY()) < 4) {
            return 0;
        } else return -1;
    }

    private RealPoint getMarker(ScreenPoint screenPoint) {
        ScreenPoint p1S = sc.r2s(new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun()));
        ScreenPoint p2S = sc.r2s(new RealPoint(sun.getO().getX() - sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun()));
        ScreenPoint p3S = sc.r2s(new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() - sun.getrOfSun()));
        ScreenPoint p4S = sc.r2s(new RealPoint(sun.getO().getX() + sun.getrOfSun(), sun.getO().getY() + sun.getrOfSun()));

        ScreenPoint oScreen = sc.r2s(sun.getO());

        if (Math.abs(p1S.getX() - screenPoint.getX()) < 4 && Math.abs(p1S.getY() - screenPoint.getY()) < 4) {
            return sc.s2r(p1S);
        } else if (Math.abs(p2S.getX() - screenPoint.getX()) < 4 && Math.abs(p2S.getY() - screenPoint.getY()) < 4) {
            return sc.s2r(p2S);
        } else if (Math.abs(p3S.getX() - screenPoint.getX()) < 4 && Math.abs(p3S.getY() - screenPoint.getY()) < 4) {
            return sc.s2r(p3S);
        } else if (Math.abs(p4S.getX() - screenPoint.getX()) < 4 && Math.abs(p4S.getY() - screenPoint.getY()) < 4) {
            return sc.s2r(p4S);
        } else if (Math.abs(oScreen.getX() - screenPoint.getX()) < 10 && Math.abs(oScreen.getY() - screenPoint.getY()) < 10) {
            return sc.s2r(oScreen);
        } else return null;
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


}
