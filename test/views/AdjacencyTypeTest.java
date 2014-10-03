package views;

import org.junit.Test;

import static org.junit.Assert.*;

public class AdjacencyTypeTest {

    @Test
    public void testNames() throws Exception {
        assertEquals("ABOVE", AdjacencyType.ABOVE.name());
        assertEquals("BELOW", AdjacencyType.BELOW.name());
        assertEquals("LEFT", AdjacencyType.LEFT.name());
        assertEquals("RIGHT", AdjacencyType.RIGHT.name());
        assertEquals("NOT_ADJACENT", AdjacencyType.NOT_ADJACENT.name());
    }
}