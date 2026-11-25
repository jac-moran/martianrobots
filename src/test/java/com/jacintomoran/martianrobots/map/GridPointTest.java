package com.jacintomoran.martianrobots.map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GridPointTest {

    @Test
    void shouldStoreCoordinates() {
        GridPoint point = new GridPoint(3, 5);
        assertEquals(3, point.getX());
        assertEquals(5, point.getY());
    }

    @Test
    void movementShouldAlterCoordinates() {
        GridPoint point = new GridPoint(2, 2);
        point.addX(3);
        point.addY(-1);

        assertEquals(5, point.getX());
        assertEquals(1, point.getY());
    }

    @Test
    void testToString() {
        GridPoint point = new GridPoint(4, 7);
        assertEquals("4 7", point.toString());
    }

    @Test
    void equalsAndHashCodeTest() {
        GridPoint p1 = new GridPoint(1, 2);
        GridPoint p2 = new GridPoint(1, 2);
        GridPoint differentX = new GridPoint(9, 2);
        GridPoint differentY = new GridPoint(1, 9);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, differentX);
        assertNotEquals(p1, differentY);
        assertNotEquals(p1, null);
        assertNotEquals(p1, "1 2");
    }
}
