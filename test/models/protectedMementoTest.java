package models;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import views.AbstractWordView;
import views.WordView;
import static org.junit.Assert.*;

public class protectedMementoTest {

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
		assertEquals(abs, p.getProtectedView());
	}
}
