package com.trinta.bruno.udraw.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by Bruno on 14/10/2016.
 * Cette classe regroupe les méthodes permettant de rafraichir la gallerie du téléphone afin de faire appraitres
 * les vidéos/images enregistrées dans l'application.
 */

public class GalleryRefreshHelper {

    public static void refreshGalleryWithUrl(String url, Context ctx)
    {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(url);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }

    public static void refreshGalleryFromFile(File file, Context ctx)
    {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }
}
