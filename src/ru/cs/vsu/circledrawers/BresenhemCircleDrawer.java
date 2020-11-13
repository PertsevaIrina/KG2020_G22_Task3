package ru.cs.vsu.circledrawers;

import ru.cs.vsu.pixeldrawers.PixelDrawer;

import java.awt.*;

public class BresenhemCircleDrawer  implements CircleDrawer {
        private PixelDrawer pd;

    public BresenhemCircleDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
        public void drawCircle(int x, int y, int rOfSun) {
            int x1 = 0, y1 = rOfSun, gap = 0, delta = (2 - 2 * rOfSun);
            while (y1 >= 0) {

                pd.setPixel(x + x1, y + y1, Color.ORANGE);
                pd.setPixel(x + x1, y - y1, Color.ORANGE);
                pd.setPixel(x - x1, y - y1, Color.ORANGE);
                pd.setPixel(x - x1, y + y1, Color.ORANGE);
                gap = 2 * (delta + y1) - 1;
                if (delta < 0 && gap <= 0) {
                    x1++;
                    delta += 2 * x1 + 1;
                    continue;
                }
                if (delta > 0 && gap > 0) {
                    y1--;
                    delta -= 2 * y1 + 1;
                    continue;
                }
                x1++;
                delta += 2 * (x1 - y1);
                y1--;
            }
        }
}
