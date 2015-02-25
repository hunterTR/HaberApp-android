package haberapp.ahmetcemkaya.com.haberapp;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by ahmetcem on 21.2.2015.
 */
public class CategoriesFragment extends ListFragment {
    OnCategorySelectedListener callback;
    ArrayList<String> positionList;
    TinyDB tinydb;

    public interface OnCategorySelectedListener{
        public void onCategorySelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                2);

         tinydb= new TinyDB(activity);
        try
        {
            callback = (OnCategorySelectedListener) activity;

        }catch(ClassCastException e)  // Being sure that activity has implemented the interface if not throw a exception
        {
            throw new ClassCastException(activity.toString());
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tinydb.putString("currentView","categories");  // set the current view


    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int layout = android.R.layout.simple_list_item_multiple_choice;
        String[] data = Ipsum.Categories;

        ListView v = getListView();
        if(v!=null){
            v.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }


        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, data));
        v.setItemChecked(Integer.parseInt("1"),true);
        positionList = tinydb.getList("positions");

        for(int i = 0 ; i < positionList.size() ; i++ )
        {
            Log.e("position",positionList.get(i));
            v.setItemChecked(Integer.parseInt(positionList.get(i)),true);
        }



    }

    @Override
    public void onStart() {
        super.onStart();
        positionList = new ArrayList();




    }

    @Override
    public void onPause() {
        super.onPause();
        tinydb.putList("positions", positionList);

    }

    @Override
    public void onDetach() {
        super.onDetach();


        tinydb.putList("positions", positionList);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(l.isItemChecked(position) == true)
        {
            l.setItemChecked(position,true);
            positionList.add(Integer.toString(position));

        }
        else
        {
            l.setItemChecked(position, false);
            positionList.remove(Integer.toString(position));

        }
        tinydb.putList("positions", positionList);

        callback.onCategorySelected(position);
    }



}
