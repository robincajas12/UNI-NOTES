package com.prograavanzada.texturas3d;

import android.opengl.GLES20;

public class ShaderUtil {
    public static int loadShader(int type, String code) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, code);
        GLES20.glCompileShader(shader);
        return shader;
    }
    public static int createProgram(String vertexCoder, String fragmentCode)
    {
        int  vertexShander = loadShader(GLES20.GL_VERTEX_SHADER, vertexCoder);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentCode);
        int mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShander);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        return mProgram;
    }
}
