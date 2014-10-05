package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {

    @Test
    public void testConstructor() throws Exception {
        Position p = new Position(3, 5);
        assertNotNull(p);
        assertEquals(3, p.x);
        assertEquals(5, p.y);
        assertEquals(3, p.getX());
        assertEquals(5, p.getY());
    }

    @Test
    public void testEquals() throws Exception {
        Position p1 = new Position(4, 4);
        Position p2 = new Position(4, 4);
        Position p3 = new Position(4, 5);
        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
    }
}