package kayseven.linq.operation.ordering;

import java.util.Comparator;
import kayseven.linq.LINQ;
import kayseven.linq.LINQBase;
import kayseven.linq.PropertyExpression;

/**
 *
 * @author K7
 * @param <E>
 */
public class OrderedLINQ<E> extends LINQ<E> {
    private final OrderedLINQOperation<E, ?, ?> operation;

    <EProperty> OrderedLINQ(OrderedLINQOperation<E, EProperty, ?> operation) {
        super(LINQ.create(operation).iterator());
        this.operation = operation;
    }
    
    public  OrderedLINQ<E> thenAscending() {
        return thenBy(LINQBase.<E,E>selfSelectExpression());
    }

    public <EThen> OrderedLINQ<E> thenBy(PropertyExpression<E, EThen> expression) {
        return thenBy(expression, LINQBase.<EThen>defaultComparator());
    }

    public <EThen> OrderedLINQ<E> thenUsing(Comparator<EThen> comparator) {
        return thenBy(LINQBase.<E,EThen>selfSelectExpression(), comparator);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <EThen> OrderedLINQ<E> thenBy(PropertyExpression<E, EThen> expression, Comparator<EThen> comparator) {
        return new OrderByAscendingOperation(operation.linq(), operation, expression, comparator).getValue();
    }
    
    public  OrderedLINQ<E> thenDescending() {
        return thenBy(LINQBase.<E,E>selfSelectExpression());
    }

    public <EThen> OrderedLINQ<E> thenDescendingBy(PropertyExpression<E, EThen> expression) {
        return thenBy(expression, LINQBase.<EThen>defaultComparator());
    }

    public <EThen> OrderedLINQ<E> thenDescendingUsing(Comparator<EThen> comparator) {
        return thenBy(LINQBase.<E,EThen>selfSelectExpression(), comparator);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <EThen> OrderedLINQ<E> thenDescendingBy(PropertyExpression<E, EThen> expression, Comparator<EThen> comparator) {
        return new OrderByDescendingOperation(operation.linq(), operation, expression, comparator).getValue();
    }
}
