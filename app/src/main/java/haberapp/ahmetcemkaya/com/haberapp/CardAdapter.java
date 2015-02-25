package haberapp.ahmetcemkaya.com.haberapp;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ahmetcem on 22.2.2015.
 * This is a custom adapter for cards.
 */
public class CardAdapter extends ArrayAdapter<CardModel> {

    Context context;
    int layoutResourceId;
    ArrayList<CardModel> data = null;
    public CardAdapter(Context context, int layoutResourceId, ArrayList<CardModel> al) {
        super(context, layoutResourceId,al);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
     //   data= new ArrayList<CardModel>();
        this.data = al;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CardHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.testImageView);
            holder.txtTitle = (TextView)row.findViewById(R.id.helloText);

            row.setTag(holder);
        }
        else
        {
            holder = (CardHolder)row.getTag();
        }

        CardModel cardmodel = data.get(position);
        holder.txtTitle.setText(cardmodel.title);
        holder.imgIcon.setImageResource(cardmodel.image);

        return row;
    }

    static class CardHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}


