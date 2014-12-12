package models;

import views.AbstractWordView;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class UnprotectedMemento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8852796032126561897L;

	private Collection<AbstractWordView> unprotectedViews = new HashSet<AbstractWordView>();

	public UnprotectedMemento(Collection<AbstractWordView> view) {
		unprotectedViews.addAll(view);
	}

	public Collection<AbstractWordView> getUnprotectedView() {
		return unprotectedViews;
	}

}