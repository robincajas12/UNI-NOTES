package com.prograavanzada.spotlight;

import android.opengl.GLES20;

public class ShaderUtils {
    public static int createProgram(String vertexShadderCode, String fragmentShadderCode)
    {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShadderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShadderCode);

        int mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        return mProgram;
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
