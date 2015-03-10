package haberapp.ahmetcemkaya.com.haberapp;

import java.util.ArrayList;

/**
 * Created by ahmetcem on 21.2.2015.
 */
public class Ipsum {

    public static ArrayList<ListModel> testo = new ArrayList<ListModel>();
    public static ArrayList<ListModel> categoriesArrayList = new ArrayList<ListModel>();
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


    static String currentView = null;

    public static ListModel[] listmodeltest = {
            new ListModel("Hürriyet",R.drawable.hurriyet),
            new ListModel("Milliyet",R.drawable.milliyet),
            new ListModel("Zaman",R.drawable.zaman),
            new ListModel("Radikal",R.drawable.radikal),
            new ListModel("Sporx",R.drawable.sporx),
            new ListModel("NTV",R.drawable.ntv),
            new ListModel("Diken",R.drawable.diken),
            new ListModel("OdaTV",R.drawable.odatv),
            new ListModel("IFL Science",R.drawable.iflscience),
            new ListModel("Sözcü",R.drawable.sozcu),
            new ListModel("AMK Spor",R.drawable.amk),
            new ListModel("EuroSport",R.drawable.eurosport),
            new ListModel("BBC Türk",R.drawable.bbc),
            new ListModel("Aljazeera",R.drawable.aljazeera),
            new ListModel("Gamespot",R.drawable.gamespot),
            new ListModel("IGN",R.drawable.ign),
            new ListModel("Kotaku",R.drawable.kotaku),
            new ListModel("HaberApp Özel",R.drawable.icon)
    };

    public static ListModel[] categoriesarray = {
            new ListModel("Dünya",R.drawable.dunya),
            new ListModel("Ekonomi",R.drawable.ekonomi),
            new ListModel("Siyaset",R.drawable.siyaset),
            new ListModel("Spor",R.drawable.spor),
            new ListModel("Magazin",R.drawable.magazin),
            new ListModel("Gündem",R.drawable.gundem),
            new ListModel("Eğitim",R.drawable.egitim),
            new ListModel("Teknoloji",R.drawable.teknoloji),
            new ListModel("Bilim",R.drawable.bilim),
            new ListModel("Sağlık",R.drawable.saglik),
            new ListModel("Sanat",R.drawable.sanat),
            new ListModel("Oyun",R.drawable.oyun)
    };





}