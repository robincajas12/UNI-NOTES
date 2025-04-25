package com.prograavanzada.primitivas;
import static com.prograavanzada.primitivas.MyGLRenderer.c;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import com.prograavanzada.primitivas.MyGLRenderer;
public class Point {
    private final FloatBuffer vertexBuffer;
    private final int mProgram;
    private int positionHandle;
    private int colorHandle;
    static final int COORDS_FOR_VERTEX = 3;
    static float[] pointCoord = {0.0f,0.0f,0.0f};
    private final int vertexCount = Point.pointCoord.length/COORDS_FOR_VERTEX;
    private final int vertexStride = COORDS_FOR_VERTEX * 4;
    private float color[] = {c(246),c(149), c(80),1.0f};

    public Point()
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertexCount);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(pointCoord);
        vertexBuffer.position(0);
    }

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main(){" +
                    ""+
                    "}";
    private final String fragmentShaderCode = "";
}
