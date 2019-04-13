package kayseven.linq;

/**
 *
 * @author K7
 */
class DefaultEqualityComparer implements EqualityComparer<Object> {

    @Override
    public boolean isEquals(Object o1, Object o2) {
        return o1 == o2
                || (o1 != null && o1.equals(o2))
                || (o2 != null && o2.equals(o1));
    }

}
