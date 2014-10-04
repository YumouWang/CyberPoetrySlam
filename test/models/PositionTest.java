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
}