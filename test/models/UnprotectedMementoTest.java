package models;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import views.AbstractWordView;
import views.WordView;
import static org.junit.Assert.*;

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
		assertEquals(abs, un.getUnprotectedView());
	}

}
