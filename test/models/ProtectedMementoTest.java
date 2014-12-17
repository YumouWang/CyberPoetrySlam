package models;

import org.junit.Test;
import views.AbstractWordView;
import views.WordView;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProtectedMementoTest {

    @Test
    public void testConstructor() throws Exception {
        Collection<AbstractWordView> abs = new HashSet<AbstractWordView>();
        abs.add(new WordView(new Word("Dog", WordType.NOUN), new Position(0, 0)));
        ProtectedMemento p = new ProtectedMemento(abs);
        assertNotNull(p);
    }

    @Test
    public void testGetUnprotectedView() {
        Collection<AbstractWordView> abs = new HashSet<AbstractWordView>();
        abs.add(new WordView(new Word("Dog", WordType.NOUN), new Position(0, 0)));
        ProtectedMemento p = new ProtectedMemento(abs);
        assertEquals(abs.size(), p.getProtectedView().size());
    }
}
