package com.prograavanzada.manejocamara;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle triangleF;
    private final float[] mProyectionMatriz = new float[16];
    private final float[] mViewMatriz = new float[16];
    private final float[] mVPMatriz = new float[16]; // proy x vista
    int count = 0;
    public static float c(Integer value)
    {
        return ((float) value / 255);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(c(0), c(0), c(0), 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST); // profundidad;
        triangleF = new Triangle(new float[]
                {
                        -0.0f,0.8f,0f,
                        -0.0f,0.70f,0f,
                        0.5f,0.75f,0.0f
                });
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float radio = (float) width/height; // aspect radio
        Matrix.frustumM(mProyectionMatriz, 0, -radio, radio, -1 , 1, 1,10);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        triangleF.draw();



    }
    static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
