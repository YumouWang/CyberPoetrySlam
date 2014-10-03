package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Observable;

/**
 * The Area model class
 *
 * Created by Nathan on 10/3/2014.
 */
public class Area extends Observable {

    Collection<AbstractWord> abstractWordCollection;

    public Area(Collection<AbstractWord> words) {
        this.abstractWordCollection = new HashSet<AbstractWord>();
        if(words != null) {
            this.abstractWordCollection.addAll(words);
        }
    }

    public boolean addAbstractWord(AbstractWord word) {
        return abstractWordCollection.add(word);
    }

    public boolean removeAbstractWord(AbstractWord word) {
        return abstractWordCollection.remove(word);
    }

    public Collection<AbstractWord> getAbstractWordCollection() {
        return abstractWordCollection;
    }
}
