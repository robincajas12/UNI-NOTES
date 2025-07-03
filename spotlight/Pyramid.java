package com.prograavanzada.spotlight;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Pyramid {
    private final FloatBuffer vertexBuffer;
    private final int program;
    private final float[] vertices = {
            -1,0,-1,    0,-1,0,
            1,0,-1,      0,-1,0,
            1,0,1,      0,-1,0,
            -1,0,1,      0,-1,0,

            -1,0,-1,    -0.5f, 0.5f, -0.5f,
            1,0,-1,    0.5f, 0.5f, -0.5f,
            0,1,0,    0f, 0.5f, 0f,
            1,0,-1,    0.5f, 0.5f, -0.5f,
            1,0,1,    0.5f, 0.5f, 0.5f,
            -1,0,1,    -0.5f, 0.5f, 0.5f,
            1,0,1,    0.5f, 0.5f, 0.5f,
            -1,0,1,     -0.5f,0.5f,0.5f,
            0,1,0,      0,0.5f,0,
            -1,0,1      -0.5f,0.5f,0.5f,
            -1,0,-1      -0.5f,0.5f,-0.5f,
            0,1,0 ,     0,0.5f,0f,
    };
    public Pyramid()
    {
        ByteBuffer vb = ByteBuffer.allocateDirect(vertices.length * Float.BYTES).order(ByteOrder.nativeOrder());
        vertexBuffer = vb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        program = ShaderUtils.createProgram(vertexShadderCode, fragmentShadderCode);

    }
    public void draw(float[] mvpMatrix,
                     float[] modelMatrix,
                     float[] lightPos,
                     float[] lightDir,
                     float[] eyePos) {
        GLES20.glUseProgram(program);

    }

    private final String vertexShadderCode =
            "uniform mat4 uMVPMatrix;" +
                    "uniform mat4 uMVMatrix;" +
                    "attribute vec4 aPosition;" +
                    "attribute vec3 aNormal;" +
                    "varying vec3 vPosition;" +
                    "varying vec3 vNormal;" +
                    "void main() {" +
                    "vPosition = vec3(uMVMatrix * aPosition);" +
                    "vNormal = normalize(vec3(uMVMatrix * vec4(aNormal, 0.0)));" +
                    "gl_Position = uMVPMatrix * aPosition;" +
                    "}";

    private final String fragmentShadderCode =
            "precision mediump float;" +
                    "uniform vec3 uLightPos;" +
                    "uniform vec4 uColor;" +
                    "uniform float uShininess;" +
                    "varying vec3 vPosition;" +
                    "varying vec3 vNormal;" +
                    "void main() {" +
                    "vec3 lightDir = normalize(uLightPos - vPosition);" +
                    "vec3 viewDir = normalize(-vPosition);" +
                    "vec3 reflectDir = reflect(-lightDir, normalize(vNormal));" +
                    "float spec = pow(max(dot(viewDir, reflectDir), 0.0), uShininess);" +
                    "vec4 specular = uColor * spec;" +
                    "  gl_FragColor = specular;" +
                    "}";


}
