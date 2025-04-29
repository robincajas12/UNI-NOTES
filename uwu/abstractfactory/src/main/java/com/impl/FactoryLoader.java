package com.impl;

import com.google.common.reflect.ClassPath;
import com.inter.UIFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FactoryLoader {
    Map<String, Class<? extends UIFactory>> map = new HashMap<>();
    public void load(String url)
    {
        InputStream path = ClassLoader.getSystemResourceAsStream(url);
        path.
    }
}
