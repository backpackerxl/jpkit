package cn.backpackerxl.jpkit.typeof;

public class JDouble extends JBase {
    private final Double value;

    public JDouble(double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public String apply(String name) {
        return null;
    }
}
