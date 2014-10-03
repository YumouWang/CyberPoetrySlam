package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WordTypeTest {
    @Test
    public void testWordTypes() throws Exception {
        assertNotNull(WordType.NOUN);
        assertNotNull(WordType.VERB);
        assertNotNull(WordType.PRONOUN);
        assertNotNull(WordType.ADJECTIVE);
        assertNotNull(WordType.ADVERB);
        assertNotNull(WordType.PREPOSITION);
        assertNotNull(WordType.CONJUNCTION);
        assertNotNull(WordType.INTERJECTION);
        assertNotNull(WordType.PREFIX);
        assertNotNull(WordType.POSTFIX);
        assertNotNull(WordType.ANY);
    }
}