package com.example.yday_19_02;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildFragment_01 extends Fragment implements SwipeRefreshLayout.OnRefreshListener,RecyclerAdapter.AddItemClickListener,View.OnClickListener{

    private List<Music> list;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isChange=false;
    public ChildFragment_01() {
        // Required empty public constructor
        list=new LinkedList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_child_fragment_01, container, false);
        setSwipe(view);
        setRecycler(view);
        FloatingActionButton fab= (FloatingActionButton) view.findViewById(R.id.frg_fab);
        fab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadMusic();
    }

    private void setRecycler(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.frg_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(list,getContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setAddItemClickListener(this);
    }

    private void setSwipe(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.frg_swipe);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN);
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
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
    public void onItemClick(RecyclerView parent, int position, View view, List<Music> list) {

    }

    @Override
    public void onClick(View v) {
        if (!isChange){
            isChange=true;
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }else{
            isChange=false;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
}
