package com.example.yday_19_02;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
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
    private LayoutInflater layoutInflater;
    private Context context;
    private RecyclerView recyclerView;
    private AddItemClickListener addItemClickListener;
    private int judge;
    public void setAddItemClickListener(AddItemClickListener addItemClickListener){
        this.addItemClickListener=addItemClickListener;
    }
    public interface AddItemClickListener{
        void onItemClick(RecyclerView parent,int position,View view,List<Music> list);
    }
    public RecyclerAdapter(List<Music> list, Context context){
        this.list=list;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cancel;
        TextView musicName;
        TextView singerName;
        public ViewHolder(View itemView) {
            super(itemView);
            cancel= (ImageView) itemView.findViewById(R.id.item_cancel);
            musicName= (TextView) itemView.findViewById(R.id.item_musicName);
            singerName= (TextView) itemView.findViewById(R.id.item_singerName);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.list_item,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=recyclerView.getChildAdapterPosition(v);
                addItemClickListener.onItemClick(recyclerView,position,v,list);
                Intent intent=new Intent(context,MusicService.class);
                intent.putExtra("pathKey",list.get(position).getData());
                intent.putExtra("actionKey","PLAY");
                context.startService(intent);
                judge=position;
                notifyDataSetChanged();
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView=recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView=recyclerView;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.musicName.setText(list.get(position).getMusicName());
        holder.singerName.setText(list.get(position).getSingerName());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judge==position){
                    Intent intent=new Intent(context,MusicService.class);
                    intent.putExtra("actionKey","STOP");
                    context.startService(intent);
                }
                list.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    public void addMusic(List<Music> list){
        this.list.addAll(list);
    }

}
