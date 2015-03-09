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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmetcem on 21.2.2015.
 */
public class Ipsum {

    public static ArrayList<ListModel> testo = new ArrayList<ListModel>();

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
            "EuroSport",
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

    public static int [] sourceImages={R.drawable.hurriyet,
            R.drawable.hurriyet,R.drawable.hurriyet,
            R.drawable.hurriyet,
            R.drawable.hurriyet,R.drawable.hurriyet,
            R.drawable.hurriyet,R.drawable.hurriyet,
            R.drawable.hurriyet,R.drawable.hurriyet,
            R.drawable.hurriyet,R.drawable.hurriyet,
            R.drawable.hurriyet};

    static String currentView = null;

    public static ListModel[] listmodeltest = {new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
            new ListModel("deneme",R.drawable.hurriyet),
    };





}