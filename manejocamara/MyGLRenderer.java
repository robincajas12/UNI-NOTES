package com.prograavanzada.manejocamara;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle triangleF;
    private final float[] mProyectionMatriz = new float[16]; // proyecciones
    private final float[] mViewMatriz = new float[16]; // vista de camara
    private final float[] mVPMatriz = new float[16]; // proy x vista
    int count = 0;
    public static float c(Integer value)
    {
        return ((float) value / 255);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(c(25), c(100), c(80), 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST); // profundidad;
        triangleF = new Triangle(new float[]
                {
                        -1f,1f,0.0f,
                        -1f,-1f,0.0f,
                        1f,0f,0.0f
                });
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float radio = (float) width/height; // aspect radio
        Matrix.frustumM(mProyectionMatriz, 0, -radio, radio, -1 , 1, 1,10); // perspectiva
    }
    float a = 1;
    float b = 1;
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        // Variables para el movimiento circular
        float radio = 3f; // Radio del círculo en el que la cámara gira
        float velocidad = 0.01f; // Velocidad de rotación (cuánto cambia el ángulo en cada frame)

        // Incrementamos el ángulo para hacer que la cámara gire
        float angle = count * velocidad; // Usamos 'count' para obtener un ángulo creciente

        // Calculamos las nuevas posiciones 'x' y 'z' usando trigonometría para el círculo
        float x = (float) (radio * Math.cos(angle)); // 'x' basado en coseno
        float z = (float) (radio * Math.sin(angle)); // 'z' basado en seno

        // 'b' puede ser la altura constante de la cámara (si quieres que gire a nivel del suelo, usa '0')
        float b = 1f;

        // Configuración de la cámara en movimiento circular
        Matrix.setLookAtM(
                mViewMatriz, 0,
                x, b, z,  // Cámara moviéndose alrededor del origen
                0f, 0f, 0f, // El centro de la cámara sigue el origen
                0f, 1f, 0f  // El vector 'up' es el eje Y positivo
        );

        // Actualizamos la matriz de proyección + vista (MVP)
        Matrix.multiplyMM(mVPMatriz, 0, mProyectionMatriz, 0, mViewMatriz, 0);

        // Dibuja el triángulo
        triangleF.draw(mVPMatriz);

        // Incrementar el ángulo en cada frame
        count++;  // 'count' va au

    }
    static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
