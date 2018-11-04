package lmniit.hackx.aesher.lnmniit_hacx.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import lmniit.hackx.aesher.lnmniit_hacx.R;
import lmniit.hackx.aesher.lnmniit_hacx.Threads.NewsFetcher;
import lmniit.hackx.aesher.lnmniit_hacx.Threads.NewsInterface;
import lmniit.hackx.aesher.lnmniit_hacx.adapters.NewsRecyclerView;

public class News extends Fragment {

    String TAG = "News";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container , false);

        ButterKnife.bind(this,v);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);


        new NewsFetcher(new NewsInterface() {
            @Override
            public void ProcessFinished(final JSONArray jsonArray) {
                Log.w(TAG, jsonArray.toString());

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new NewsRecyclerView(jsonArray, getContext()));
                    }
                });
            }
        }).execute();




        return v;

    }
}
