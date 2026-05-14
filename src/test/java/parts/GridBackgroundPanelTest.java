package parts;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GridBackgroundPanelTest {

    @Test
    void toScreenYUsesBoundingBoxTopAsOriginWhenPresent() throws Exception {
        GridBackgroundPanel panel = new GridBackgroundPanel(20);
        panel.setBoundingBox(new BoundingBoxView(0, 0, 100, 50));

        Method toScreenY = GridBackgroundPanel.class.getDeclaredMethod("toScreenY", int.class);
        toScreenY.setAccessible(true);

        int screenYAtBottom = (int) toScreenY.invoke(panel, 0);
        int screenYAtTop = (int) toScreenY.invoke(panel, 50);

        assertEquals(70, screenYAtBottom);
        assertEquals(20, screenYAtTop);
    }

    @Test
    void toScreenYFallsBackToPaddingOffsetWithoutBoundingBox() throws Exception {
        GridBackgroundPanel panel = new GridBackgroundPanel(20);

        Method toScreenY = GridBackgroundPanel.class.getDeclaredMethod("toScreenY", int.class);
        toScreenY.setAccessible(true);

        int screenY = (int) toScreenY.invoke(panel, 12);
        assertEquals(32, screenY);
    }
}
