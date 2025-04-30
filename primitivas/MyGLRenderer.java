package com.prograavanzada.primitivas;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Point point;
    private  Line line;
    private  Line line2;

    Point p = new Point();
    int count = 0;
    public static float c(Integer value)
    {
        return ((float) value / 255);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(c(114), c(179), c(253), 1.0f);
        point = new Point();

        line2 = new Line(new float[]{
                        -1.0f, 1.0f,
                        0f, -1.0f
                });
        line = new Line(new float[]{
                -1.0f, 0.7f,
                -0.5f, -1.0f
        });

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        point.draw();
        line2.draw();
        line.draw();

    }
}
