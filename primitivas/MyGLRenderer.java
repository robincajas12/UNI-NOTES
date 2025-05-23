package com.prograavanzada.primitivas;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Point point;
    private  Line line;
    private Triangle triangle;
    Point p = new Point();
    private Square square;
    private Circle circle;
    int count = 0;
    public static float c(Integer value)
    {
        return ((float) value / 255);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(c(114), c(179), c(253), 1.0f);
        triangle = new Triangle();
        square = new Square();
        circle = new Circle(0.5f, 5);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        circle.draw();


    }
    static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
