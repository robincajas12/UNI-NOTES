package com.prograavanzada.texturas3d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private final float[] vpMatrix = new float[16];
    private final float[] projMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    float rotationAngle = 0.0f;
    private CuadradoTexturizado cuadradoTexturizado;
    private Context context;
    Cube miCube;
    Sphere sphere;

    public MyGLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 0.0f, 0.0f, 1f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        //cuadradoTexturizado = new CuadradoTexturizado(context,R.drawable.texture);
        miCube= new Cube(context);
        sphere = new Sphere(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(projMatrix, 0, -ratio, ratio, -1, 1, 2, 10);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(viewMatrix, 0,
                2f, 2f, 5f,
                0f, 0f, 0f,
                0f, 1f, 0f);

        Matrix.multiplyMM(vpMatrix, 0, projMatrix, 0, viewMatrix, 0);
        rotationAngle += 0.25f;
        Matrix.rotateM(vpMatrix, 0,40, 1f,0f,0f);
        Matrix.rotateM(vpMatrix, 0,rotationAngle, 0f,0f,1f);
       // miCube.draw(vpMatrix);
        sphere.draw(vpMatrix);
    }
}
