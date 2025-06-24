package com.prograavanzada.especular;

import static com.prograavanzada.especular.MyGLRenderer.c;
import static com.prograavanzada.especular.MyGLRenderer.loadShader;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Cube {
    private static final int COORDS_PER_VERTEX = 3;
    private final FloatBuffer vertexBuffer;
    private final ShortBuffer shortBuffer;
    private final FloatBuffer normalBuffer;
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    private final int mProgram;

    private int positionHandle;
    private int colorHandle;
    private int uMVPMatrixHandle;
    private final int vertexCount;

    float[] color = {c(0), c(149), c(80), 1.0f};

    public void setColor(float[] color) {
        this.color = color;
    }

    // Vertex coordinates for a cube (8 vertices, 3 coords each)
    float[] coords = {
            -0.5f, -0.5f, -0.5f, //0
            0.5f, -0.5f, -0.5f,  //1
            0.5f,  0.5f, -0.5f,  //2
            -0.5f,  0.5f, -0.5f,  //3
            -0.5f, -0.5f,  0.5f, //4
            0.5f, -0.5f,  0.5f, //5
            0.5f,  0.5f,  0.5f,  //6
            -0.5f,  0.5f,  0.5f  //7
    };
    static float[] normals = {
            // Cara trasera (z = -0.5)
            0f, 0f, -1f,
            0f, 0f, -1f,
            0f, 0f, -1f,
            0f, 0f, -1f,

            // Cara frontal (z = 0.5)
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,

            // Cara inferior (y = -0.5)
            0f, -1f, 0f,
            0f, -1f, 0f,
            0f, -1f, 0f,
            0f, -1f, 0f,

            // Cara superior (y = 0.5)
            0f, 1f, 0f,
            0f, 1f, 0f,
            0f, 1f, 0f,
            0f, 1f, 0f,

            // Cara izquierda (x = -0.5)
            -1f, 0f, 0f,
            -1f, 0f, 0f,
            -1f, 0f, 0f,
            -1f, 0f, 0f,

            // Cara derecha (x = 0.5)
            1f, 0f, 0f,
            1f, 0f, 0f,
            1f, 0f, 0f,
            1f, 0f, 0f
    };
    static float[] generateNormals(float[] coords, short[] drawOrder) {
        float[] normals = new float[coords.length];

        for (int i = 0; i < drawOrder.length; i += 3) {
            int index0 = drawOrder[i] * 3;
            int index1 = drawOrder[i + 1] * 3;
            int index2 = drawOrder[i + 2] * 3;

            // Obtener posiciones de los 3 vértices del triángulo
            float x0 = coords[index0],     y0 = coords[index0 + 1],     z0 = coords[index0 + 2];
            float x1 = coords[index1],     y1 = coords[index1 + 1],     z1 = coords[index1 + 2];
            float x2 = coords[index2],     y2 = coords[index2 + 1],     z2 = coords[index2 + 2];

            // Calcular vectores del triángulo
            float ux = x1 - x0, uy = y1 - y0, uz = z1 - z0;
            float vx = x2 - x0, vy = y2 - y0, vz = z2 - z0;

            // Calcular la normal con producto cruzado
            float nx = uy * vz - uz * vy;
            float ny = uz * vx - ux * vz;
            float nz = ux * vy - uy * vx;

            // Normalizar la normal
            float length = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
            if (length != 0) {
                nx /= length;
                ny /= length;
                nz /= length;
            }

            // Asignar la normal a cada vértice del triángulo
            normals[index0]     += nx; normals[index0 + 1] += ny; normals[index0 + 2] += nz;
            normals[index1]     += nx; normals[index1 + 1] += ny; normals[index1 + 2] += nz;
            normals[index2]     += nx; normals[index2 + 1] += ny; normals[index2 + 2] += nz;
        }

        // Normalizar todas las normales acumuladas
        for (int i = 0; i < normals.length; i += 3) {
            float nx = normals[i];
            float ny = normals[i + 1];
            float nz = normals[i + 2];
            float length = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
            if (length != 0) {
                normals[i]     = nx / length;
                normals[i + 1] = ny / length;
                normals[i + 2] = nz / length;
            }
        }

        return normals;
    }

    // Drawing order for the cube's triangles
    private static final short[] drawOrder = {

            0, 1, 2, 0, 2, 3,
            // cara izquierda
            4, 5, 6, 4, 6, 7,
            // cara inferior
            0, 1, 5, 0, 5, 4,
            // cara superior
            2, 3, 7, 2, 7, 6,
            // cara trasera
            0, 3, 7, 0, 7, 4,
            // cara frontal
            1, 2, 6, 1, 6, 5
    };

    public Cube() {
        ByteBuffer vb = ByteBuffer.allocateDirect(coords.length * Float.BYTES).order(ByteOrder.nativeOrder());
        vertexBuffer = vb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        ByteBuffer nBuffer = ByteBuffer.allocateDirect(normals.length * Float.BYTES).order(ByteOrder.nativeOrder());
        normalBuffer = nBuffer.asFloatBuffer();
        normalBuffer.put(normals);
        normalBuffer.position(0);

        ByteBuffer db = ByteBuffer.allocateDirect(drawOrder.length * Short.BYTES).order(ByteOrder.nativeOrder());
        shortBuffer = db.asShortBuffer();
        shortBuffer.put(drawOrder);
        shortBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShadderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShadderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        vertexCount = drawOrder.length;
    }

    public void draw(float[] mvpMatrix, float[] mvMatrix, float[] lightPos) {
        GLES20.glUseProgram(mProgram);

        positionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        int normalHandle = GLES20.glGetAttribLocation(mProgram, "aNormal");
        int mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        int mvMatrixHandler = GLES20.glGetUniformLocation(mProgram, "uMVMatrix");
        int lightPositionHandle = GLES20.glGetUniformLocation(mProgram, "uLightPosition");
        colorHandle = GLES20.glGetUniformLocation(mProgram, "uColor");
        int shininessHandle = GLES20.glGetUniformLocation(mProgram, "uShininess");

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        GLES20.glEnableVertexAttribArray(normalHandle);
        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false, 3*4, normalBuffer);



        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);


        GLES20.glUniformMatrix4fv(mvMatrixHandler, 1, false, mvMatrix, 0);


        GLES20.glUniform3fv(lightPositionHandle, 1,lightPos,0);


        GLES20.glUniform4f(colorHandle, 1, 0.5f,0f,1f);

        GLES20.glUniform1f(shininessHandle, 64.0f);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, shortBuffer);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(normalHandle);
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