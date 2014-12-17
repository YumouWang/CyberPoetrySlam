package models;

import views.AbstractWordView;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * This is ProtectedMemento Class for protected state preservation
 *
 * @author xujian Created on 10/25/2014
 */

public class ProtectedMemento implements Serializable {

    /**
     * protectedView is a collection for AbstractWordView
     */
    private static final long serialVersionUID = -5795305432374010152L;

    private Collection<AbstractWordView> protectedViews = new HashSet<AbstractWordView>();

    /**
     * Constructor for ProtectedMemento
     *
     * @param view
     */
    public ProtectedMemento(Collection<AbstractWordView> view) {
        for (AbstractWordView abstractWordView : view) {
            protectedViews.add((AbstractWordView) abstractWordView.clone());
        }
        //protectedViews.addAll(view);
    }

    /**
     * get protectedViews this collection
     *
     * @return protectedViews
     */
    public Collection<AbstractWordView> getProtectedView() {
        return protectedViews;
    }
}
