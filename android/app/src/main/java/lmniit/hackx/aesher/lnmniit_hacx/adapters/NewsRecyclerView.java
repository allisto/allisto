package lmniit.hackx.aesher.lnmniit_hacx.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lmniit.hackx.aesher.lnmniit_hacx.R;

public class NewsRecyclerView extends RecyclerView.Adapter<NewsRecyclerView.Viewholder> {

    JSONArray newsArray;
    Context context;



    public NewsRecyclerView(JSONArray newsArray, Context context){
        this.newsArray = newsArray;
        this.context = context;
    }




    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_top_news,viewGroup,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder viewholder, int i) {


        try {

            final JSONObject newsObject = newsArray.getJSONObject(i);
            viewholder.title.setText(newsObject.getString("title"));
            Picasso.get().load(newsObject.getString("urlToImage")).into(viewholder.imageView);
            viewholder.news.setText(newsObject.getString("description"));

            viewholder.MoreNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = null;
                    try {
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsObject.get("url").toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    context.startActivity(browserIntent);
                }
            });


        }
        catch (Exception e){
            Log.w("RecyclerView Error", e);
        }
    }

    @Override
    public int getItemCount() {
        return newsArray.length();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView title, news;
        ImageView imageView;
        LinearLayout MoreNews;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            imageView = itemView.findViewById(R.id.news_image);
            news = itemView.findViewById(R.id.news_description);
            MoreNews = itemView.findViewById(R.id.news_more);
        }
    }
}
