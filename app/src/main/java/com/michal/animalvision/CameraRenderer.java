/*
 * Copyright 2016 nekocode
 * @author nekocode (nekocode.cn@gmail.com)
 *
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package com.michal.animalvision;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.TextureView;

import java.io.IOException;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

import com.michal.animalvision.filter.Ape;
import com.michal.animalvision.filter.Bear;
import com.michal.animalvision.filter.Bee;
import com.michal.animalvision.filter.Bird;
import com.michal.animalvision.filter.BlackandWhite;
import com.michal.animalvision.filter.CameraFilter;
import com.michal.animalvision.filter.Cat;
import com.michal.animalvision.filter.Cb_Achromatomaly;
import com.michal.animalvision.filter.Cb_Achromatospia;
import com.michal.animalvision.filter.Cb_Deuteranomaly;
import com.michal.animalvision.filter.Cb_Deuteranopia;
import com.michal.animalvision.filter.Cb_Protanomaly;
import com.michal.animalvision.filter.Cb_Tritanopia;
import com.michal.animalvision.filter.Clam;
import com.michal.animalvision.filter.Cuttlefish;
import com.michal.animalvision.filter.DogVision;
import com.michal.animalvision.filter.Dolphin;
import com.michal.animalvision.filter.FishVision;
import com.michal.animalvision.filter.Gecko;
import com.michal.animalvision.filter.HSVtoRGB;
import com.michal.animalvision.filter.Horse;
import com.michal.animalvision.filter.InsectVision;
import com.michal.animalvision.filter.InvertVision;
import com.michal.animalvision.filter.JumpingSpider;
import com.michal.animalvision.filter.Octopus;
import com.michal.animalvision.filter.OriginalFilter;
import com.michal.animalvision.filter.Cb_Protanopia;
import com.michal.animalvision.filter.Shark;
import com.michal.animalvision.filter.Snail;
import com.michal.animalvision.filter.Test;
import com.michal.animalvision.filter.Thermal;
import com.michal.animalvision.filter.Cb_Tritanomaly;
import com.michal.animalvision.filter.VR_DogVision;

/**
 * Created by Michal on 06/03/2017.
 * Runnable, TextureView.SurfaceTextureListener
 * public static interface TextureView.SurfaceTextureListener 
 */
public class CameraRenderer implements Runnable, TextureView.SurfaceTextureListener {
    private static final String TAG = "CameraRenderer";
    private static final int EGL_OPENGL_ES2_BIT = 4;
    private static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;
    private static final int DRAW_INTERVAL = 1000 / 30;

    private Thread renderThread;
    private Context context;
    private SurfaceTexture surfaceTexture;
    private int gwidth, gheight;

    private EGLDisplay eglDisplay;
    private EGLSurface eglSurface;
    private EGLContext eglContext;
    private EGL10 egl10;

    private Camera camera;
    private SurfaceTexture cameraSurfaceTexture;
    private int cameraTextureId;
    private CameraFilter selectedFilter;
    private int selectedFilterId = R.id.filter0;
    private SparseArray<CameraFilter> cameraFilterMap = new SparseArray<>();

    private String cameraOrient  = "Back";

    public CameraRenderer(Context context) {
        this.context = context;

    }

    public void setCameraOrient(String cameraOrient) {
        this.cameraOrient = cameraOrient;

        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        if (renderThread != null && renderThread.isAlive()) {
            renderThread.interrupt();
        }

        renderThread = new Thread(this);
        //Open camera
        if(cameraOrient.equals("Back")){
            Pair<Camera.CameraInfo, Integer> camerainfo = getBackCamera();
            final int CameraId = camerainfo.second;
            camera = Camera.open(CameraId);
        }
        if(cameraOrient.equals("Front")){
            Pair<Camera.CameraInfo, Integer> camerainfo = getFrontCamera();
            final int CameraId = camerainfo.second;
            camera = Camera.open(CameraId);
        }

        // Start rendering
        renderThread.start();

    }

