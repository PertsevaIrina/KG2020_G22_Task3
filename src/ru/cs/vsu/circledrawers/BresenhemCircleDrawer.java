package ru.cs.vsu.circledrawers;

import ru.cs.vsu.ScreenConvertor;
import ru.cs.vsu.linedrawers.DDALineDrawer;
import ru.cs.vsu.linedrawers.LineDrawer;
import ru.cs.vsu.pixeldrawers.PixelDrawer;
import ru.cs.vsu.points.RealPoint;
import ru.cs.vsu.points.ScreenPoint;

import java.awt.*;

public class BresenhemCircleDrawer implements CircleDrawer {
    private PixelDrawer pd;

    public BresenhemCircleDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    //    @Override
//        public void drawCircle(ScreenConvertor sc,RealPoint o, double rOfSun) {
//            double x1 = 0, y1 = rOfSun, gap = 0, delta = (2 - 2 * rOfSun);
//            while (y1 >= 0) {
//                RealPoint realPoint1 = new RealPoint(o.getX() + x1, o.getY() + y1);
//                RealPoint realPoint2 = new RealPoint(o.getX() + x1, o.getY() - y1);
//                RealPoint realPoint3 = new RealPoint(o.getX() - x1, o.getY() - y1);
//                RealPoint realPoint4 = new RealPoint(o.getX() - x1, o.getY() + y1);
//                pd.setPixel(sc.realToScreen(realPoint1).getX(),sc.realToScreen(realPoint1).getY(), Color.ORANGE);
//                pd.setPixel(sc.realToScreen(realPoint2).getX(),sc.realToScreen(realPoint2).getY(), Color.ORANGE);
//                pd.setPixel(sc.realToScreen(realPoint3).getX(),sc.realToScreen(realPoint3).getY(), Color.ORANGE);
//                pd.setPixel(sc.realToScreen(realPoint4).getX(),sc.realToScreen(realPoint4).getY(), Color.ORANGE);
//                gap = 2 * (delta + y1) - 1;
//                if (delta < 0 && gap <= 0) {
//                    x1++;
//                    delta += 2 * x1 + 1;
//                    continue;
//                }
//                if (delta > 0 && gap > 0) {
//                    y1--;
//                    delta -= 2 * y1 + 1;
//                    continue;
//                }
//                x1++;
//                delta += 2 * (x1 - y1);
//                y1--;
//            }
//        }

    @Override
    public void drawCircle(ScreenConvertor sc, PixelDrawer pd, ScreenPoint o, int rOfSun) {
        int x1 = rOfSun;
        int y1 = 0;
        int radiusError = 1;
        while (x1 >= y1) {
            pd.setPixel(x1 + o.getX(), y1 + o.getY(), Color.BLACK);
            pd.setPixel(y1 + o.getX(), x1 + o.getY(), Color.BLACK);
            pd.setPixel(-x1 + o.getX(), y1 + o.getY(), Color.BLACK);
            pd.setPixel(-y1 + o.getX(), x1 + o.getY(), Color.BLACK);
            pd.setPixel(-x1 + o.getX(), -y1 + o.getY(), Color.BLACK);
            pd.setPixel(-y1 + o.getX(), -x1 + o.getY(), Color.BLACK);
            pd.setPixel(x1 + o.getX(), -y1 + o.getY(), Color.BLACK);
            pd.setPixel(y1 + o.getX(), -x1 + o.getY(), Color.BLACK);
            y1++;
            if (radiusError < 0) {
                radiusError += 2 * y1 + 1;
            } else {
                x1--;
                radiusError += 2 * (y1 - x1 + 1);
            }
        }
    }

//    @Override
//    public void drawCircle(ScreenConvertor sc, ScreenPoint o, int rOfSun) {
//        double step = sc.getH() / 40000;
//        for (double i = 0; i < 2 * Math.PI; i += step) {
//            double x = o.getX() + rOfSun * Math.cos(i);
//            double y = o.getY() + rOfSun * Math.sin(i);
////            int intx = (int) x;
////            intx += x - intx > 0.5 ? 1 : 0;
////            int inty = (int) y;
////            intx += y - inty > 0.5 ? 1 : 0;
////            ScreenPoint p = sc.r2s(new RealPoint(x, y));
//            pd.setPixel((int) x, (int) y, Color.ORANGE);
//
//        }
//    }

    public void fillCircle(ScreenConvertor sc, LineDrawer ld, ScreenPoint o, int rOfSun) {
        double step = sc.getH() / 40000;
        for (double i = 0; i < 2 * Math.PI; i += step) {
            double x = o.getX() + rOfSun * Math.cos(i);
            double y = o.getY() + rOfSun * Math.sin(i);
//            int intx = (int) x;
//            intx += x - intx > 0.5 ? 1 : 0;
//            int inty = (int) y;
//            intx += y - inty > 0.5 ? 1 : 0;
            ScreenPoint p = new ScreenPoint((int) x, (int) y);
            ld.drawLine(p, o);
        }
    }
}
