package types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawingEngineTest {

    @Test
    void rejectsProgramWhenBoundingBoxIsNotFirst() {
        String program = """
                (LINE (0 0) (4 0))
                (BOUNDING-BOX (0 0) (10 10))
                """;

        assertThrows(RuntimeException.class, () -> DrawingEngine.interpret(program));
    }

    @Test
    void rejectsProgramWhenBoundingBoxAppearsMultipleTimes() {
        String program = """
                (BOUNDING-BOX (0 0) (10 10))
                (LINE (0 0) (4 0))
                (BOUNDING-BOX (1 1) (2 2))
                """;

        assertThrows(RuntimeException.class, () -> DrawingEngine.interpret(program));
    }

    @Test
    void clipsPointsToBoundingBox() {
        String program = """
                (BOUNDING-BOX (0 0) (2 2))
                (LINE (0 0) (4 0))
                """;

        var points = DrawingEngine.interpret(program);

        assertFalse(points.isEmpty());
        points.forEach(p -> {
            assertTrue(p.x() >= 0 && p.x() <= 2);
            assertTrue(p.y() >= 0 && p.y() <= 2);
        });
    }

    @Test
    void keepsDefaultColorOutsideDrawScope() {
        String program = """
                (BOUNDING-BOX (0 0) (10 10))
                (DRAW red
                  (LINE (0 0) (2 0))
                )
                (LINE (0 1) (2 1))
                """;

        var points = DrawingEngine.interpret(program);

        boolean hasRed = points.stream().anyMatch(p -> p.color().equalsIgnoreCase("red"));
        boolean hasBlack = points.stream().anyMatch(p -> p.color().equalsIgnoreCase("black"));

        assertTrue(hasRed, "Expected points from DRAW red");
        assertTrue(hasBlack, "Expected points from default color outside DRAW");
    }

    @Test
    void returnsRequiredBoundingBox() {
        String program = """
                (BOUNDING-BOX (3 4) (8 9))
                (LINE (3 4) (8 9))
                """;

        var boundingBox = DrawingEngine.getBoundingBox(program);

        assertTrue(boundingBox.isPresent());
        assertEquals(3, boundingBox.get().x1());
        assertEquals(4, boundingBox.get().y1());
        assertEquals(8, boundingBox.get().x2());
        assertEquals(9, boundingBox.get().y2());
    }
}
