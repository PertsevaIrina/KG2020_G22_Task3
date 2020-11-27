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


    @Override
    public void drawCircle(PixelDrawer pd, ScreenPoint o, int rOfSun, Color c) {
        int x1 = rOfSun;
        int y1 = 0;
        int radiusError = 1;
        while (x1 >= y1) {
            pd.setPixel(x1 + o.getX(), y1 + o.getY(), c);
            pd.setPixel(y1 + o.getX(), x1 + o.getY(), c);
            pd.setPixel(-x1 + o.getX(), y1 + o.getY(), c);
            pd.setPixel(-y1 + o.getX(), x1 + o.getY(), c);
            pd.setPixel(-x1 + o.getX(), -y1 + o.getY(), c);
            pd.setPixel(-y1 + o.getX(), -x1 + o.getY(), c);
            pd.setPixel(x1 + o.getX(), -y1 + o.getY(), c);
            pd.setPixel(y1 + o.getX(), -x1 + o.getY(), c);
            y1++;
            if (radiusError < 0) {
                radiusError += 2 * y1 + 1;
            } else {
                x1--;
                radiusError += 2 * (y1 - x1 + 1);
            }
        }
    }


    public void fillCircle(ScreenConvertor sc, LineDrawer ld, ScreenPoint o, int rOfSun, Color c) {


        double step = sc.getH() / 40000;
        for (double i = 0; i < 2 * Math.PI; i += step) {
            double x = o.getX() + rOfSun * Math.cos(i);
            double y = o.getY() + rOfSun * Math.sin(i);
            ScreenPoint p = new ScreenPoint((int) x, (int) y);
            ld.drawLine(p, o, c);
            //        int x1 = rOfSun;
//        int y1 = 0;
//        int radiusError = 1;
//        while (x1 >= y1) {
////            ScreenPoint p11 = sc.r2s(new RealPoint(x1 + o.getX(), y1 + o.getY()));
////            ScreenPoint p12 = sc.r2s(new RealPoint(-x1 + o.getX(), y1 + o.getY()));
////            ScreenPoint p21 = sc.r2s(new RealPoint(y1 + o.getX(), x1 + o.getY()));
////            ScreenPoint p22 = sc.r2s(new RealPoint(-y1 + o.getX(), x1 + o.getY()));
////            ScreenPoint p31 = sc.r2s(new RealPoint(-x1 + o.getX(), -y1 + o.getY()));
////            ScreenPoint p32 = sc.r2s(new RealPoint(x1 + o.getX(), -y1 + o.getY()));
////            ScreenPoint p41 = sc.r2s(new RealPoint(-y1 + o.getX(), -x1 + o.getY()));
////            ScreenPoint p42 = sc.r2s(new RealPoint(y1 + o.getX(), -x1 + o.getY()));
//            ld.drawLine(sc.r2s(new RealPoint(x1 + o.getX(), y1 + o.getY())),sc.r2s(new RealPoint(-x1 + o.getX(), y1 + o.getY())),Color.ORANGE);
//            ld.drawLine(sc.r2s(new RealPoint(y1 + o.getX(), x1 + o.getY())),sc.r2s(new RealPoint(-y1 + o.getX(), x1 + o.getY())),Color.ORANGE);
//            ld.drawLine(sc.r2s(new RealPoint(-x1 + o.getX(), -y1 + o.getY())),sc.r2s(new RealPoint(x1 + o.getX(), -y1 + o.getY())),Color.ORANGE);
//            ld.drawLine(sc.r2s(new RealPoint(-y1 + o.getX(), -x1 + o.getY())),sc.r2s(new RealPoint(y1 + o.getX(), -x1 + o.getY())),Color.ORANGE);
//
//            y1++;
//            if (radiusError < 0) {
//                radiusError += 2 * y1 + 1;
//            } else {
//                x1--;
//                radiusError += 2 * (y1 - x1 + 1);
//            }
//        }
        }
    }}
//}
////    @Override
////    public void drawCircle(ScreenConvertor sc, ScreenPoint o, int rOfSun) {
////        double step = sc.getH() / 40000;
////        for (double i = 0; i < 2 * Math.PI; i += step) {
////            double x = o.getX() + rOfSun * Math.cos(i);
////            double y = o.getY() + rOfSun * Math.sin(i);
//////            int intx = (int) x;
//////            intx += x - intx > 0.5 ? 1 : 0;
//////            int inty = (int) y;
//////            intx += y - inty > 0.5 ? 1 : 0;
//////            ScreenPoint p = sc.r2s(new RealPoint(x, y));
////            pd.setPixel((int) x, (int) y, Color.ORANGE);
////
////        }
////    }
