package kayseven.linq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * LINQBase
 */
public class LINQBase<E> implements Iterable<E> {

    @Deprecated
    @SuppressWarnings("rawtypes")
    public static final Comparator DEFAULT_COMPARATOR = new DefaultComparator();

    @Deprecated
    @SuppressWarnings("rawtypes")
    public static final EqualityComparer DEFAULT_EQUALITY_COMPARER = new DefaultEqualityComparer();

    @Deprecated
    @SuppressWarnings("rawtypes")
    public static final PropertyExpression SELF_SELECT_EXPRESSION = new PropertyExpression() {

        @Override
        public Object getValue(Object obj) {
            return obj;
        }

    };

    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> defaultComparator() {
        return DEFAULT_COMPARATOR;
    }

    @SuppressWarnings("unchecked")
    public static <T> EqualityComparer<T> defaultEqualityComparator() {
        return DEFAULT_EQUALITY_COMPARER;
    }

    @SuppressWarnings("unchecked")
    public static <T, TProperty> PropertyExpression<T, TProperty> selfSelectExpression() {
        return SELF_SELECT_EXPRESSION;
    }

    public static <E> List<E> asList(final Iterator<E> iterator) {
        List<E> iteList = new ArrayList<E>();

        while (iterator.hasNext()) {
            E next = iterator.next();
            iteList.add(next);
        }

        return iteList;
    }

    public static <T> LINQ<T> create(Iterator<T> iterator) {
        return new LINQ<T>(iterator);
    }

    public static <T> LINQ<T> create(Iterable<T> iterable) {
        return new LINQ<T>(iterable != null ? iterable : new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return Collections.emptyIterator();
            }
        });
    }

    public static <T> LINQ<T> create(T[] ary) {
        return new LINQ<T>(ary != null ? Arrays.asList(ary) : new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return Collections.emptyIterator();
            }
        });
    }

    public static <T> LINQ<T> create(final Enumeration<T> enumeration) {
        return new LINQ<T>(enumeration != null ? new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return enumeration.hasMoreElements();
                    }

                    @Override
                    public T next() {
                        return enumeration.nextElement();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                };
            }
        } : new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return Collections.emptyIterator();
            }
        });
    }

    private final Iterator<E> m_iterator;
    private final Object constructLock;

    Collection<E> collection;
    boolean isConstructed;

    LINQBase(final Iterator<E> iterator) {
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
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
}
