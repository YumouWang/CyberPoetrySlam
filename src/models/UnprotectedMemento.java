package models;

import views.AbstractWordView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This is UnprotectedMemento Class for unprotected state preservation
 *
 * @author xujian Created on 10/25/2014
 */

public class UnprotectedMemento implements Serializable {

    /**
     * unprotectedView is a collection for AbstractWordView
     */
    private static final long serialVersionUID = -8852796032126561897L;

    private List<AbstractWordView> unprotectedViews = new ArrayList<AbstractWordView>();

    /**
     * Constructor for UnprotectedMemento
     *
     * @param view
     */
    public UnprotectedMemento(Collection<AbstractWordView> view) {
        for (AbstractWordView abstractWordView : view) {
            unprotectedViews.add((AbstractWordView) abstractWordView.clone());
        }
    }

    /**
     * get UnprotectedViews this collection
     *
     * @return unprotectedViews
     */
    public List<AbstractWordView> getUnprotectedView() {
        return unprotectedViews;
    }

}
