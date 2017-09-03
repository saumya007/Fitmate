package com.saumya.fitmate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saumya.fitmate.R;
import com.saumya.fitmate.beans.horizontal_beans;
import com.saumya.fitmate.beans.main_beans;
import com.saumya.fitmate.beans.selector_beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saumyamehta on 8/25/17.
 */

public class Selector_Adapter extends RecyclerView.Adapter<Selector_Adapter.MyViewHolder> {
    private Context mContext;
    private List<selector_beans> albumList =new ArrayList<>();
    private List<main_beans> main_beansList = new ArrayList<>();
    private List<horizontal_beans> horizontal_beanses = new ArrayList<>();

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reycler_selector_child, parent, false);

        return new MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final selector_beans singleItem = albumList.get(position);
        holder.title.setText(singleItem.getName());
        holder.thumbnail.setImageResource(singleItem.getThubmnail());
        AppBucketDrops.setRalewayThin(mContext, holder.title);

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView thumbnail;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.selector_text);
            thumbnail = (ImageView)itemView.findViewById(R.id.img_selector);

        }
    }

    public List<selector_beans> getAlbumList() {
        Log.e("list",albumList+"");
        return albumList;
    }

    public selector_beans getItem(int position)
    {
        return albumList.get(position);
    }
    public  Selector_Adapter()
    {

    }
    public Selector_Adapter(Context mContext, ArrayList<selector_beans> albumList)
    {
        this.mContext = mContext;
        this.albumList = albumList;

    }
}
