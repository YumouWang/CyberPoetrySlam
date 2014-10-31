package models;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import views.AbstractWordView;
import views.PoemView;
import views.RowView;
import views.WordView;

public class unprotectedMemento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8852796032126561897L;

	private Collection<AbstractWordView> unprotectedViews = new HashSet<AbstractWordView>();

	public unprotectedMemento(Collection<AbstractWordView> view) {
		unprotectedViews.addAll(view);
	}

	public Collection<AbstractWordView> getUnprotectedView() {
		return unprotectedViews;
	}

}
