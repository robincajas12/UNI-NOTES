package com.prograavanzada.texturas3d;

import static com.prograavanzada.texturas3d.CuadradoTexturizado.texCoords;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Cube {
    private final FloatBuffer vertexBuffer,textBuffer;
    private final ShortBuffer indexBuffer;
    private final int shaderProgram, textureId;
    private int positionHandle;
    private int texCoordHandle;
    private int textureUniformHandle;
    private int mvpMatrixHandle;
    private final float[] vertices = {
            // Frente
            -0.5f,  0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            -0.5f, -0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,

            // Atrás
            0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,

            // Izquierda
            -0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f,  0.5f,

            // Derecha
            0.5f,  0.5f,  0.5f,
            0.5f,  0.5f, -0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f, -0.5f, -0.5f,

            // Arriba
            -0.5f,  0.5f, -0.5f,
            0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,

            // Abajo
            -0.5f, -0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f
    };

    private final float[] texCoords = {
            // Frente
            0f, 0f,   1f, 0f,   0f, 1f,   1f, 1f,
            // Atrás
            0f, 0f,   1f, 0f,   0f, 1f,   1f, 1f,
            // Izquierda
            0f, 0f,   1f, 0f,   0f, 1f,   1f, 1f,
            // Derecha
            0f, 0f,   1f, 0f,   0f, 1f,   1f, 1f,
            // Arriba
            0f, 0f,   1f, 0f,   0f, 1f,   1f, 1f,
            // Abajo
            0f, 0f,   1f, 0f,   0f, 1f,   1f, 1f
    };

    private final short[] indices = {
            0,  1,  2,   2,  1,  3,   // Frente
            4,  5,  6,   6,  5,  7,   // Atrás
            8,  9, 10,  10,  9, 11,   // Izquierda
            12, 13, 14,  14, 13, 15,   // Derecha
            16, 17, 18,  18, 17, 19,   // Arriba
            20, 21, 22,  22, 21, 23    // Abajo
    };
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
    public Cube(Context context) {
        ByteBuffer bb = ByteBuffer.allocateDirect(this.vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(this.vertices);
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


        shaderProgram = ShaderUtil.createProgram(vertexShaderCode, fragmentShaderCode);
        textureId = TextureUtils.loadTexture(context, R.drawable.texture);
        positionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
         texCoordHandle = GLES20.glGetAttribLocation(shaderProgram, "aTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");

        textureUniformHandle = GLES20.glGetUniformLocation(shaderProgram, "uTexture");
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
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);




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

