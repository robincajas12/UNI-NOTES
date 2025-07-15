package com.prograavanzada.transparencia;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGlSurfaceView extends GLSurfaceView {
    private final MyGLRenderer renderer = new MyGLRenderer();
    public MyGlSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(renderer);
    }
}