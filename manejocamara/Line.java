package com.prograavanzada.manejocamara;

import static com.prograavanzada.manejocamara.MyGLRenderer.c;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Line {
    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int positionHandle;
    private int colorHandle;
    static float[] coordsLine = {
            -1.0f,1.0f,
            1.0f,-1.0f
    };
    private static final int COORDS_FOR_VERTEX = 2;
    private final int vertexCount = coordsLine.length/COORDS_FOR_VERTEX;
    float[] color = {c(246),c(149), c(80),1.0f};
    public Line(float[] coords)
    {
        ByteBuffer myByteBuffer = ByteBuffer.allocateDirect(coordsLine.length * 4);
        myByteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = myByteBuffer.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        int vertexShaderId = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int idFragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShaderId);
        GLES20.glAttachShader(mProgram, idFragmentShader);
        GLES20.glLinkProgram(mProgram);
    }
    public void draw()
    {
        GLES20.glUseProgram(mProgram);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 2,GLES20.GL_FLOAT, false, 0, vertexBuffer);
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glLineWidth(10);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
    private int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;

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