    public String getCameraOrient() {
        return cameraOrient;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {




    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        gwidth = -width;
        gheight = -height;
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        if (renderThread != null && renderThread.isAlive()) {
            renderThread.interrupt();
        }
        CameraFilter.release();

        return true;
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (renderThread != null && renderThread.isAlive()) {
            renderThread.interrupt();
        }
        renderThread = new Thread(this);

        surfaceTexture = surface;

        gwidth = -width;
        gheight = -height;

        //Open camera
        if(cameraOrient.equals("Back")){
            Pair<Camera.CameraInfo, Integer> camerainfo = getBackCamera();
            final int CameraId = camerainfo.second;
            camera = Camera.open(CameraId);
        }
        if(cameraOrient.equals("Front")){
            Pair<Camera.CameraInfo, Integer> camerainfo = getFrontCamera();
            final int CameraId = camerainfo.second;
            camera = Camera.open(CameraId);
        }

        // Start rendering
        renderThread.start();
    }

    public void setSelectedFilter(int id) {
        selectedFilterId = id;
        selectedFilter = cameraFilterMap.get(id);
        if (selectedFilter != null)
            selectedFilter.onAttach();
    }




    // original code !!!!!!!!!!!!!!!!!!!!!!11

//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        if (renderThread != null && renderThread.isAlive()) {
//            renderThread.interrupt();
//        }
//        renderThread = new Thread(this);
//
//        surfaceTexture = surface;
//
//        gwidth = -width;
//        gheight = -height;
//
//        // Open camera
//        Pair<Camera.CameraInfo, Integer> camerainfo = getBackCamera();
//        final int CameraId = camerainfo.second;
//        camera = Camera.open(CameraId);
//
//
//
//        // Start rendering
//        renderThread.start();
//    }
//
//    public void setSelectedFilter(int id) {
//        selectedFilterId = id;
//        selectedFilter = cameraFilterMap.get(id);
//        if (selectedFilter != null)
//            selectedFilter.onAttach();
//    }





    @Override
    public void run() {
        initGL(surfaceTexture);

        // Setup camera filters map
        cameraFilterMap.append(R.id.filter0, new OriginalFilter(context));
        cameraFilterMap.append(R.id.filter1, new BlackandWhite(context));
        cameraFilterMap.append(R.id.filter3, new FishVision(context));
        cameraFilterMap.append(R.id.filter4, new HSVtoRGB(context));
        cameraFilterMap.append(R.id.filter5, new InsectVision(context));
        cameraFilterMap.append(R.id.filter6, new InvertVision(context));
        cameraFilterMap.append(R.id.filter7, new Test(context));
        cameraFilterMap.append(R.id.filter17, new VR_DogVision(context));

        cameraFilterMap.append(R.id.filter9, new Cb_Deuteranomaly(context));
        cameraFilterMap.append(R.id.filter10, new Cb_Deuteranopia(context));
        cameraFilterMap.append(R.id.filter11, new Cb_Protanomaly(context));
        cameraFilterMap.append(R.id.filter12, new Cb_Protanopia(context));
        cameraFilterMap.append(R.id.filter13, new Cb_Tritanomaly(context));
        cameraFilterMap.append(R.id.filter14, new Cb_Tritanopia(context));
        cameraFilterMap.append(R.id.filter15, new Cb_Achromatomaly(context));
        cameraFilterMap.append(R.id.filter16, new Cb_Achromatospia(context));

        cameraFilterMap.append(R.id.filter2, new DogVision(context));
        cameraFilterMap.append(R.id.filter8, new Thermal(context));
        cameraFilterMap.append(R.id.filter18, new Snail(context));
        cameraFilterMap.append(R.id.filter19, new Shark(context));
        cameraFilterMap.append(R.id.filter20, new Cat(context));
        cameraFilterMap.append(R.id.filter21, new Bird(context));
        cameraFilterMap.append(R.id.filter22, new Bee(context));
        cameraFilterMap.append(R.id.filter23, new Clam(context));
        cameraFilterMap.append(R.id.filter24, new Gecko(context));
        cameraFilterMap.append(R.id.filter25, new Cuttlefish(context));
        cameraFilterMap.append(R.id.filter26, new JumpingSpider(context));
        cameraFilterMap.append(R.id.filter27, new Bear(context));
        cameraFilterMap.append(R.id.filter28, new Ape(context));
        cameraFilterMap.append(R.id.filter29, new Horse(context));
        cameraFilterMap.append(R.id.filter30, new Octopus(context));
        cameraFilterMap.append(R.id.filter31, new Dolphin(context));

        setSelectedFilter(selectedFilterId);

        // Create texture for camera preview
        cameraTextureId = MyGLUtils.genTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES);
        cameraSurfaceTexture = new SurfaceTexture(cameraTextureId);

        // Start camera preview
        try {


            camera.setPreviewTexture(cameraSurfaceTexture);

            Camera.Parameters params = camera.getParameters();
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            camera.setParameters(params);


            camera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
        }

        // Render loop
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (gwidth < 0 && gheight < 0)
                    GLES20.glViewport(0, 0, gwidth = -gwidth, gheight = -gheight);

                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

                // Update the camera preview texture
                synchronized (this) {
                    cameraSurfaceTexture.updateTexImage();
                }

                // Draw camera preview
                selectedFilter.draw(cameraTextureId, gwidth, gheight);

                // Flush
                GLES20.glFlush();
                egl10.eglSwapBuffers(eglDisplay, eglSurface);

                Thread.sleep(DRAW_INTERVAL);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        cameraSurfaceTexture.release();
        GLES20.glDeleteTextures(1, new int[]{cameraTextureId}, 0);
    }

