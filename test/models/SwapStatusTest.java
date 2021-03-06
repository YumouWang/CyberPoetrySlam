package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SwapStatusTest {

    @Test
    public void testNames() throws Exception {
        assertEquals("COMPLETED", SwapStatus.COMPLETED.name());
        assertEquals("REVOKED", SwapStatus.REVOKED.name());
        assertEquals("PENDING", SwapStatus.PENDING.name());
    }
}