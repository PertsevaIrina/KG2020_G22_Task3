package ru.cs.vsu.circledrawers;

import ru.cs.vsu.ScreenConvertor;
import ru.cs.vsu.linedrawers.LineDrawer;
import ru.cs.vsu.models.Line;
import ru.cs.vsu.pixeldrawers.PixelDrawer;
import ru.cs.vsu.points.ScreenPoint;

import java.awt.*;

public interface CircleDrawer {
    void drawCircle(PixelDrawer pd, ScreenPoint o, int rOfSun, Color c);

    void fillCircle(ScreenConvertor sc, LineDrawer ld, ScreenPoint o, int rOfSun, Color c);
}