    private void initGL(SurfaceTexture texture) {
        egl10 = (EGL10) EGLContext.getEGL();

        eglDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (eglDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglGetDisplay failed " +
                    android.opengl.GLUtils.getEGLErrorString(egl10.eglGetError()));
        }

        int[] version = new int[2];
        if (!egl10.eglInitialize(eglDisplay, version)) {
            throw new RuntimeException("eglInitialize failed " +
                    android.opengl.GLUtils.getEGLErrorString(egl10.eglGetError()));
        }

        int[] configsCount = new int[1];
        EGLConfig[] configs = new EGLConfig[1];
        int[] configSpec = {
                EGL10.EGL_RENDERABLE_TYPE,
                EGL_OPENGL_ES2_BIT,
                EGL10.EGL_RED_SIZE, 8,
                EGL10.EGL_GREEN_SIZE, 8,
                EGL10.EGL_BLUE_SIZE, 8,
                EGL10.EGL_ALPHA_SIZE, 8,
                EGL10.EGL_DEPTH_SIZE, 0,
                EGL10.EGL_STENCIL_SIZE, 0,
                EGL10.EGL_NONE
        };

        EGLConfig eglConfig = null;
        if (!egl10.eglChooseConfig(eglDisplay, configSpec, configs, 1, configsCount)) {
            throw new IllegalArgumentException("eglChooseConfig failed " +
                    android.opengl.GLUtils.getEGLErrorString(egl10.eglGetError()));
        } else if (configsCount[0] > 0) {
            eglConfig = configs[0];
        }
        if (eglConfig == null) {
            throw new RuntimeException("eglConfig not initialized");
        }

        int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE};
        eglContext = egl10.eglCreateContext(eglDisplay, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
        eglSurface = egl10.eglCreateWindowSurface(eglDisplay, eglConfig, texture, null);

        if (eglSurface == null || eglSurface == EGL10.EGL_NO_SURFACE) {
            int error = egl10.eglGetError();
            if (error == EGL10.EGL_BAD_NATIVE_WINDOW) {
                Log.e(TAG, "eglCreateWindowSurface returned EGL10.EGL_BAD_NATIVE_WINDOW");
                return;
            }
            throw new RuntimeException("eglCreateWindowSurface failed " +
                    android.opengl.GLUtils.getEGLErrorString(error));
        }

        if (!egl10.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)) {
            throw new RuntimeException("eglMakeCurrent failed " +
                    android.opengl.GLUtils.getEGLErrorString(egl10.eglGetError()));
        }
    }

    private Pair<Camera.CameraInfo, Integer> getBackCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        final int numberOfCameras = Camera.getNumberOfCameras();

        for (int i = 0; i < numberOfCameras; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return new Pair<>(cameraInfo, i);
            }
        }
        return null;
    }

    private Pair<Camera.CameraInfo, Integer> getFrontCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        final int numberOfCameras = Camera.getNumberOfCameras();

        for (int i = 0; i < numberOfCameras; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return new Pair<>(cameraInfo, i);
            }
        }
        return null;
    }
}