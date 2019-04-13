package kayseven.linq;

/**
 *
 * @author K7
 */
public interface EqualityComparer<T> {
    boolean isEquals(T o1, T o2);
}
