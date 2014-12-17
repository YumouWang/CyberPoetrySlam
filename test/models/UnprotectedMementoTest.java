package models;

import org.junit.Test;
import views.AbstractWordView;
import views.WordView;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UnprotectedMementoTest {

    @Test
    public void testConstructor() throws Exception {
        Collection<AbstractWordView> abs = new HashSet<AbstractWordView>();
        abs.add(new WordView(new Word("Dog", WordType.NOUN), new Position(0, 0)));
        UnprotectedMemento un = new UnprotectedMemento(abs);
        assertNotNull(un);
    }

    @Test
    public void testGetUnprotectedView() throws Exception {
        Collection<AbstractWordView> abs = new HashSet<AbstractWordView>();
        abs.add(new WordView(new Word("Dog", WordType.NOUN), new Position(0, 0)));
        UnprotectedMemento un = new UnprotectedMemento(abs);
        assertEquals(abs.size(), un.getUnprotectedView().size());
    }

}
