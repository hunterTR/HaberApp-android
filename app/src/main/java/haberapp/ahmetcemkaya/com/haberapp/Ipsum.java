package haberapp.ahmetcemkaya.com.haberapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by ahmetcem on 21.2.2015.
 */
public class Ipsum {

    static String[] Sources = {
            "Hürriyet",
            "Milliyet",
            "Zaman",
            "Radikal",
            "Sporx",
            "NTV",
            "Diken",
            "odaTV",
            "(IFL)I Fucking Love Science",
            "Sözcü",
            "AMK Spor",
            "HaberApp Özel"

    };
    static String[] Categories = {
            "Dünya",
            "Ekonomi",
            "Siyaset",
            "Spor",
            "Magazin",
            "Gündem",
            "Eğitim",
            "Teknoloji",
            "Bilim",
            "Sağlık",
            "Sanat"
    };

    static String currentView = null;


}