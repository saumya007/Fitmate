package com.saumya.fitmate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saumya.fitmate.R;
import com.saumya.fitmate.beans.horizontal_beans;
import com.saumya.fitmate.beans.main_beans;

import java.util.List;

/**
 * Created by saumyamehta on 8/24/17.
 */

public class recycler_horizontal extends RecyclerView.Adapter<recycler_horizontal.MyViewHolder> {

    private Context mContext;
    private List<horizontal_beans> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, title1,count;
        public ImageView thumbnail, addbtn;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text_indicator);
            count = (TextView)view.findViewById(R.id.water_cups_count_text);
            title1 = (TextView)view.findViewById(R.id.water_name_text);
            thumbnail = (ImageView)view.findViewById(R.id.thumbnail);
            addbtn = (ImageView)view.findViewById(R.id.btn_add);
        }
    }


    public recycler_horizontal(Context mContext, List<horizontal_beans> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_horizontal_inside, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        horizontal_beans album = albumList.get(position);
        holder.count.setText(album.getCount());
        holder.addbtn.setImageResource(album.getAddbtn());
        holder.thumbnail.setImageResource(album.getThumbnail());
        holder.title1.setText(album.getTitle());
        AppBucketDrops.setRalewayBold(mContext,holder.title);

        //  AppBucketDrops.setRalewayThin(mContext,holder.count,holder.title1);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}