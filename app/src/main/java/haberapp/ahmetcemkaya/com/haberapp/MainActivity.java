package haberapp.ahmetcemkaya.com.haberapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.android.push.BasePushMessageReceiver;
import com.arellomobile.android.push.PushManager;
import com.arellomobile.android.push.utils.RegisterBroadcastReceiver;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
    private ArrayList<String> reads;
    private CardAdapter cardAdapter;
    private JSONObject newsToSave;
    private JSONArray newsArrayToSave;
    HttpRequestTest request;
    SwipeFlingAdapterView flingContainer;
    NewsSourceFragment newsSourceFragment;
    CategoriesFragment categoriesFragment;
    TinyDB tinydb;
    private Menu optionsMenu;

    private int i;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        request = new HttpRequestTest(this);
        request.jsonListener = this;
        tinydb = new TinyDB(this);
        reads = tinydb.getList("reads");
        newsArrayToSave = new JSONArray();

        String tmpJson = tinydb.getString("savedNews");
        if (tmpJson != null) {
            try {
                newsToSave = new JSONObject(tmpJson);
                newsArrayToSave = newsToSave.getJSONArray("result");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        Log.e("saved news json",tinydb.getString("savedNews"));
        ArrayList<String> positionList = tinydb.getList("sources");

        for(int i = 0 ; i < Ipsum.listmodeltest.length ; i++ )
        {
            Ipsum.testo.add(Ipsum.listmodeltest[i]);
        }

        for(int i = 0 ; i < positionList.size() ; i++ )
        {
          Ipsum.testo.get(Integer.parseInt(positionList.get(i))).setSelected(true);
        }

        ArrayList<String> categoriesPositionList = tinydb.getList("categories");
        for(int i = 0 ; i < Ipsum.categoriesarray.length ; i++ )
        {
            Ipsum.categoriesArrayList.add(Ipsum.categoriesarray[i]);
        }

        for(int i = 0 ; i < categoriesPositionList.size() ; i++ )
        {
            Ipsum.categoriesArrayList.get(Integer.parseInt(categoriesPositionList.get(i))).setSelected(true);
        }


        //PUSHWOOSH

        //Register receivers for push notifications
        registerReceivers();

        //Create and start push manager
        PushManager pushManager = PushManager.getInstance(this);

        //Start push manager, this will count app open for Pushwoosh stats as well
        try {
            pushManager.onStartup(this);
        }
        catch(Exception e)
        {
            //push notifications are not available or AndroidManifest.xml is not configured properly
        }

        //Register for push!
        pushManager.registerForPushNotifications();

        checkMessage(getIntent());


        //PUSHWOOSH


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
                //    Log.d("LIST", "removed object!");
                    al.remove(0);
                    cardAdapter.notifyDataSetChanged();
                }

                @Override
                public void onLeftCardExit(Object dataObject) {
                    //Do something on the left!
                    //You also have access to the original object.
                    //If you want to use it just cast it (String) dataObject
                   // makeToast(this, "Left!");
                    CardModel test =(CardModel)dataObject;
                    reads.add(test.newsID);
                    JSONObject tmpJson = new JSONObject();
                    if(!tinydb.getString("currentView").equals("saved"))   // if we are not on saved news view then we can add the card to array
                    {
                        try {
                            tmpJson.put("title", test.title);
                            tmpJson.put("newsID", test.newsID);
                            tmpJson.put("url", test.newsURL);
                            tmpJson.put("image", test.imageurl);
                            tmpJson.put("content",test.mContent);
                            newsArrayToSave.put(tmpJson);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        putSavedNews();
                    }
                    // Log.e("movement" , "Left!" + test.newsID);
                }

                @Override
                public void onRightCardExit(Object dataObject) {
                   // makeToast(this, "Right!");
                    CardModel test =(CardModel)dataObject;
                    reads.add(test.newsID);
                    if(tinydb.getString("currentView").equals("saved"))   // if we are on saved news view then we can remove the card from array
                    {
                      newsArrayToSave = RemoveJSONArray(newsArrayToSave,test.newsID);

                   //     Log.e("movement" , "Right! " +newsArrayToSave.length());
                        putSavedNews();
                    }
                   // Log.e("movement" , "Right! " +tinydb.getString("currentView"));

                 //   Log.e("movement" , "Right! " +test.newsID);
                }

                @Override
                public void onAdapterAboutToEmpty(int itemsInAdapter) {
                    // Ask for more data here
                    //al.add( new CardModel("Formula 1 korkutan kaza",R.drawable.formula1));
                    cardAdapter.notifyDataSetChanged();
                    //Log.d("LIST", "notified");
                  //  Log.e("movement" , "About to Empty!");
                    //i++;
                }

                @Override
                public void onScroll(float scrollProgressPercent) {

                    View view = flingContainer.getSelectedView();
                    view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                }

            });


            // Optionally add an OnItemClickListener
            flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
                @Override
                public void onItemClicked(int itemPosition, Object dataObject) {
                //    makeToast(this , "Clicked!");
                    CardModel data = (CardModel)dataObject;
                    Uri uri = Uri.parse(data.newsURL);
               //    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    //Intent intent;
                    //intent = new Intent(this, WebViewActivity.class);

//                    startActivity(intent);

                    openWebViewActivity(data.newsURL,data.mContent);
                  //  Log.e("movement" , "Item Tapped!" + itemPosition);
                }
            });



            flingContainer.setVisibility(View.GONE);
}
        // change fragment;
        if(tinydb.getList("sources").size() <=0)   // If user hasn't selected news source before.
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
            tinydb.putString("currentView","news");
            flingContainer.setVisibility(View.VISIBLE);
            request.test();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tinydb.putList("reads",reads);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tinydb.putList("reads",reads);
        putSavedNews();
        unregisterReceivers();
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceivers();
    }

    public void putSavedNews()
    {
        JSONObject tmpJson =  new JSONObject();
        try {
            tmpJson.put("result",newsArrayToSave);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tinydb.putString("savedNews",tmpJson.toString());
    }

    public JSONArray RemoveJSONArray( JSONArray jarray,String newsid) {

        JSONArray Njarray=new JSONArray();

        try{
            for(int i=0;i<jarray.length();i++){
                String tmpnewsID = jarray.getJSONObject(i).getString("newsID");
                if(!tmpnewsID.equals(newsid))
                    Njarray.put(jarray.get(i));
            }
        }catch (Exception e){e.printStackTrace();}
        return Njarray;

    }


    public void openWebViewActivity(String webviewURL,String content)
    {
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("url", webviewURL);
        i.putExtra("content",content);
        startActivity(i);
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

                swapFragment=PlaceholderFragment.newInstance(position + 1);
                tinydb.putString("currentView","news");
                flingContainer.setVisibility(View.VISIBLE);
                request.test();

                break;
            case 3:
                swapFragment=PlaceholderFragment.newInstance(position + 1);
                tinydb.putString("currentView","saved");
                flingContainer.setVisibility(View.VISIBLE);
                addCardsFromJson(tinydb.getString("savedNews"),true);
                getSupportActionBar().setTitle("Saklanan Haberler");
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
                tinydb.putString("currentView","news");
                break;
            case 4:
                mTitle="Saklanan Haberler";
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public void setRefreshActionButtonState(final boolean refreshing) {
        if (optionsMenu != null) {
            final MenuItem refreshItem = optionsMenu
                    .findItem(R.id.action_refresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            this.optionsMenu = menu;
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

                    request.test();
                    break;
                case "news":
                //Change the card.
                    break;
            }

           // Log.e("Next","Next Button On Click");
            return true;
        }
        else if(id == R.id.action_refresh)
        {
           // setRefreshActionButtonState(true);
            if(!tinydb.getString("currentView").equals("saved"))
            request.test();

            return true;
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
      //  Log.e("JSON",result);
      addCardsFromJson(result,false);

    }

    public void addCardsFromJson(String result,boolean isSaved)
    {

        al.clear();
        flingContainer.removeAllViewsInLayout();
        cardAdapter.notifyDataSetChanged();

        if(result != null) {

            JSONObject json = null; // convert String to JSONObject
            JSONArray articles;
            try {
                json = new JSONObject(result);

                articles = json.getJSONArray("result"); // get articles array

               // Log.e("LENGTH", Integer.toString(articles.length())); // --> 2
             //   Log.e("al length", Integer.toString(al.size())); // --> 2


                for (int i = 0; i < articles.length(); i++) {

                    boolean isThere = false;
                    for (int h = 0; h < reads.size(); h++) {

                        // Log.e("IS READ",reads.get(h) + "  " +articles.getJSONObject(i).getString("newsID") );
                        if (reads.get(h).equals(articles.getJSONObject(i).getString("newsID")) && !isSaved)   // Checking if it has been read already.
                        {
                            // Log.e("Read size",Integer.toString(reads.size()));

                            isThere = true;
                        }
                    }
                    if (!isThere)  // if it is not then add it to arraylist.
                    {
                        String content = null;

                        content = articles.getJSONObject(i).getString("content");
                        Log.e("CONTENT",content);
                        if (content == null)
                            content = "Yükleniyor...";


                        al.add(new CardModel(articles.getJSONObject(i).getString("title"), articles.getJSONObject(i).getString("image"),
                                articles.getJSONObject(i).getString("url"), articles.getJSONObject(i).getString("newsID"),content));
                    }



                }

                cardAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setRefreshActionButtonState(false);
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

            AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
        //PushWOoSH
        //Registration receiver
        BroadcastReceiver mBroadcastReceiver = new RegisterBroadcastReceiver()
        {
            @Override
            public void onRegisterActionReceive(Context context, Intent intent)
            {
                checkMessage(intent);
            }
        };

    //Push message receiver
    private BroadcastReceiver mReceiver = new BasePushMessageReceiver()
    {
        @Override
        protected void onMessageReceive(Intent intent)
        {
            //JSON_DATA_KEY contains JSON payload of push notification.
         //   showMessage("push message is " + intent.getExtras().getString(JSON_DATA_KEY));
        }
    };

    //Registration of the receivers
    public void registerReceivers()
    {
        IntentFilter intentFilter = new IntentFilter(getPackageName() + ".action.PUSH_MESSAGE_RECEIVE");

        registerReceiver(mReceiver, intentFilter, getPackageName() +".permission.C2D_MESSAGE", null);

        registerReceiver(mBroadcastReceiver, new IntentFilter(getPackageName() + "." + PushManager.REGISTER_BROAD_CAST_ACTION));
    }

    public void unregisterReceivers()
    {
        //Unregister receivers on pause
        try
        {
            unregisterReceiver(mReceiver);
        }
        catch (Exception e)
        {
            // pass.
        }

        try
        {
            unregisterReceiver(mBroadcastReceiver);
        }
        catch (Exception e)
        {
            //pass through
        }
    }


    private void checkMessage(Intent intent)
    {
        if (null != intent)
        {
            if (intent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
            {
              //  showMessage("push message is " + intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT));
            }
            else if (intent.hasExtra(PushManager.REGISTER_EVENT))
            {
               // showMessage("register");
            }
            else if (intent.hasExtra(PushManager.UNREGISTER_EVENT))
            {
             //   showMessage("unregister");
            }
            else if (intent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
            {
              //  showMessage("register error");
            }
            else if (intent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
            {
              //  showMessage("unregister error");
            }

            resetIntentValues();
        }
    }

    /**
     * Will check main Activity intent and if it contains any PushWoosh data, will clear it
     */
    private void resetIntentValues()
    {
        Intent mainAppIntent = getIntent();

        if (mainAppIntent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.PUSH_RECEIVE_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.REGISTER_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.REGISTER_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.UNREGISTER_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.REGISTER_ERROR_EVENT);
        }
        else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
        {
            mainAppIntent.removeExtra(PushManager.UNREGISTER_ERROR_EVENT);
        }

        setIntent(mainAppIntent);
    }

    private void showMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);

        checkMessage(intent);
    }


}
