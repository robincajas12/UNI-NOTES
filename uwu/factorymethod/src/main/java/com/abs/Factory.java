package com.abs;

import com.models.Computador;

public interface Factory {
	public <T> T create(String name);
}
