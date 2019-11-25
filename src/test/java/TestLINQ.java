import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import kayseven.linq.LINQ;
import kayseven.linq.Predicate;
import kayseven.linq.PropertyExpression;
import kayseven.linq.operation.grouping.Grouping;

/**
 * TestLINQ
 */
public class TestLINQ {
    private TestModel model1 = new TestModel(1, 100, "kilo");
    private TestModel model2 = new TestModel(2, 20, "delta");
    private TestModel model3 = new TestModel(3, 150, "terabit");
    private TestModel model4 = new TestModel(4, 20, "kilo");

    private final LINQ<TestModel> modelQ = LINQ.create(new TestModel[] { model1, model2, model3, model4 });

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <A> Matcher<Iterable<? extends A>> assertIterable(Iterable<A> iterable) {
        List<Matcher<? super A>> matchers = LINQ.create(iterable)
                .select(new PropertyExpression<A, Matcher<? super A>>() {
                    @Override
                    public Matcher<? super A> getValue(A obj) {
                        if (obj instanceof Grouping) {
                            final Grouping grp = (Grouping) obj;

                            return CoreMatchers.both(assertIterable(grp)).and(new IsEqual(grp.getKey()) {
                                @Override
                                public boolean matches(Object actualValue) {
                                    return super.matches(((Grouping) actualValue).getKey());
                                }
                            });
                        }

                        return IsEqual.equalTo(obj);
                    }
                }).toList();

        return new IsIterableContainingInOrder<A>(matchers);
    }

    private static <E> void assertThat(Iterable<E> iterable, E... items) {
        List<E> actual = LINQ.create(iterable).toList();
        List<E> expected = Arrays.asList(items);
        Assert.assertEquals(actual.size(), expected.size());
        Assert.assertThat(actual, assertIterable(expected));
    }

    @Test
    public void testSelect() {
        assertThat(modelQ.select(new PropertyExpression<TestModel, Double>() {
            @Override
            public Double getValue(TestModel obj) {
                return obj.getValue();
            }
        }), 100D, 20D, 150D, 20D);
    }

    @Test
    public void testWhere() {
        assertThat(modelQ.where(new Predicate<TestModel>() {
            @Override
            public boolean test(TestModel data) {
                return data.getValue() >= 100;
            }
        }), model1, model3);
    }

    @Test
    public void testOrder() {
        assertThat(modelQ.orderDescending().thenBy(new PropertyExpression<TestModel, String>() {
            @Override
            public String getValue(TestModel obj) {
                return obj.getUnit();
            }
        }), model3, model1, model2, model4);
    }

    @Test
    public void testOrderNumeric() {
        assertThat(LINQ.create(new Integer[] { 2, 4, 1, null }).orderAscending(), null, 1, 2, 4);
    }

    @Test
    public void testOrderDescLetter() {
        assertThat(LINQ.create(new String[] { "D", null, "A", "Z", "C" }).orderDescending(), "Z", "D", "C", "A", null);
    }

    @Test
    @SuppressWarnings({ "unchecked" })
    public void testGroupBy() {
        TestLINQ.assertThat(modelQ.groupBy(new PropertyExpression<TestModel, String>() {
            @Override
            public String getValue(TestModel obj) {
                return obj.getUnit();
            }
        }), new Grouping<String, TestModel>("kilo", Arrays.asList(model1, model4)),
                new Grouping<String, TestModel>("delta", Arrays.asList(model2)),
                new Grouping<String, TestModel>("terabit", Arrays.asList(model3)));
    }
}