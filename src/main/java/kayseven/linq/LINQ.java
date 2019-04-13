package kayseven.linq;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
import kayseven.linq.operation.grouping.Grouping;
import kayseven.linq.operation.ordering.OrderByAscendingOperation;
import kayseven.linq.operation.ordering.OrderedLINQ;

/**
 *
 * @author K7
 * @param <E> item type
 */
public class LINQ<E> implements Iterable<E> {

    @SuppressWarnings("rawtypes")
    public static final Comparator DEFAULT_COMPARATOR = new DefaultComparator();
    @SuppressWarnings("rawtypes")
    public static final EqualityComparer DEFAULT_EQUALITY_COMPARER = new DefaultEqualityComparer();

    public static <E> List<E> asList(final Iterator<E> iterator) {
        List<E> iteList = new ArrayList<E>();

        while (iterator.hasNext()) {
            E next = iterator.next();
            iteList.add(next);
        }

        return iteList;
    }

    public static <T> LINQ<T> create(Iterable<T> iterable) {
        return new LINQ<T>(iterable != null ? iterable : new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public T next() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                };
            }
        });
    }

    public static <T> LINQ<T> create(T[] ary) {
        return new LINQ<T>(Arrays.asList(ary));
    }

    private static <T> LINQ<T> concat(final Iterator<T> ite1, final Iterator<T> ite2) {
        return new ConcatenateOperation<T>(ite1, ite2).getLINQ();
    }
    private Collection<E> collection;
    private final Iterator<E> m_iterator;
    private boolean isConstructed;
    private final Object constructLock;

    protected LINQ(Iterable<E> iterable) {
        this(iterable.iterator());

        if (iterable instanceof Collection) {
            isConstructed = true;
            this.collection = (Collection<E>) iterable;
        }
    }

    protected LINQ(final Iterator<E> iterator) {
        m_iterator = iterator;
        constructLock = new Object();
    }

    @Override
    public final synchronized Iterator<E> iterator() {
        if (isConstructed) {
            return collection.iterator();
        }

        final List<E> syncList;
        if (collection == null) {
            syncList = Collections.synchronizedList(new ArrayList<E>());
            collection = syncList;
        } else {
            syncList = (List<E>) collection;
        }

        return new Iterator<E>() {
            private int idx = -1;

            @Override
            public boolean hasNext() {
                synchronized (constructLock) {
                    return idx + 1 < syncList.size() || m_iterator.hasNext();
                }
            }

            @Override
            public E next() {
                synchronized (constructLock) {
                    if (++idx < syncList.size()) {
                        return syncList.get(idx);
                    }

                    E nxt = m_iterator.next();
                    if (!m_iterator.hasNext()) {
                        isConstructed = true;
                    }
                    syncList.add(nxt);

                    return nxt;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
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
        if (collection instanceof List && index < collection.size()) {
            return ((List<E>) collection).get(index);
        }

        return skip(index).iterator().next();
    }

    public boolean any() {
        return iterator().hasNext();
    }

    public int size() {
        if (isConstructed) {
            return collection.size();
        }

        int cnt = 0;

        for (E e : this) {
            cnt++;
        }

        return cnt;
    }

    public LINQ<E> skip(int offset) {
        return new SkipOperation<E>(iterator(), offset).getLINQ();
    }

    public LINQ<E> skipWhile(final Predicate<E> predicate) {
        return new SkipWhileOperation(iterator(), predicate).getLINQ();
    }

    public LINQ<E> take(final int count) {
        return new TakeOperation<E>(iterator(), count).getLINQ();
    }

    public LINQ<E> takeWhile(final Predicate<E> predicate) {
        return new TakeWhileOperation<E>(iterator(), predicate).getLINQ();
    }

    public E[] toArray(Class<E> clazz) {
        List<E> list = toList();
        E[] ary = (E[]) Array.newInstance(clazz, list.size());
        return list.toArray(ary);
    }

    public List<E> toList() {
        ArrayList<E> list = new ArrayList<E>();

        for (E e : this) {
            list.add(e);
        }

        return list;
    }

    public LinkedList<E> toLinkedList() {
        LinkedList<E> linkedList = new LinkedList<E>();

        for (E e : this) {
            linkedList.add(e);
        }

        return linkedList;
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
        return distinct(DEFAULT_EQUALITY_COMPARER);
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
        for (E e : this) {
            if (e == obj || (obj != null && obj.equals(e))) {
                return true;
            }
        }

        return false;
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

    public <EProperty> OrderedLINQ<E> orderBy(final PropertyExpression<E, EProperty> expression) {
        return new OrderByAscendingOperation<E, EProperty>(this, expression, DEFAULT_COMPARATOR).getLINQ();
    }

    public <EProperty> OrderedLINQ<E> orderBy(final PropertyExpression<E, EProperty> expression, final Comparator<EProperty> comparator) {
        return new OrderByAscendingOperation<E, EProperty>(this, expression, comparator).getLINQ();
    }

    public <EProperty> LINQ<Grouping<EProperty, E>> groupBy(PropertyExpression<E, EProperty> expression) {
        return groupBy(expression, DEFAULT_EQUALITY_COMPARER);
    }

    public LINQ<E> foreach(Consumer<E> consumer) {
        for (E item : this) {
            consumer.consume(item);
        }

        return this;
    }

    public <EProperty> LINQ<Grouping<EProperty, E>> groupBy(final PropertyExpression<E, EProperty> expression, final EqualityComparer<EProperty> equalityComparer) {
        final LinkedList<E> ll = toLinkedList();

        return new LINQ<Grouping<EProperty, E>>(new Iterator<Grouping<EProperty, E>>() {
            @Override
            public boolean hasNext() {
                return !ll.isEmpty();
            }

            @Override
            public Grouping<EProperty, E> next() {
                E first = ll.removeFirst();
                final EProperty prop = first == null ? null : expression.getValue(first);

                List<E> lst = LINQ.create(ll).where(new Predicate<E>() {
                    @Override
                    public boolean test(E t) {
                        EProperty otherProp = t == null ? null : expression.getValue(t);
                        return equalityComparer.isEquals(prop, otherProp);
                    }
                }).toList();
                lst.add(0, first);
                ll.removeAll(lst);

                return new Grouping<EProperty, E>(prop, lst);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
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
