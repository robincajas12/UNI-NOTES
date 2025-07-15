package com.prograavanzada.transparencia;

import static com.prograavanzada.transparencia.MyGLRenderer.c;
import static com.prograavanzada.transparencia.MyGLRenderer.loadShader;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Cube {
    private static final int COORDS_PER_VERTEX = 3;
    private final FloatBuffer vertexBuffer;
    private final ShortBuffer shortBuffer;

    private final int vertexStride = COORDS_PER_VERTEX * 4;
    private final int mProgram;

    private int positionHandle;
    private int colorHandle;
    private int uMVPMatrixHandle;
    private final int vertexCount;

    float[] color = {c(150), c(149), c(80), 0.1f};

    public void setColor(float[] color) {
        this.color = color;
    }

    // Vertex coordinates for a cube (8 vertices, 3 coords each)
    float[] coords = {
            -0.5f, -0.5f, -0.5f, //0
            0.5f, -0.5f, -0.5f,  //1
            0.5f,  0.5f, -0.5f,  //2
            -0.5f,  0.5f, -0.5f,  //3
            -0.5f, -0.5f,  0.5f, //4
            0.5f, -0.5f,  0.5f, //5
            0.5f,  0.5f,  0.5f,  //6
            -0.5f,  0.5f,  0.5f  //7
    };

    // Orden de dibujo para las caras del cubo (dos triángulos por cara)
    private static final short[] drawOrder = {
            0, 1, 2, 0, 2, 3, // Cara frontal
            4, 5, 6, 4, 6, 7, // Cara trasera
            0, 1, 5, 0, 5, 4, // Cara inferior
            2, 3, 7, 2, 7, 6, // Cara superior
            0, 3, 7, 0, 7, 4, // Cara izquierda
            1, 2, 6, 1, 6, 5  // Cara derecha
    };


    public Cube() {
        ByteBuffer vb = ByteBuffer.allocateDirect(coords.length * Float.BYTES).order(ByteOrder.nativeOrder());
        vertexBuffer = vb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);


        ByteBuffer db = ByteBuffer.allocateDirect(drawOrder.length * Short.BYTES).order(ByteOrder.nativeOrder());
        shortBuffer = db.asShortBuffer();
        shortBuffer.put(drawOrder);
        shortBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShadderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShadderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        vertexCount = drawOrder.length;
    }

    public void draw(float[] mvpMatrix, float[] mvMatrix, float[] lightPos) {
        GLES20.glUseProgram(mProgram);

        positionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        int normalHandle = GLES20.glGetAttribLocation(mProgram, "aNormal");
        int mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        int mvMatrixHandler = GLES20.glGetUniformLocation(mProgram, "uMVMatrix");
        int lightPositionHandle = GLES20.glGetUniformLocation(mProgram, "uLightPosition");
        colorHandle = GLES20.glGetUniformLocation(mProgram, "uColor");
        int shininessHandle = GLES20.glGetUniformLocation(mProgram, "uShininess");

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        GLES20.glEnableVertexAttribArray(normalHandle);



        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);


        GLES20.glUniformMatrix4fv(mvMatrixHandler, 1, false, mvMatrix, 0);


        GLES20.glUniform3fv(lightPositionHandle, 1,lightPos,0);


        GLES20.glUniform4fv(colorHandle, 1,color, 0 );

        GLES20.glUniform1f(shininessHandle, 64.0f);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, shortBuffer);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(normalHandle);
    }


    private final String vertexShadderCode =
            "uniform mat4 uMVPMatrix;" +
                    "uniform mat4 uMVMatrix;" +
                    "attribute vec4 aPosition;" +
                    "attribute vec3 aNormal;" +
                    "varying vec3 vPosition;" +
                    "varying vec3 vNormal;" +
                    "void main() {" +
                    "gl_Position = uMVPMatrix * aPosition;" +
                    "}";

    private final String fragmentShadderCode =
            "precision mediump float;" +
                    "uniform vec4 uColor;" +
                    "void main() {" +
                    "  gl_FragColor = uColor;" +
                    "}";

}