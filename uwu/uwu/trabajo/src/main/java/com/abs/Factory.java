package com.abs;

public interface Factory {
    void init(String packageName);
    public <T> T create(Class<T> clazz);
}

