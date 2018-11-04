package lmniit.hackx.aesher.lnmniit_hacx.Threads;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class NewsFetcher extends AsyncTask<Void ,Void ,JSONArray> {

    String URL = "https://newsapi.org/v2/top-headlines?sources=medical-news-today&apiKey=55b700e5d7594d64830ce97cf400d460";
    String TAG = "NewsFetcher";
    private  NewsInterface newsInterface;

    public  NewsFetcher(NewsInterface newsInterface){
      this.newsInterface = newsInterface;
    }




    @Override
    protected JSONArray doInBackground(Void... voids) {

        JSONArray newsArray = null;
        
        try{
            String getNews = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            JSONObject readableNews = new JSONObject(getNews);

            newsArray = readableNews.getJSONArray("articles");



        }
        catch (Exception e){
            Log.w(TAG, e);
        }

        return newsArray;


    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);

        newsInterface.ProcessFinished(jsonArray);
    }
}
