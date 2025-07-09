package com.prograavanzada.spotlight;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] mvpMatrix = new float[16];
    private float[] modelMatrix = new float[16];
    private final float[] lightPos = {0,3,3};
    private final float[] lightDir = {0,-1,-1};
    private final float[] eyePos = {0,2,6};
    private Pyramid pyramid;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 1f, 0f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        pyramid = new Pyramid();
    }
    public static float c(int i) {
        return (float) i /255;
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 1, 10);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(viewMatrix, 0,
                eyePos[0], eyePos[1], eyePos[2],   // Cámara en (0,0,5)
                0f, 0f, 0f,   // Mirando hacia el origen
                0f, 1f, 0f);  // Eje Y hacia arriba

        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        pyramid.draw(mvpMatrix, modelMatrix, lightPos, lightDir, eyePos);

    }
}

