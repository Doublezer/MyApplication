package com.example.yday_19_001;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11/011.
 */

public class WorkAsync extends AsyncTask<Void,Void,List<Music>> {
    private WeakReference<SwipeRefreshLayout> weakReference;
    private Context context;
    public WorkAsync(SwipeRefreshLayout swipeRefreshLayout, Context context){
        weakReference=new WeakReference<SwipeRefreshLayout>(swipeRefreshLayout);
        this.context=context;
    }
    @Override
    protected List<Music> doInBackground(Void... params) {
        List<Music> temp=new LinkedList<>();
        ContentResolver contentResolver=context.getContentResolver();
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection={MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DATA};
        String selection=MediaStore.Audio.Media.IS_MUSIC+"=?";
        String[] selectionArgs={"1"};
        String sorOrder=MediaStore.Audio.Media.DATE_ADDED+" desc ";
        Cursor cursor=contentResolver.query(uri,projection,selection,selectionArgs,sorOrder);
        if (cursor.moveToFirst()){
            for (;!cursor.isAfterLast();cursor.moveToNext()){
                String musicName=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String singerName=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String data=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                Music music=new Music(musicName,singerName,data);
                temp.add(music);
            }
        }
        return temp;
    }

    @Override
    protected void onPostExecute(List<Music> list) {
        if (weakReference.get()==null)return;
        SwipeRefreshLayout swipeRefreshLayout=weakReference.get();
        swipeRefreshLayout.setRefreshing(false);
        RecyclerView recyclerView= (RecyclerView) swipeRefreshLayout.findViewById(R.id.frg_recycler);
        RecyclerAdapter recyclerAdapter= (RecyclerAdapter) recyclerView.getAdapter();
        recyclerAdapter.addMusic(list);
        recyclerAdapter.notifyDataSetChanged();
        super.onPostExecute(list);
    }
}
