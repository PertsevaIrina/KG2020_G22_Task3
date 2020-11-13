package ru.cs.vsu.circledrawers;

import ru.cs.vsu.ScreenConvertor;
import ru.cs.vsu.linedrawers.DDALineDrawer;
import ru.cs.vsu.pixeldrawers.PixelDrawer;
import ru.cs.vsu.points.RealPoint;
import ru.cs.vsu.points.ScreenPoint;

import java.awt.*;

public class BresenhemCircleDrawer  implements CircleDrawer {
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
public void drawCircle(ScreenConvertor sc, RealPoint center, double rOfSun) {

    double step = sc.getH() / 40000;
    for (double i = 0; i < 2 * Math.PI; i += step) {
        double x = center.getX() + rOfSun * Math.cos(i);
        double y = center.getY() + rOfSun * Math.sin(i);
        ScreenPoint p = sc.r2s(new RealPoint(x, y));
        pd.setPixel(p.getX(), p.getY(), Color.ORANGE);

    }
}
public void fillCircle (ScreenConvertor sc, DDALineDrawer ld,)
}
