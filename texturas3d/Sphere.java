package com.prograavanzada.texturas3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Sphere {
    private final FloatBuffer vertexBuffer, textBuffer;
    private final ShortBuffer indexBuffer;
    private final int shaderProgram, textureId;
    private int positionHandle;
    private int texCoordHandle;
    private int textureUniformHandle;
    private int mvpMatrixHandle;

    private final int stacks = 20;  // Número de latitudes
    private final int slices = 20;  // Número de longitudes
    private final float radius = 0.5f;  // Radio de la esfera

    private float[] vertices;
    private float[] texCoords;
    private short[] indices;

    static final int COORDS_POR_VERTEX = 3;

    int[] textureIds = new int[1];

    private void loadTexture(Context context, int resourceId) {
        GLES20.glGenTextures(1, textureIds, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    public Sphere(Context context) {
        // Inicializamos los vértices, coordenadas de textura e índices
        createSphere();

        // Buffers
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer tb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tb.order(ByteOrder.nativeOrder());
        textBuffer = tb.asFloatBuffer();
        textBuffer.put(texCoords);
        textBuffer.position(0);

        ByteBuffer sb = ByteBuffer.allocateDirect(indices.length * 2);
        sb.order(ByteOrder.nativeOrder());
        indexBuffer = sb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

        // Shader Program
        shaderProgram = ShaderUtil.createProgram(vertexShaderCode, fragmentShaderCode);
        textureId = TextureUtils.loadTexture(context, R.drawable.texture2);

        positionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        texCoordHandle = GLES20.glGetAttribLocation(shaderProgram, "aTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");
        textureUniformHandle = GLES20.glGetUniformLocation(shaderProgram, "uTexture");
    }

    private void createSphere() {
        int numVertices = (slices + 1) * (stacks + 1);
        int numIndices = slices * stacks * 6; // Cada cara de la esfera tiene dos triángulos

        vertices = new float[numVertices * 3];
        texCoords = new float[numVertices * 2];
        indices = new short[numIndices];

        int vertexIndex = 0;
        int texCoordIndex = 0;
        int indexIndex = 0;

        for (int i = 0; i <= stacks; i++) {
            double phi = Math.PI * i / stacks;  // Ángulo latitudinal
            for (int j = 0; j <= slices; j++) {
                double theta = 2 * Math.PI * j / slices;  // Ángulo longitudinal

                // Vértices en coordenadas esféricas
                float x = (float) (radius * Math.sin(phi) * Math.cos(theta));
                float y = (float) (radius * Math.sin(phi) * Math.sin(theta));
                float z = (float) (radius * Math.cos(phi));

                vertices[vertexIndex++] = x;
                vertices[vertexIndex++] = y;
                vertices[vertexIndex++] = z;

                // Coordenadas de la textura
                texCoords[texCoordIndex++] = (float) j / slices;
                texCoords[texCoordIndex++] = (float) i / stacks;
            }
        }

        // Generación de los índices
        for (int i = 0; i < stacks; i++) {
            for (int j = 0; j < slices; j++) {
                short topLeft = (short) (i * (slices + 1) + j);
                short topRight = (short) (i * (slices + 1) + (j + 1));
                short bottomLeft = (short) ((i + 1) * (slices + 1) + j);
                short bottomRight = (short) ((i + 1) * (slices + 1) + (j + 1));

                // Primer triángulo
                indices[indexIndex++] = topLeft;
                indices[indexIndex++] = bottomLeft;
                indices[indexIndex++] = topRight;

                // Segundo triángulo
                indices[indexIndex++] = topRight;
                indices[indexIndex++] = bottomLeft;
                indices[indexIndex++] = bottomRight;
            }
        }
    }

    private int loadShader(int type, String code) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, code);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(shaderProgram);

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        GLES20.glUniform1i(textureUniformHandle, 0);

        GLES20.glEnableVertexAttribArray(texCoordHandle);
        GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, textBuffer);

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);

        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(texCoordHandle);
    }

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "attribute vec2 aTexCoord;" +
                    "varying vec2 vTexCoord;" +
                    "uniform mat4 uMVPMatrix;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  vTexCoord = aTexCoord;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "varying vec2 vTexCoord;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D(uTexture, vTexCoord);" +
                    "}";
}
