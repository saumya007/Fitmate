package com.saumya.fitmate.adapters;

import android.support.v7.widget.RecyclerView;

/**
 * Created by saumyamehta on 6/22/17.
 */

public interface SwipeListener {
    void onSwipe(int position, RecyclerView.ViewHolder vh);
}
