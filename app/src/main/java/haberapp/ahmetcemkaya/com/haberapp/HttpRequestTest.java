package haberapp.ahmetcemkaya.com.haberapp;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ahmetcem on 26.2.2015.
 */
public class HttpRequestTest {

    Activity main;
    JsonListener jsonListener;
    ArrayList<String> categories;
    ArrayList<String> sources;
    TinyDB tinydb;

    public interface JsonListener
    {

        void onGetJsonListener(String result);

    }
    public HttpRequestTest(Activity activity){

        main = activity;
        categories = new ArrayList<String>();
        sources = new ArrayList<String>();
        tinydb = new TinyDB(main);

        updateConstraints();
    }

    public void updateConstraints()
    {
        categories.clear();
        sources.clear();
       ArrayList<String> categoriesList =  tinydb.getList("categories");
       ArrayList<String> sourceList = tinydb.getList("sources");

        for ( int i = 0 ; i < categoriesList.size() ; i++)
        {
            switch(categoriesList.get(i))
            {
                case "0":
                    categories.add("world");
                    break;
                case "1":
                   categories.add("economy");
                    break;
                case "2":
                    categories.add("politics");
                    break;
                case "3":
                    categories.add("sport");
                    break;
                case "4":
                    categories.add("magazin");
                    break;
                case "5":
                    categories.add("gundem");
                    break;
                case "6":
                    categories.add("education");
                    break;
                case "7":
                    categories.add("technology");
                    break;
                case "8":
                    categories.add("science");
                    break;
                case "9":
                    categories.add("health");
                    break;
                case "10":
                    categories.add("art");
                    break;

            }
        }

        for (int h = 0; h < sourceList.size();h ++)
        {
            switch(sourceList.get(h))
            {
                case "0":
                    sources.add("hurriyet");
                    break;
                case "1":
                    sources.add("milliyet");
                    break;
                case "2":
                    sources.add("zaman");
                    break;
                case "3":
                    sources.add("radikal");
                    break;
                case "4":
                    sources.add("sporx");
                    break;
                case "5":
                    sources.add("ntv");
                    break;
                case "6":
                    sources.add("diken");
                    break;
                case "7":
                    sources.add("odatv");
                    break;
                case "8":
                    sources.add("ifl");
                    break;
                case "9":
                    sources.add("sozcu");
                    break;
                case "10":
                    sources.add("amk");
                    break;
                case "11":
                    sources.add("haberapp");
                    break;

            }
        }
    }

    public String GET(String url){
        InputStream inputStream = null;
        String result = "";

        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();


            HttpPost httpost = new HttpPost(url);

            //convert parameters into JSON object
           JSONObject holder = new JSONObject();
            holder.put("categories",new JSONArray(categories));
            holder.put("sources",new JSONArray(sources));

            //passes the results to a string builder/entity
            StringEntity se = new StringEntity(holder.toString());

            //sets the post request as the resulting string
            httpost.setEntity(se);
            //sets a request header so the page receving the request
            //will know what to do with it
            httpost.setHeader("Accept", "application/json");
            httpost.setHeader("Content-type", "application/json");



            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpost);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    public void test()
    {
        ((MainActivity) main).setRefreshActionButtonState(true);
        updateConstraints();
        new HttpAsyncTask().execute("http://178.62.245.125:3000/testGetNewsByPost");
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) main.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(main,"Recieved!", Toast.LENGTH_LONG).show();
            jsonListener.onGetJsonListener(result);
        }
    }
}



