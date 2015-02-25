package haberapp.ahmetcemkaya.com.haberapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by ahmetcem on 21.2.2015.
 */
public class NewsSourceFragment extends ListFragment {
    OnNewsSourceSelectedListener callback;
    TinyDB tinydb;

    public interface OnNewsSourceSelectedListener{

        public void onSourceSelected(int position);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                1);
        tinydb= new TinyDB(activity);
       try
       {
           callback = (OnNewsSourceSelectedListener) activity;

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
        String[] data = Ipsum.Sources;
        setListAdapter(new ArrayAdapter<String>(getActivity(),layout,data));
    }

    @Override
    public void onStart() {
        super.onStart();
        ListView v = getListView();
        if(v!=null){
            v.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }
        tinydb.putString("currentView","sources");
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(l.isItemChecked(position) == true)
            l.setItemChecked(position,true);
        else
            l.setItemChecked(position,false);

        callback.onSourceSelected(position);
    }
}
