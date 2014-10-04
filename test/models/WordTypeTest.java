package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WordTypeTest {
    @Test
    public void testWordTypeNames() throws Exception {
        assertEquals("NOUN", WordType.NOUN.name());
        assertEquals("VERB", WordType.VERB.name());
        assertEquals("PRONOUN", WordType.PRONOUN.name());
        assertEquals("ADJECTIVE", WordType.ADJECTIVE.name());
        assertEquals("ADVERB", WordType.ADVERB.name());
        assertEquals("PREPOSITION", WordType.PREPOSITION.name());
        assertEquals("CONJUNCTION", WordType.CONJUNCTION.name());
        assertEquals("INTERJECTION", WordType.INTERJECTION.name());
        assertEquals("PREFIX", WordType.PREFIX.name());
        assertEquals("POSTFIX", WordType.POSTFIX.name());
        assertEquals("ANY", WordType.ANY.name());
    }
}