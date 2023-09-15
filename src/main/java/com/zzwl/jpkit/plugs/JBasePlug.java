package com.zzwl.jpkit.plugs;

import com.zzwl.jpkit.typeof.JBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JBasePlug<E> extends BasePlug<E> {
    @Override
    public E getObject(JBase jb) {
        return null;
    }

    @Override
    public E[] getArray(JBase jBase) {
        return null;
    }

    @Override
    public List<E> getList(JBase jBase) {
        return new ArrayList<>();
    }

    @Override
    public Map<String, E> getMap(JBase jBase) {
        return new HashMap<>();
    }
}
