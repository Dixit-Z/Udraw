package com.trinta.bruno.udraw.videoServices;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.trinta.bruno.udraw.helpers.GalleryRefreshHelper;
import com.trinta.bruno.udraw.helpers.InfoOnDeviceHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by Bruno on 11/10/2016.
 * Classe permettant de réaliser le split d'une vidéo. C-à-d la découper en images.
 */

public class VideoSpliter {

    private static MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
    private static String pathToVideo = new String();
    final static int quality = 100;
    final static float factor = 0.80f;
    private static final String TAG = "VideoSpliter";

    public static void splitVideo(Context ctx) {
        pathToVideo = InfoOnDeviceHelper.getVideoFilePath();
        mediaMetadataRetriever.setDataSource(pathToVideo + "/Movies/VIDEO_TESTUDRAW.mp4");
        //Duration in milliseconds
        String METADATA_KEY_DURATION = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

        Bitmap bmpOriginal = mediaMetadataRetriever.getFrameAtTime(0);
        int bmpVideoHeight = bmpOriginal.getHeight();
        int bmpVideoWidth = bmpOriginal.getWidth();

        Log.d("LOGTAG", "bmpVideoWidth:'" + bmpVideoWidth + "'  bmpVideoHeight:'" + bmpVideoHeight + "'");

        byte[] lastSavedByteArray = new byte[0];

        int scaleWidth = (int) ((float) bmpVideoWidth * factor);
        int scaleHeight = (int) ((float) bmpVideoHeight * factor);
        int max = (int) Long.parseLong(METADATA_KEY_DURATION);
        for (int index = 0; index < max; index+=250) {
            bmpOriginal = mediaMetadataRetriever.getFrameAtTime(index * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
            bmpVideoHeight = bmpOriginal == null ? -1 : bmpOriginal.getHeight();
            bmpVideoWidth = bmpOriginal == null ? -1 : bmpOriginal.getWidth();
            int byteCount = bmpOriginal.getWidth() * bmpOriginal.getHeight() * 4;
            ByteBuffer tmpByteBuffer = ByteBuffer.allocate(byteCount);
            bmpOriginal.copyPixelsToBuffer(tmpByteBuffer);
            byte[] tmpByteArray = tmpByteBuffer.array();
            System.out.println("INDEX IS:" + index);
//            if (!Arrays.equals(tmpByteArray, lastSavedByteArray)) {

                String namePhoto = "IMAGE_" + (index + 1)
                        + "_" + max + "_quality_" + quality + "_w" + scaleWidth + "_h" + scaleHeight + ".png";
                File parentFolder = new File(pathToVideo + "/Screenshots/");
                parentFolder.mkdirs();
                File outputFile = new File(parentFolder, namePhoto);
                System.out.println(outputFile.getAbsoluteFile());
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(outputFile);
                } catch (FileNotFoundException e) {
                    Log.e(TAG, "splitVideo: FileNotFound output_file", e);
                    e.printStackTrace();
                }

                Bitmap bmpScaledSize = Bitmap.createScaledBitmap(bmpOriginal, scaleWidth, scaleHeight, false);

                bmpScaledSize.compress(Bitmap.CompressFormat.PNG, quality, outputStream);

                try {
                    outputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "splitVideo: Could not close outputStream error", e);
                    e.printStackTrace();
                }

                lastSavedByteArray = tmpByteArray;
                GalleryRefreshHelper.refreshGalleryFromFile(outputFile, ctx);
//            }
        }
        mediaMetadataRetriever.release();
    }
}

