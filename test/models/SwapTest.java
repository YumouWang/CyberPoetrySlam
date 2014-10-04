package models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SwapTest {

    @Test
    public void testUpdateSwapStatus() throws Exception {
        Swap swap = new Swap();
        assertEquals(SwapStatus.COMPLETED, swap.status);
        swap.updateSwapStatus(SwapStatus.PENDING);
        assertEquals(SwapStatus.PENDING, swap.status);
        swap.updateSwapStatus(SwapStatus.REVOKED);
        assertEquals(SwapStatus.REVOKED, swap.status);
    }

    @Test
    public void testUpdateCurrentSwap() throws Exception {
        Swap swap = new Swap();
        List<WordType> giveType = new ArrayList<WordType>();
        List<String> giveValue = new ArrayList<String>();
        List<WordType> getType = new ArrayList<WordType>();
        List<String> getValue = new ArrayList<String>();
        giveType.add(WordType.ADJECTIVE);
        giveValue.add("*");
        getType.add(WordType.NOUN);
        getValue.add("*");
        swap.updateCurrentSwap(1, giveType, giveValue, getType, getValue);
        assertEquals(1, swap.num);
        assertEquals(1, swap.giveType.size());
        assertTrue(swap.giveType.contains(WordType.ADJECTIVE));
        assertEquals(1, swap.giveValue.size());
        assertTrue(swap.giveValue.contains("*"));
        assertEquals(1, swap.getType.size());
        assertTrue(swap.getType.contains(WordType.NOUN));
        assertEquals(1, swap.getValue.size());
        assertTrue(swap.getValue.contains("*"));
    }
}