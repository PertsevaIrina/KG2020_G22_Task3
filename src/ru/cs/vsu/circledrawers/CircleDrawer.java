package ru.cs.vsu.circledrawers;

import ru.cs.vsu.ScreenConvertor;
import ru.cs.vsu.points.RealPoint;

public interface CircleDrawer {
    void drawCircle(ScreenConvertor sc, RealPoint o, double rOfSun);
}
