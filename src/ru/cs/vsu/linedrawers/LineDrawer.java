package ru.cs.vsu.linedrawers;

import ru.cs.vsu.points.ScreenPoint;

import java.awt.*;

public interface LineDrawer {
    void drawLine(ScreenPoint p1, ScreenPoint p2, Color c);
}
