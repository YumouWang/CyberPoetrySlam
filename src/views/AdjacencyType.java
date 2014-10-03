package views;

/**
 * An enum describing the different ways words can be adjacent to each other.
 * Words will determine how they are adjacent to each other, and the controller will
 * determine how to connect the words together.
 *
 * Created by Nathan on 10/3/2014.
 */
public enum AdjacencyType {
    ABOVE, BELOW, LEFT, RIGHT, NOT_ADJACENT
}
