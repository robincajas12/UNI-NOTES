package com.prograavanzada.diffuselight;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Build;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] mvpMatrix = new float[16];

    private com.prograavanzada.diffuselight.Cube miCube;


    private float mPreviousX;
    private float mPreviousY;
    private float mDeltaX;
    private float mDeltaY;
    private float[] mRotationMatrix = new float[16];
    private float[] mAccumulatedRotation = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        miCube = new Cube();
        Matrix.setIdentityM(mRotationMatrix, 0);
        Matrix.setIdentityM(mAccumulatedRotation, 0);

        GLES20.glClearColor(0f, 0f, 0f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }
    public static float c(int i) {
        return (float) i /255;
    }
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 20);

    }
    float ange = 0;

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);




        Matrix.setLookAtM(viewMatrix, 0,
                0, 4, 12,
                0f, 0f, 0f,
                0f, 1f, 0f);

        float[] modelMatrix = new float[16];
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.multiplyMM(modelMatrix, 0, modelMatrix, 0, mAccumulatedRotation, 0);


        float[] viewModelMatrix = new float[16];
        Matrix.multiplyMM(viewModelMatrix, 0, viewMatrix, 0, modelMatrix, 0);


        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewModelMatrix, 0);

        //miCube.draw(mvpMatrix);
        updateRotation(1,1);
    }

    public void updateRotation(float dx, float dy) {

        float[] deltaRotationMatrix = new float[16];


        Matrix.setRotateM(deltaRotationMatrix, 0, dx * 0.5f, 0, 1, 0);
        Matrix.multiplyMM(mRotationMatrix, 0, deltaRotationMatrix, 0, mRotationMatrix, 0);


        Matrix.setRotateM(deltaRotationMatrix, 0, dy * 0.5f*new Random().nextInt(1), 1, 0, 0);
        Matrix.multiplyMM(mRotationMatrix, 0, deltaRotationMatrix, 0, mRotationMatrix, 0);


        Matrix.multiplyMM(mAccumulatedRotation, 0, mRotationMatrix, 0, mAccumulatedRotation, 0);
        Matrix.setIdentityM(mRotationMatrix, 0);
    }

    public static int loadShader(int type, String glShaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, glShaderCode);
        GLES20.glCompileShader(shader);
        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            String error = GLES20.glGetShaderInfoLog(shader);
            GLES20.glDeleteShader(shader);
            throw new RuntimeException("Error compilando shader: " + error);
        }
        return shader;
    }
}

