package com.abs;

import com.models.Computador;

public interface Factory {
	void init(String packageName);
	public <T> T create(Class<T> clazz);
}
