package haberapp.ahmetcemkaya.com.haberapp;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by ahmetcem on 21.2.2015.
 */
public class CategoriesFragment extends ListFragment {
    OnCategorySelectedListener callback;

    ArrayList positionList;
    public interface OnCategorySelectedListener{
        public void onCategorySelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            callback = (OnCategorySelectedListener) activity;

        }catch(ClassCastException e)  // Being sure that activity has implemented the interface if not throw a exception
        {
            throw new ClassCastException(activity.toString());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int layout = android.R.layout.simple_list_item_multiple_choice;
        String[] data = Ipsum.Categories;
        setListAdapter(new ArrayAdapter<String>(getActivity(),layout,data));


    }

    @Override
    public void onStart() {
        super.onStart();
        positionList = new ArrayList();
        ListView v = getListView();
        if(v!=null){
            v.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(l.isItemChecked(position) == true)
        {
            l.setItemChecked(position,true);
            positionList.add(position);
            Log.e("positionBro: ",positionList.get(0).toString());
        }
        else
        {
            l.setItemChecked(position, false);
            int index = positionList.indexOf(position);
            positionList.remove(index);
        }


        callback.onCategorySelected(position);
    }



}
