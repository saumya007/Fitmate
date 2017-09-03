package com.saumya.fitmate.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chootdev.recycleclick.RecycleClick;
import com.saumya.fitmate.R;
import com.saumya.fitmate.beans.main_beans;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class main_adapter extends RecyclerView.Adapter<main_adapter.MyViewHolder> {

    private Context mContext;
    private List<main_beans> albumList;
    private HorizontalItemsAdapter horizontalItemsAdapter;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;/*, title1,count;
        public ImageView thumbnail, addbtn;*/
        public RecyclerView mRecyclerbottom;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text_indicator);
            mRecyclerbottom = (RecyclerView) view.findViewById(R.id.recycler_horizontal);

            /*count = (TextView)view.findViewById(R.id.water_cups_count_text);
            title1 = (TextView)view.findViewById(R.id.water_name_text);
            thumbnail = (ImageView)view.findViewById(R.id.thumbnail);
            addbtn = (ImageView)view.findViewById(R.id.btn_add);*/
        }

    }


    public main_adapter(Context mContext, List<main_beans> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    public void updateAdapter(/* gets params like TITLES,ICONS,NAME,EMAIL,PROFILE*/) {

        // update adapter element like NAME, EMAIL e.t.c. here

        // then in order to refresh the views notify the RecyclerView
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_main_child, parent, false);

        return new MyViewHolder(itemView);
    }

    public main_beans getItem(int position) {
        return albumList.get(position);
    }

    public main_adapter() {

    }

    public void removeitems() {
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        main_beans album = albumList.get(position);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("view", holder.itemView.toString());
        editor.commit();
        Log.e("bhb", album + "hhj");
        holder.title.setText(album.getName());
        holder.mRecyclerbottom.setHasFixedSize(true);
        holder.mRecyclerbottom.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        ArrayList singleSectionItems = albumList.get(position).getAllItemsInSection();
        HorizontalItemsAdapter itemListDataAdapter = new HorizontalItemsAdapter(mContext, singleSectionItems);

        holder.mRecyclerbottom.setAdapter(itemListDataAdapter);
        Log.e("hhihi",holder.mRecyclerbottom.findViewById(R.id.recycler_bottom_container)+""+position);
        if (albumList.get(position).getAllItemsInSection() == null || albumList.get(position).getAllItemsInSection().isEmpty()) {

            albumList.remove(position);

            Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();

                }
            };
            handler.post(runnable);
        }
        final Animation mAnims = AnimationUtils.loadAnimation(mContext,R.anim.bouncy);

        RecycleClick.addTo(holder.mRecyclerbottom).setOnItemLongClickListener(new RecycleClick.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, final int positions, final View v) {


                if(v.getId() == R.id.recycler_bottom_container){
                    Toasty.warning(mContext, " true", Toast.LENGTH_SHORT).show();

                    mAnims.setInterpolator(new AccelerateDecelerateInterpolator());
                    mAnims.setDuration(500);
                    mAnims.setRepeatCount(Animation.INFINITE);
                    mAnims.setRepeatMode(Animation.REVERSE);
                    v.findViewById(R.id.rels).startAnimation(mAnims);
                    if(v.findViewById(R.id.cancel).getVisibility() == View.GONE)
                    {
                        v.findViewById(R.id.cancel).setVisibility(View.VISIBLE);
                        v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View views) {
                                Log.e("dsdsdsd","ddsdsdsds"+position+"pos"+ albumList.get(position).getAllItemsInSection().size()+" df"+positions);
                                v.findViewById(R.id.rels).clearAnimation();
                                albumList.get(position).getAllItemsInSection().remove(positions);
                                new HorizontalItemsAdapter().notifyDataSetChanged();
                                notifyDataSetChanged();

                            }
                        });
                    }

                    Log.e("voola",v.findViewById(R.id.cancel)+"dsds");
                }
                return true;

            }

        });

        AppBucketDrops.setRalewayBold(mContext, holder.title);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
