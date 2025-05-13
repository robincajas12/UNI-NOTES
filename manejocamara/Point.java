package com.prograavanzada.manejocamara;

import static com.prograavanzada.manejocamara.MyGLRenderer.c;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
public class Point {
    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int positionHandle;
    private int colorHandle;
    static final int COORDS_FOR_VERTEX = 3;
    static float[] pointCoord = {0.0f,0.0f,0.0f};
    private final int vertexCount = Point.pointCoord.length/COORDS_FOR_VERTEX;
    private final int vertexStride = COORDS_FOR_VERTEX * 4;
    private float color[] = {c(246),c(149), c(80),1.0f};


    public Point(float[] coords)
    {
       // ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertexCount);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Point.pointCoord.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);
        int vertexShaderId = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int idFragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShaderId);
        GLES20.glAttachShader(mProgram, idFragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    private int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
    public void draw()
    {
        GLES20.glUseProgram(mProgram);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer(
                positionHandle,
                COORDS_FOR_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer);
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);


    }
    private final String vertexShaderCode =
                    "attribute vec4 vPosition;" +
                    "void main(){" +
                            "gl_Position= vPosition;" +
                            "gl_PointSize = 100.0;" +
                    "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main(){" +
                    "gl_FragColor = vColor;" +
            "}";
}
