package com.example.yday_19_001;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{


    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerAdapter recyclerAdapter;
    private List<Music> list;
    private boolean isChange=false;
    public MusicListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_music_list, container, false);
        setRecycler(view);
        setSwipe(view);
        FloatingActionButton fab= (FloatingActionButton) view.findViewById(R.id.frg_fab);
        fab.setOnClickListener(this);
        return view;
    }

    private void setRecycler(View view) {
        list=new LinkedList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.frg_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(list);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setSwipe(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.frg_swipe);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN,Color.BLUE,Color.YELLOW,Color.RED);
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        loadMusic();
    }

    private void loadMusic() {
        WorkAsync workAsync=new WorkAsync(swipeRefreshLayout,getActivity().getApplicationContext());
        workAsync.executeOnExecutor(Executors.newCachedThreadPool());
    }

    @Override
    public void onClick(View v) {
        if (!isChange){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            isChange=true;
        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            isChange=false;
        }
    }
}
