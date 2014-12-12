package models;

import views.AbstractWordView;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class ProtectedMemento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5795305432374010152L;

	private Collection<AbstractWordView> protectedViews = new HashSet<AbstractWordView>();

	public ProtectedMemento(Collection<AbstractWordView> view) {
		protectedViews.addAll(view);
	}

	public Collection<AbstractWordView> getProtectedView() {
		return protectedViews;
	}
}