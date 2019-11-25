package kayseven.linq;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import kayseven.linq.accessor.ContainsAccessor;
import kayseven.linq.accessor.ElementAtAccessor;
import kayseven.linq.accessor.SizeAccessor;
import kayseven.linq.accessor.ToArrayAccessor;
import kayseven.linq.accessor.ToLinkedListAccessor;
import kayseven.linq.accessor.ToListAccessor;
import kayseven.linq.operation.ConcatenateOperation;
import kayseven.linq.operation.DistinctOperation;
import kayseven.linq.operation.OfTypeOperation;
import kayseven.linq.operation.ReverseOperation;
import kayseven.linq.operation.SelectOperation;
import kayseven.linq.operation.SkipOperation;
import kayseven.linq.operation.SkipWhileOperation;
import kayseven.linq.operation.TakeOperation;
import kayseven.linq.operation.TakeWhileOperation;
import kayseven.linq.operation.UnionSelectOperation;
import kayseven.linq.operation.WhereOperation;
import kayseven.linq.operation.grouping.GroupByOperation;
import kayseven.linq.operation.grouping.Grouping;
import kayseven.linq.operation.ordering.OrderByAscendingOperation;
import kayseven.linq.operation.ordering.OrderByDescendingOperation;
import kayseven.linq.operation.ordering.OrderedLINQ;

/**
 *
 * @author K7
 * @param <E> item type
 */
public class LINQ<E> extends LINQBase<E> {

    private static <T> LINQ<T> concat(final Iterator<T> ite1, final Iterator<T> ite2) {
        return new ConcatenateOperation<T>(ite1, ite2).getLINQ();
    }

    protected LINQ(Iterable<E> iterable) {
        this(iterable.iterator());

        if (iterable instanceof Collection) {
            super.isConstructed = true;
            super.collection = (Collection<E>) iterable;
        }
    }

    protected LINQ(Iterator<E> iterator) {
        super(iterator);
    }

    public LINQ<E> reverse() {
        return new ReverseOperation<E>(iterator()).getLINQ();
    }

    public E firstOrNull() {
        return firstOrDefault(null);
    }

    public E firstOrDefault(E defaultValue) {
        Iterator<E> iterator = iterator();
        return iterator.hasNext() ? iterator.next() : defaultValue;
    }

    public E elementAt(int index) {
        return new ElementAtAccessor<E>(this, index).getValue();
    }

    public boolean any() {
        return iterator().hasNext();
    }

    public int size() {
        return new SizeAccessor<E>(this).getValue();
    }

    public LINQ<E> skip(int offset) {
        return new SkipOperation<E>(iterator(), offset).getLINQ();
    }

    public LINQ<E> skipWhile(final Predicate<E> predicate) {
        return new SkipWhileOperation<E>(iterator(), predicate).getLINQ();
    }

    public LINQ<E> take(final int count) {
        return new TakeOperation<E>(iterator(), count).getLINQ();
    }

    public LINQ<E> takeWhile(final Predicate<E> predicate) {
        return new TakeWhileOperation<E>(iterator(), predicate).getLINQ();
    }

    public E[] toArray(Class<E> clazz) {
        return new ToArrayAccessor<E>(this, clazz).getValue();
    }

    public List<E> toList() {
        return new ToListAccessor<E>(this).getValue();
    }

    public LinkedList<E> toLinkedList() {
        return new ToLinkedListAccessor<E>(this).getValue();
    }

    public boolean contains(E obj, EqualityComparer<E> equalityComparer) {
        for (E e : this) {
            if (equalityComparer.isEquals(e, obj)) {
                return true;
            }
        }

        return false;
    }

    public LINQ<E> prepend(E... items) {
        return prepend(Arrays.asList(items));
    }

    public LINQ<E> prepend(Iterable<E> iterable) {
        return concat(iterable.iterator(), iterator());
    }

    public LINQ<E> append(E... items) {
        return append(Arrays.asList(items));
    }

    public LINQ<E> distinct() {
        return distinct(LINQBase.<E>defaultEqualityComparator());
    }

    public LINQ<E> distinct(EqualityComparer<E> equalityComparer) {
        return new DistinctOperation<E>(iterator(), equalityComparer).getLINQ();
    }

