package haberapp.ahmetcemkaya.com.haberapp;

/**
 * Created by cem.kaya on 09.03.2015.
 */
public class ListModel {
    public String mName;
    public int mImageID;
    public String newsID;
    public boolean selected;

    public ListModel(String name,int imageID)
    {
        mName = name;
        mImageID = imageID;
        selected = false;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
}
}
