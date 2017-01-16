package com.trinta.bruno.udraw.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.trinta.bruno.udraw.R;
import com.trinta.bruno.udraw.activities.connectedHome.ConnectedHome;
import com.trinta.bruno.udraw.helpers.GalleryRefreshHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bruno on 30/09/2016.
 * Activité permettant de record une vidéo.
 */

public class VideoRecorder extends AppCompatActivity implements View.OnClickListener {

    private TextureView mTextureView;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mPreviewBuilder;
    private CameraCaptureSession mPreviewSession;
    private Size mPreviewSize;

    private Handler backgroundHandler;
    private HandlerThread thread;

    private MediaRecorder mMediaRecorder;
    private String mVideoPath;
    private String mVideoName;

    private boolean mIsRecordingVideo;
    Surface recorderSurface;

    private File video;
    private Button recordButton;
    private static final String TAG = "VideoRecording";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_preview);

        mMediaRecorder = new MediaRecorder();

        mMediaRecorder.setMaxDuration(7000);

        mTextureView = (TextureView) findViewById(R.id.previewTexture);
        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);

        mVideoName = System.currentTimeMillis() + ".mp4";
        video = new File(getVideoFilePath(), mVideoName);

        recordButton = (Button) findViewById(R.id.button_capture);
        recordButton.setOnClickListener(this);

    }

    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Log.v(TAG, "onSurfaceTextureAvailable, width=" + width + ",height=" + height);
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            //is true ?
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        closeCamera();
        stopBackgroundThread();
    }

    private void openCamera() {
        CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        Log.d(TAG, "Opening Camera");
        try {
            String camId = camManager.getCameraIdList()[0];
            CameraCharacteristics cameraChars = camManager.getCameraCharacteristics(camId);
            StreamConfigurationMap map = cameraChars.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(VideoRecorder.this, R.string.AuthorizationRequired, Toast.LENGTH_LONG).show();
                return;
            }
            camManager.openCamera(camId, cameraStateCallback, null);
        } catch (CameraAccessException e) {
            Log.e(TAG, "ERROR WHEN OPENING CAMERA");
            e.printStackTrace();
        }
    }

    private void startPreview() {
        if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
            Log.e(TAG, "Error Starting Preview. ABORTED!");
            return;
        }

        SurfaceTexture texture = mTextureView.getSurfaceTexture();
        if (null == texture) {
            Log.e(TAG, "Cannot create texture. ABORTED!");
            return;
        }

        texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        Surface surface = new Surface(texture);

        try {
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (CameraAccessException e) {
            Log.e(TAG, "startPreview: Could not get camera access", e);
            e.printStackTrace();
        }

        mPreviewBuilder.addTarget(surface);

        try {
            mCameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mPreviewSession = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Log.e(TAG, "onConfigureFailed");
                }
            }, null);
        } catch (CameraAccessException e) {
            Log.e(TAG, "ERROR WHEN RENDERING CAMERA PREVIEW");
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if (null == mCameraDevice) {
            Log.e(TAG, "Camera Device is Null! ABORT!");
            return;
        }

        mPreviewBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, new Range<>(60, 60));
        thread = new HandlerThread("CameraPreview");
        thread.start();
        backgroundHandler = new Handler(thread.getLooper());

        try {
            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {
            Log.e(TAG, "ERROR WHEN UPDATING CAMERA PREVIEW");
            e.printStackTrace();
        }
    }

    /**
     * Stops the background thread and its {@link Handler}.
     */
    private void stopBackgroundThread() {
        thread.quitSafely();
        try {
            thread.join();
            thread = null;
            backgroundHandler = null;
        } catch (InterruptedException e) {
            Log.e(TAG, "stopBackgroundThread: Error when stopping backround threads", e);
            e.printStackTrace();
        }
    }

    private void closePreviewSession() {
        if (null != mPreviewSession) {
            mPreviewSession.close();
        }
    }

    private void closeCamera() {
        closePreviewSession();

        if (null != mCameraDevice) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }

    private CameraDevice.StateCallback cameraStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.d(TAG, "onOpened");
            mCameraDevice = camera;
            startPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            Log.d(TAG, "onDisconnected");
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.e(TAG, "onError code: " + error);
        }
    };

    private void stopRecordingVideo() throws CameraAccessException {
        mIsRecordingVideo = false;
        closeCamera();

        mMediaRecorder.stop();
        /*mMediaRecorder.reset();*/
        mMediaRecorder.release();
        Log.d(TAG, "Video saved: " + mVideoPath);
        GalleryRefreshHelper.refreshGalleryWithUrl(mVideoPath+"/"+mVideoName, this.getApplicationContext());
        Intent goHome = new Intent(VideoRecorder.this, ConnectedHome.class);
        startActivity(goHome);
    }

    private void startRecordingVideo() {
        if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
            return;
        }
        try {
            mIsRecordingVideo = true;
            recordButton.setBackgroundColor(Color.RED);
            closePreviewSession();
            setupMediaRecorder();
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);

            // Set up de la surface pour la preview de l'enregistrement.
            List<Surface> surfaces = new ArrayList<>();
            Surface previewSurface = new Surface(texture);
            surfaces.add(previewSurface);
            mPreviewBuilder.addTarget(previewSurface);

            recorderSurface = mMediaRecorder.getSurface();
            surfaces.add(recorderSurface);
            mPreviewBuilder.addTarget(recorderSurface);

            //Debut de session et d'enregistrement de la vidéo.
            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mPreviewSession = session;
                    updatePreview();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMediaRecorder.start();
                        }
                    });
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Log.e(TAG, "Capture failed!");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mIsRecordingVideo = false;
                        }
                    });
                }
            }, backgroundHandler);

        } catch (CameraAccessException e) {
            Log.e(TAG, "startRecordingVideo: No camera access. You might not have give camera authorizarion", e);
            e.printStackTrace();

        } catch (IOException e) {
            Log.e(TAG, "startRecordingVideo: Couldn't read from camera. You might not have any camera on your device or UDraw cannot access it.", e);
            e.printStackTrace();
        }
    }

    private void setupMediaRecorder() throws IOException {

        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight);
        mMediaRecorder.setVideoEncodingBitRate(1800000);
        mMediaRecorder.setVideoFrameRate(60);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setCaptureRate(60);

        if (mVideoPath == null || mVideoPath.isEmpty()) {
            mVideoPath = getVideoFilePath();
        }
        mMediaRecorder.setOutputFile(video.getAbsolutePath());
        mMediaRecorder.prepare();
    }

    @Deprecated
    /*
    Should now use the helper InfoOnDeviceHelper
     */
    private String getVideoFilePath() {
        StringBuilder pathSave = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath());
        pathSave.append("/Movies");
        String resultPath = pathSave.toString();
        return resultPath;
    }

    @Override
    public void onClick(View v) {
        try {
            if (mIsRecordingVideo) {
                stopRecordingVideo();

            } else {
                startRecordingVideo();
            }
        } catch (Exception ex) {
            Log.e(TAG, "NO CAMERA FOUND");
            ex.printStackTrace();
        }
    }
}
