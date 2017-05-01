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

public class HueShift extends CameraFilter {
    private int program;

    public HueShift(Context context) {
        super(context);

        // Build shaders
        program = MyGLUtils.buildProgram(context, R.raw.vertext, R.raw.f_hueshift);
    }

    @Override
    public void onDraw(int cameraTexId, int canvasWidth, int canvasHeight) {
        setupShaderInputs(program,
                new int[]{canvasWidth, canvasHeight},
                new int[]{cameraTexId},
                new int[][]{});
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
