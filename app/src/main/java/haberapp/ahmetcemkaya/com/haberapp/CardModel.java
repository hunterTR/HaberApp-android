package haberapp.ahmetcemkaya.com.haberapp;


import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ahmetcem on 22.2.2015.
 * Custom model for Cards. More features can be added later.
 */
public class CardModel {
    public String title;
    public int image;
    public Bitmap bitmap;
    public String imageurl;
    public ImageView imgv;

    public CardModel(String modelTitle,String imageUrl)
    {

        imageurl = imageUrl;
        title = modelTitle;
    }


}
