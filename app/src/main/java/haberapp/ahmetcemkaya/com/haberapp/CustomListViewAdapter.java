package haberapp.ahmetcemkaya.com.haberapp;

/**
 * Created by cem.kaya on 09.03.2015.
 */
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomListViewAdapter extends ArrayAdapter<ListModel> {
    String [] result;
    ListModel[] listmodelarray;
    ArrayList<ListModel> listmodelarraylist;
    ArrayList<String> positionList;
    int [] imageId;
    Context mContext;
    int resourceId;
    TinyDB tinydb ;
    private static LayoutInflater inflater=null;
    public CustomListViewAdapter(Context context,int resource, ArrayList<ListModel> listmodel) {
        super(context,resource,listmodel);
        listmodelarraylist = listmodel;
        mContext = context;
        this.resourceId = resource;
        positionList = new ArrayList<String>();
        tinydb  = new TinyDB(context);

        //Checking which fragment we are on.
        if(listmodel.get(0).mName.equals("Hürriyet"))
        positionList = tinydb.getList("sources");
        else
        positionList = tinydb.getList("categories");
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listmodelarraylist.size();
    }



    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    static class Holder
    {
        TextView text;
        ImageView image;
        protected CheckBox checkbox;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

         Holder holder = null;
        View row = convertView;


            ImageView imageView = null;
            TextView textView = null;
            CheckBox chkbox = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                row = inflater.inflate(resourceId, parent, false);

                holder = new Holder();
                holder.text = (TextView) row.findViewById(R.id.textView1);
                holder.image = (ImageView) row.findViewById(R.id.imageView1);
                holder.checkbox = (CheckBox) row.findViewById(R.id.check);
                final Holder finalHolder = holder;
                holder.checkbox
                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                ListModel element = (ListModel) finalHolder.checkbox
                                        .getTag();
                                element.setSelected(buttonView.isChecked());



                            }
                        });



                row.setTag(holder);
                holder.checkbox.setTag(listmodelarraylist.get(position));
            } else {
                holder = (Holder) row.getTag();
                ((Holder) row.getTag()).checkbox.setTag(listmodelarraylist.get(position));
            }

            ListModel data = listmodelarraylist.get(position);
            holder.text.setText(data.mName);
            holder.image.setImageResource(data.mImageID);
            holder.checkbox.setChecked(data.isSelected());


        final Holder finalHolder1 = holder;
        row.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ListModel data = listmodelarraylist.get(position);
                boolean b = data.isSelected();
                finalHolder1.checkbox.setChecked(!b);
                listmodelarraylist.get(position).setSelected(!b);

                if(b == false)
                {

                    positionList.add(Integer.toString(position));
                    Log.e("selected :",Integer.toString(position));
                }
                else
                {
                    positionList.remove(Integer.toString(position));
                }

                //Checking which fragment we are on.
                if(listmodelarraylist.get(0).mName.equals("Hürriyet"))
                    tinydb.putList("sources",positionList);
                else
                    tinydb.putList("categories",positionList);



            }
        });


        return row;


    }

}