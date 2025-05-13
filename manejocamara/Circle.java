package com.prograavanzada.manejocamara;

import static com.prograavanzada.manejocamara.MyGLRenderer.c;
import static com.prograavanzada.manejocamara.MyGLRenderer.loadShader;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Circle {
    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int positionHandle;
    private int colorHandle;
    private static final int COORDS_PER_VERTEX = 3;
    float[] color = {c(246),c(149), c(80),1.0f};
    private final int vertexCount;
    private final int vexterStride = COORDS_PER_VERTEX * 4;

    private float[] circleCoords;

    public Circle(float r, int n, float[] coordCenter)
    {

        circleCoords = createCircleCoords(r,n, coordCenter);
        vertexCount = circleCoords.length/COORDS_PER_VERTEX;
        ByteBuffer buffer = ByteBuffer.allocateDirect(circleCoords.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(circleCoords);
        vertexBuffer.position(0);
        int idShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int idFragment = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, idShader);
        GLES20.glAttachShader(mProgram, idFragment);
        GLES20.glLinkProgram(mProgram);



    }
    public Circle(float r, int n, float[] coordCenter, float[] color)
    {
        this.color = color;
        circleCoords = createCircleCoords(r,n, coordCenter);
        vertexCount = circleCoords.length/COORDS_PER_VERTEX;
        ByteBuffer buffer = ByteBuffer.allocateDirect(circleCoords.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(circleCoords);
        vertexBuffer.position(0);
        int idShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int idFragment = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, idShader);
        GLES20.glAttachShader(mProgram, idFragment);
        GLES20.glLinkProgram(mProgram);



    }

    private float[] createCircleCoords(float r, int n, float[] coordCenter) {
        List<Float> coords = new ArrayList<>();
        coords.add(coordCenter[0]); // centro x
        coords.add(coordCenter[1]); // centro y
        coords.add(0.0f); // centro z
        float angle = (float) ((2*Math.PI)/n);
        for(int i = 0; i <= n; i++)
        {
            double angle2 = angle*i;
            coords.add((float) (coordCenter[0] +  (r * Math.cos(angle2)))); // x
            coords.add((float) (coordCenter[1] + (r * Math.sin(angle2)))); // y
            coords.add(0.0f); // z

        }
        float[] coordsArray = new float[coords.size()];
        for (int i = 0; i < coords.size(); i++) {
            coordsArray[i] = coords.get(i); // Asignar cada elemento
        }
        return coordsArray;

    }

    public  void draw()
    {
        GLES20.glUseProgram(mProgram);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(
                positionHandle,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vexterStride ,
                vertexBuffer);

        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
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
