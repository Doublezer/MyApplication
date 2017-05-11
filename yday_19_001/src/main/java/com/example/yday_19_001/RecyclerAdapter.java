package com.example.yday_19_001;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11/011.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Music> list;
    public RecyclerAdapter(List<Music> list){
        this.list=list;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView musicName;
        TextView singerName;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.item_iv);
            musicName= (TextView) itemView.findViewById(R.id.item_musicName);
            singerName= (TextView) itemView.findViewById(R.id.item_singerName);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.mipmap.ic_launcher);
        holder.musicName.setText(list.get(position).getMusicName());
        holder.singerName.setText(list.get(position).getSingerName());
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public void addMusic(List<Music> list){
        this.list.addAll(list);
    }
}
