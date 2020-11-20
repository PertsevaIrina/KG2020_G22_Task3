package ru.cs.vsu.circledrawers;

import ru.cs.vsu.ScreenConvertor;
import ru.cs.vsu.linedrawers.LineDrawer;
import ru.cs.vsu.pixeldrawers.PixelDrawer;
import ru.cs.vsu.points.ScreenPoint;

public interface CircleDrawer {
    void drawCircle(ScreenConvertor sc, PixelDrawer pd, ScreenPoint o, int rOfSun);
    void fillCircle (ScreenConvertor sc, LineDrawer ld, ScreenPoint o, int rOfSun);
}
