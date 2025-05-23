package com.prograavanzada.primitivas;

import static com.prograavanzada.primitivas.MyGLRenderer.c;
import static com.prograavanzada.primitivas.MyGLRenderer.loadShader;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    private final int mProgram;
    private int positionHandle;
    private int colorHandle;
    private static final int COORDS_FOR_VERTEX = 3;
    private final float[] coords = {
            -0.5f,0.0f,0.0f,
            -0.5f,-0.75f,0.0f,
            0.5f,-0.75f,0.0f
    };
    FloatBuffer vertexBuffer;
    private final int vertexCount = coords.length/COORDS_FOR_VERTEX;
    private final int vertexStride = COORDS_FOR_VERTEX * 4;
    float[] color = {c(246),c(149), c(80),1.0f};

    Triangle()
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(coords.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);
        int idShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int idFragment = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, idShader);
        GLES20.glAttachShader(mProgram, idFragment);
        GLES20.glLinkProgram(mProgram);

    }

    public void draw()
    {
        GLES20.glUseProgram(mProgram);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_FOR_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);

    }


    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main(){" +
                    "gl_Position= vPosition;" +
                    "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main(){" +
                    "gl_FragColor = vColor;" +
                    "}";
}
