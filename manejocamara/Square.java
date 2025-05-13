package com.prograavanzada.manejocamara;

import static com.prograavanzada.manejocamara.MyGLRenderer.c;
import static com.prograavanzada.manejocamara.MyGLRenderer.loadShader;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {
    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int positionHandle;
    private int colorHandle;
    private static final int COORDS_PER_VERTEX = 3;
    static float[] color = {c(246),c(149), c(80),1.0f};
    static float[] coords = {
            -0.5f,0.8f,0f,
            -0.5f,0.2f,0f,
            0.1f,0.2f,0f,
            0.1f,0.8f,0f
    };
    private final short drawOrder[] = {0,1,2,0,2,3};
    private final ShortBuffer shortBuffer;
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    public  Square(float[] coords)
    {

        ByteBuffer buffer = ByteBuffer.allocateDirect(coords.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);


        ByteBuffer sb = ByteBuffer.allocateDirect(drawOrder.length * 4);
        sb.order(ByteOrder.nativeOrder());
        shortBuffer = sb.asShortBuffer();
        shortBuffer.put(drawOrder);
        shortBuffer.position(0);


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
        GLES20.glVertexAttribPointer(
                positionHandle,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                COORDS_PER_VERTEX * 4 ,
                vertexBuffer);

        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES,
                drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT,
                shortBuffer);
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
