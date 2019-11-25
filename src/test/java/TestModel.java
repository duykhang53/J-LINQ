/**
 * TestModel
 */
public class TestModel implements Comparable<TestModel> {

    private int id;
    private double value;
    private String unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public TestModel(int id, double value, String unit) {
        this.id = id;
        this.value = value;
        this.unit = unit;
    }

    @Override
    public int compareTo(TestModel o) {
        return (int) (value - o.value);
    }

    @Override
    public String toString() {
        return "TestModel (#" + id + ", " + value + " " + unit + ")";
    }
}