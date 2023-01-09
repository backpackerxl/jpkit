package cn.backpackerxl.jpkit.typeof;

import java.util.List;

public class JArray extends JBase {
    private final List<JBase> jBases;

    public JArray(List<JBase> jBases) {
        this.jBases = jBases;
    }

    @Override
    public List<JBase> getValue() {
        return jBases;
    }

    @Override
    public String apply(String name) {
        return null;
    }
}