    public LINQ<E> append(Iterable<E> iterable) {
        return concat(iterator(), iterable.iterator());
    }

    public LINQ<E> exclude(E... items) {
        return exclude(Arrays.asList(items));
    }

    public LINQ<E> exclude(Iterable<E> iterable) {
        final LINQ<E> cQ = new LINQ<E>(iterable);
        return where(new Predicate<E>() {
            @Override
            public boolean test(E t) {
                return !cQ.contains(t);
            }
        });
    }

    public boolean contains(E obj) {
        return new ContainsAccessor<E>(this, obj).getValue();
    }

    public boolean any(Predicate<E> predicate) {
        for (E e : this) {
            if (predicate.test(e)) {
                return true;
            }
        }

        return false;
    }

    public LINQ<E> where(final Predicate<E> predicate) {
        return new WhereOperation<E>(iterator(), predicate).getLINQ();
    }

    public <T> LINQ<T> ofType(final Class<T> clazz) {
        return new OfTypeOperation<E, T>(iterator(), clazz).getLINQ();
    }

    public OrderedLINQ<E> orderAscending() {
        return orderBy(LINQBase.<E, E>selfSelectExpression());
    }

    public OrderedLINQ<E> orderUsing(Comparator<E> comparator) {
        return orderBy(LINQBase.<E, E>selfSelectExpression(), comparator);
    }

    public <EProperty> OrderedLINQ<E> orderBy(PropertyExpression<E, EProperty> expression) {
        return orderBy(expression, LINQBase.<EProperty>defaultComparator());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <EProperty> OrderedLINQ<E> orderBy(PropertyExpression<E, EProperty> expression,
            final Comparator<EProperty> comparator) {
        return new OrderByAscendingOperation(this, null, expression, comparator).getValue();
    }

    public OrderedLINQ<E> orderDescending() {
        return orderDescendingBy(LINQBase.<E, E>selfSelectExpression());
    }

    public OrderedLINQ<E> orderDescendingUsing(Comparator<E> comparator) {
        return orderDescendingBy(LINQBase.<E, E>selfSelectExpression(), comparator);
    }

    public <EProperty> OrderedLINQ<E> orderDescendingBy(PropertyExpression<E, EProperty> expression) {
        return orderDescendingBy(expression, LINQBase.<EProperty>defaultComparator());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <EProperty> OrderedLINQ<E> orderDescendingBy(PropertyExpression<E, EProperty> expression,
            final Comparator<EProperty> comparator) {
        return new OrderByDescendingOperation(this, null, expression, comparator).getValue();
    }

    public LINQ<E> foreach(Consumer<E> consumer) {
        for (E item : this) {
            consumer.consume(item);
        }

        return this;
    }

    public <EProperty> LINQ<Grouping<EProperty, E>> groupUsing(EqualityComparer<EProperty> equalityComparer) {
        return groupBy(LINQBase.<E, EProperty>selfSelectExpression(), equalityComparer);
    }

    public <EProperty> LINQ<Grouping<EProperty, E>> groupBy(PropertyExpression<E, EProperty> expression) {
        return groupBy(expression, LINQBase.<EProperty>defaultEqualityComparator());
    }

    public <EProperty> LINQ<Grouping<EProperty, E>> groupBy(final PropertyExpression<E, EProperty> expression,
            final EqualityComparer<EProperty> equalityComparer) {
        return new GroupByOperation<E, EProperty>(this, expression, equalityComparer).getValue();
    }

    public <EProperty> LINQ<EProperty> select(final PropertyExpression<E, EProperty> expression) {
        return new SelectOperation<E, EProperty>(iterator(), expression).getLINQ();
    }

    public <EProperty> LINQ<EProperty> unionSelect(final Class<EProperty> clazz) {
        return UnionSelectOperation.getClassUnionSelect(iterator(), clazz);
    }

    public <EProperty> LINQ<EProperty> unionSelect(final PropertyExpression<E, Iterable<EProperty>> expression) {
        return new UnionSelectOperation<E, EProperty>(iterator(), expression).getLINQ();
    }
}
