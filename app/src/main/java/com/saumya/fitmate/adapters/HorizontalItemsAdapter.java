package com.saumya.fitmate.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saumya.fitmate.Home;
import com.saumya.fitmate.R;
import com.saumya.fitmate.beans.horizontal_beans;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * Created by saumyamehta on 8/24/17.
 */
public class HorizontalItemsAdapter extends RecyclerView.Adapter<HorizontalItemsAdapter.SingleItemRowHolder> {

    private ArrayList<horizontal_beans> itemsList;
    private Context mContext;




    public HorizontalItemsAdapter(Context context, ArrayList<horizontal_beans> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_horizontal_inside, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        final Animation mAnims = AnimationUtils.loadAnimation(mContext, R.anim.bouncy);
        final horizontal_beans singleItem = itemsList.get(i);
        holder.count.setText(singleItem.getCount());
        holder.addbtn.setImageResource(singleItem.getAddbtn());
        holder.thumbnail.setImageResource(singleItem.getThumbnail());
        holder.mCancelbtn.setImageResource(singleItem.getCancelimg());
        holder.title1.setText(singleItem.getTitle());
        AppBucketDrops.setRalewayThin(mContext, holder.title1);

        holder.mCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.warning(mContext,"here",Toast.LENGTH_SHORT).show();
                holder.mCancelbtn.setVisibility(View.GONE);
                Log.e("position",holder.getAdapterPosition()+"items"+itemsList.get(holder.getAdapterPosition()).getTitle());
                holder.mRel.clearAnimation();
                itemsList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                View itemView = LayoutInflater.from(mContext)
                        .inflate(R.layout.recycler_main_child, null);
                Log.e("sds",itemView.findViewById(R.id.recycler_horizontal)+"");
                main_adapter.MyViewHolder myViewHolder  = new main_adapter.MyViewHolder(itemView);
                itemView.findViewById(R.id.recycler_horizontal).invalidate();

            }
        });
        /*holder.mRel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(holder.mCancelbtn.getVisibility() == View.GONE)
                {
                    holder.mCancelbtn.setVisibility(View.VISIBLE);
                }
                mAnims.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnims.setDuration(500);
                mAnims.setRepeatCount(Animation.INFINITE);
                mAnims.setRepeatMode(Animation.REVERSE);
                v.startAnimation(mAnims);
                Toasty.warning(mContext, " true", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/
        AppBucketDrops.setRalewayThin(mContext, holder.count);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public HorizontalItemsAdapter() {

    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected ImageView itemImage;
        private TextView title1, count;
        public ImageView thumbnail, addbtn;
        public ImageButton mCancelbtn;
        public RelativeLayout mRel;
        public SingleItemRowHolder(View view) {
            super(view);
            mRel = (RelativeLayout)view.findViewById(R.id.rels);
            count = (TextView) view.findViewById(R.id.water_cups_count_text);
            title1 = (TextView) view.findViewById(R.id.water_name_text);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            addbtn = (ImageView) view.findViewById(R.id.btn_add);
            mCancelbtn = (ImageButton) view.findViewById(R.id.cancel);
           /*
            mCancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });


        }

    }

}

