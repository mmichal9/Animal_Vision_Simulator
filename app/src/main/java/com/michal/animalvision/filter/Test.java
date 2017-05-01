/*
 * Created by Michal on 06/03/2017.
 */
package com.michal.animalvision.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.michal.animalvision.MyGLUtils;
import com.michal.animalvision.R;

/**
 * Created by Michal on 06/03/2017.
 */

public class Test extends CameraFilter {
    private int program;
    private int texture2Id;

    public Test(Context context) {
        super(context);

        // Build shaders
        program = MyGLUtils.buildProgram(context, R.raw.vertext, R.raw.f_sepia);

        // Load the texture will need for the shader
        texture2Id = MyGLUtils.loadTexture(context, R.raw.clam4, new int[2]);
    }

    @Override
    public void onDraw(int cameraTexId, int canvasWidth, int canvasHeight) {
        setupShaderInputs(program,
                new int[]{canvasWidth, canvasHeight},
                new int[]{cameraTexId, texture2Id},
                new int[][]{});
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}

