package haberapp.ahmetcemkaya.com.haberapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,NewsSourceFragment.OnNewsSourceSelectedListener,CategoriesFragment.OnCategorySelectedListener,HttpRequestTest.JsonListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ArrayList<CardModel> al;
    private CardAdapter cardAdapter;
    HttpRequestTest request;
    SwipeFlingAdapterView flingContainer;
    NewsSourceFragment newsSourceFragment;
    CategoriesFragment categoriesFragment;
    TinyDB tinydb;
    private int i;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request = new HttpRequestTest(this);
        request.jsonListener = this;
        tinydb = new TinyDB(this);

        newsSourceFragment = new NewsSourceFragment();
        categoriesFragment = new CategoriesFragment();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if(findViewById(R.id.container) != null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
            request.test();
          /*  NewsSourceFragment sourcesFragment = new NewsSourceFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.container,sourcesFragment).commit();
            */

           flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

            al = new ArrayList<CardModel>();







          //  arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );

            cardAdapter = new CardAdapter(this,R.layout.item,al);
            flingContainer.setAdapter(cardAdapter);

            flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
                @Override
                public void removeFirstObjectInAdapter() {
                    // this is the simplest way to delete an object from the Adapter (/AdapterView)
                    Log.d("LIST", "removed object!");
                    al.remove(0);
                    cardAdapter.notifyDataSetChanged();

                }

                @Override
                public void onLeftCardExit(Object dataObject) {
                    //Do something on the left!
                    //You also have access to the original object.
                    //If you want to use it just cast it (String) dataObject
                   // makeToast(this, "Left!");
                    Log.e("movement" , "Left!");
                }

                @Override
                public void onRightCardExit(Object dataObject) {
                   // makeToast(this, "Right!");
                    CardModel test =(CardModel)dataObject;

                    Log.e("movement" , "Right! " +test.imageurl);
                }

                @Override
                public void onAdapterAboutToEmpty(int itemsInAdapter) {
                    // Ask for more data here
                    //al.add( new CardModel("Formula 1 korkutan kaza",R.drawable.formula1));
                    cardAdapter.notifyDataSetChanged();
                    //Log.d("LIST", "notified");
                    Log.e("movement" , "About to Empty!");
                    //i++;
                }

                @Override
                public void onScroll(float scrollProgressPercent) {

                   // view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                   // view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);

                }

            });


            // Optionally add an OnItemClickListener
            flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
                @Override
                public void onItemClicked(int itemPosition, Object dataObject) {
                //    makeToast(this , "Clicked!");
                    CardModel data = (CardModel)dataObject;
                    Uri uri = Uri.parse(data.newsURL);
                   Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    //Intent intent;
                    //intent = new Intent(this, WebViewActivity.class);

                    startActivity(intent);

                    Log.e("movement" , "Item Tapped!" + itemPosition);
                }
            });


            flingContainer.setVisibility(View.GONE);
}
        // change fragment;
        if(tinydb.getList("sources").size() <=0)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, newsSourceFragment)
                    .commit();

        }
        else
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container,PlaceholderFragment.newInstance(1))
                    .commit();

            flingContainer.setVisibility(View.VISIBLE);
        }

    }




    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment swapFragment = null;

        switch(position)
        {
            case 0:
                swapFragment = newsSourceFragment;
                if(flingContainer !=null)
                {
                    flingContainer.setVisibility(View.GONE);
                }
                break;
            case 1:
                swapFragment = categoriesFragment;
               // flingContainer.setVisibility(View.GONE);
                if(flingContainer !=null)
                {
                    flingContainer.setVisibility(View.GONE);
                }
                break;
            case 2:
            {
                swapFragment=PlaceholderFragment.newInstance(position + 1);
                tinydb.putString("currentView","news");
                flingContainer.setVisibility(View.VISIBLE);
            }
                break;
        }
        if(swapFragment !=null)
        {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, swapFragment)
                    .commit();
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        else if(id == R.id.action_forward)
        {
            String tmp = tinydb.getString("currentView");
            switch(tmp)
            {
                case "sources":     // if the current view is sources.
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top,R.anim.abc_fade_out)
                            .replace(R.id.container,categoriesFragment)
                            .commit();
                     getSupportActionBar().setTitle("Kategoriler");
                    break;
                case "categories":
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top,R.anim.abc_fade_out)
                            .replace(R.id.container,PlaceholderFragment.newInstance(1))
                            .commit();
                    flingContainer.setVisibility(View.VISIBLE);
                    getSupportActionBar().setTitle("Haberler");

                    break;
                case "news":
                //Change the card.
                    break;
            }

            Log.e("Next","Next Button On Click");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSourceSelected(int position) {
       // Toast info = Toast.makeText(this,Ipsum.Sources[position]+" selected",Toast.LENGTH_SHORT);
       // info.show();
    }

    @Override
    public void onCategorySelected(int position) {
       // Toast info = Toast.makeText(this,Ipsum.Categories[position]+" selected",Toast.LENGTH_SHORT);

       // info.show();
    }

    @Override
    public void onGetJsonListener(String result) {
        Log.e("JSON",result);

        JSONObject json = null; // convert String to JSONObject
        JSONArray articles;
        try {
            json = new JSONObject(result);

            articles = json.getJSONArray("result"); // get articles array

            articles.length(); // --> 2
            Log.e("LENGTH AMK",Integer.toString(articles.length()));
          for(int i = 0 ; i < articles.length() ; i++)
          {
              ImageView tmp = new ImageView(this);

                al.add(new CardModel(articles.getJSONObject(i).getString("title"),articles.getJSONObject(i).getString("image"),articles.getJSONObject(i).getString("url")));
          }

            cardAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);



            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

//WEBVIEW CLIENT



}
