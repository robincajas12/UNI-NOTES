package com.prograavanzada.primitivas;

import static com.prograavanzada.primitivas.MyGLRenderer.c;

import java.nio.FloatBuffer;

public class Square {
    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int positionHandle;
    private int colorHandle;
    private static final int COORDS_PER_VERTEX = 3;
    static float[] color = {c(246),c(149), c(80),1.0f};
    static float[] coords = {
            -.5f,8f,0f,
            -.5f,0.2f,0f,
            0.1f,0.2f,0f,
            0.1f,0.8f,0f
    };
    private final short drawOrder[] = {0,1,2,0,2,3};
}
