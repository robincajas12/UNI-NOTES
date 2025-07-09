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
                -1,0,1,      -0.5f,0.5f,0.5f,
                -1,0,-1,      -0.5f,0.5f,-0.5f,
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
            int positionHandle = GLES20.glGetAttribLocation(program, "aPosition");
            int normalHandle = GLES20.glGetAttribLocation(program, "aNormal");

            vertexBuffer.position(0);
            GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false,6 * Float.BYTES,vertexBuffer );
            GLES20.glEnableVertexAttribArray(positionHandle);
            vertexBuffer.position(3);
            GLES20.glVertexAttribPointer(normalHandle, 3 , GLES20.GL_FLOAT,false,6 * Float.BYTES, vertexBuffer);

            GLES20.glEnableVertexAttribArray(normalHandle);

            GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(program, "uMVPMatrix"), 1, false, mvpMatrix, 0);
            GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(program, "uMVMatrix"), 1, false, modelMatrix, 0);

            GLES20.glUniform3fv(GLES20.glGetUniformLocation(program, "uLightPos" ), 1,  lightPos,0);
            GLES20.glUniform3fv(GLES20.glGetUniformLocation(program, "uLightDir" ), 1, lightDir, 0);
            GLES20.glUniform3fv(GLES20.glGetUniformLocation(program, "uEyePos" ), 1, eyePos, 0);
            GLES20.glUniform1f(GLES20.glGetUniformLocation(program, "uCutOff" ), (float) Math.cos(Math.toRadians(30)));

            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,4);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 4, 12);

        }

        private final String vertexShadderCode =
                "uniform mat4 uMVPMatrix;" +
                        "uniform mat4 uMVMatrix;" +
                        "attribute vec3 aPosition;" +
                        "attribute vec3 aNormal;" +
                        "varying vec3 vPosition;" +
                        "varying vec3 vNormal;" +
                        "void main() {" +
                        "vPosition = vec3(uMVMatrix * vec4(aPosition,1.0));" +
                        "vNormal = mat3(uMVMatrix) * aNormal;" +
                        "gl_Position = uMVPMatrix * vec4(aPosition,1.0);" +
                        "}";

        private final String fragmentShadderCode =
                "precision mediump float;" +
                        "uniform vec3 uLightPos;" +
                        "uniform vec3 uLightDir;"+
                        "uniform vec3 uEyePos;" +
                        "uniform float uCutOff;" +
                        "varying vec3 vPosition;" +
                        "varying vec3 vNormal;" +
                        "void main() {" +
                        "vec3 normalN = normalize(vNormal);"+
                        "vec3 lightDir = normalize(uLightPos - vPosition);" +
                        "float theta = dot(lightDir,normalize(-uLightDir));"+
                        "vec3 ambient = vec3(0.1);"+
                        "vec3 diffuse = vec3(0.0);"+
                        "vec3 specular = vec3(0.0);"+
                        "if (theta > uCutOff) {" +
                        "float diff = max(dot(normalN, lightDir), 0.0);" +
                        "diffuse = diff * vec3(1.0, 0.7, 0.3);" +
                        "vec3 viewDir = normalize(uEyePos - vPosition);" +
                        "vec3 reflectDir = reflect(-lightDir, normalN);" +
                        "float spec = pow(max(dot(viewDir, reflectDir), 0.0), 16.0);" +
                        "specular = spec * vec3(1.0);" +
                        "}" +
                        "vec3 result = ambient + diffuse + specular;"+
                        "gl_FragColor = vec4(result, 1.0);" +
                        "}";


    }
