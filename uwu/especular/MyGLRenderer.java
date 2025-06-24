package com.prograavanzada.especular;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] mvpMatrix = new float[16];
    private final float[] mvMatrix = new float[16];
    float[] modelMatrix = new float[16];
    float[] lightPos = {0,0,3};

    private Cube miCube;


    private float mPreviousX;
    private float mPreviousY;
    private float mDeltaX;
    private float mDeltaY;
    private float[] mRotationMatrix = new float[16];
    private float[] mAccumulatedRotation = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 1f, 0f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        miCube = new Cube();
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

    private float rotationAngle = 0.0f; // Ángulo inicial de rotación

    @Override
    public void onDrawFrame(GL10 gl) {
        // Limpiar el buffer de color y profundidad
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Actualizar el ángulo de rotación (esto puede ser dinámico si lo deseas)
        rotationAngle += 0.5f;  // Ajusta el valor según lo rápido que quieras que gire el cubo

        // Crear una matriz de vista (cámara)
        Matrix.setLookAtM(viewMatrix, 0,
                0f, 0f, 5f,   // Cámara en (0,0,5)
                0f, 0f, 0f,   // Mirando hacia el origen
                0f, 1f, 0f);  // Eje Y hacia arriba

        // Crear la matriz de rotación
        float[] rotationMatrix = new float[16];
        Matrix.setIdentityM(rotationMatrix, 0);
        Matrix.rotateM(rotationMatrix, 0, rotationAngle, 0f, 1f, 0f); // Rotación alrededor del eje Y

        // Inicializar la matriz de modelo (mvMatrix)
        Matrix.setIdentityM(mvMatrix, 0);

        // Multiplicar la matriz de vista con la matriz de rotación
        Matrix.multiplyMM(mvMatrix, 0, viewMatrix, 0, rotationMatrix, 0);

        // Multiplicar la matriz de proyección con la matriz de modelo (mvMatrix)
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvMatrix, 0);

        // Dibujar el cubo con la matriz de transformación final
        miCube.draw(mvpMatrix, mvMatrix, lightPos);
    }


    private void updateRotation(float angleX, float angleY) {
        // Rotación sobre el eje X
        Matrix.setRotateM(modelMatrix, 0, angleX, 1f, 0f, 0f);


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

