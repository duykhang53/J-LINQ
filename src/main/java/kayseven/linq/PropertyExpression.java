package kayseven.linq;

/**
 *
 * @author K7
 * @param <T> linq item type
 * @param <TProperty> target type
 */
public interface PropertyExpression<T, TProperty> {

    TProperty getValue(T obj);
}
