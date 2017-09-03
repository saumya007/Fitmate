package com.saumya.fitmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.recycleclick.RecycleClick;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.saumya.fitmate.adapters.AppBucketDrops;
import com.saumya.fitmate.adapters.Selector_Adapter;
import com.saumya.fitmate.adapters.main_adapter;
import com.saumya.fitmate.beans.horizontal_beans;
import com.saumya.fitmate.beans.main_beans;
import com.saumya.fitmate.beans.selector_beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.dmoral.toasty.Toasty;


public class Home extends Fragment {
    public static RecyclerView mRecycler1;
    public static main_adapter mAdapters;
    private TextView mDescactup, mDescfoodup, mDescsleepup, mDescactdown, mDescfooddown, mInfoAct, mInfoSleep, mInfofood;
    private FloatingActionMenu mFloatingActionMenu;
    private FloatingActionButton mActivities, mBmi, mDiet;
    public static List<main_beans> mMainList = new ArrayList<>();
    public main_beans mMain = new main_beans();
    public static ArrayList<horizontal_beans> singleItem;
    private View v;
    public static ArrayList<horizontal_beans> mhorizontal_activity = new ArrayList<>();
    public static ArrayList<horizontal_beans> mhorizontal_food = new ArrayList<>();
    public static ArrayList<horizontal_beans> mhorizontal_other = new ArrayList<>();
    private Activity activity;

    String names, titles;
    int images;
    private boolean done = false;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
        mAdapters.notifyDataSetChanged();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.v = view;
        Bundle bundle = new Bundle();
        bundle.putString("viewss", v.toString());
        Log.e("asjas", bundle.getString("viewss"));
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        //... constructor
        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("Error", "IndexOutOfBoundsException in RecyclerView happens");
            }
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler1 = (RecyclerView) view.findViewById(R.id.recycler_bottom_card);
        mRecycler1.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        mAdapters = new main_adapter(getActivity(),mMainList);
        mAdapters.setHasStableIds(false);
        mRecycler1.setAdapter(mAdapters);



        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (sp != null) {
            Gson gson = new Gson();
            main_beans mList[] = gson.fromJson(sp.getString("all", ""), main_beans[].class);
            if (mList != null) {
                int counts=0;
                        for (int j = 0; j < mList.length; j++) {

                            if (mMainList.size() > 0) {
                                for (int i = 0; i < mMainList.size(); i++) {

                                    if (!mMainList.get(i).getAllItemsInSection().equals(mList[j].getAllItemsInSection())) {
                                        counts++;
                                        if (counts >= mMainList.size()) {
                                            Log.e("counts", counts + "mMain" + mMainList.get(i).getAllItemsInSection() + "mList" + mList[j].getAllItemsInSection());
                                            counts = 0;
                                            if(mList[j].getName().equals("Activities"))
                                            {
                                                mhorizontal_activity.addAll(mList[j].getAllItemsInSection());
                                            }
                                            else if(mList[j].getName().equals("Diet"))
                                            {
                                                mhorizontal_food.addAll(mList[j].getAllItemsInSection());
                                            }
                                            else if(mList[j].getName().equals("Others"))
                                            {
                                                mhorizontal_other.addAll(mList[j].getAllItemsInSection());
                                            }

                                            mMainList.add(mList[j]);
                                            mAdapters.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                            else{
                                mMainList.add(mList[0]);
                                if(mList[0].getName().equals("Activities"))
                                {
                                    mhorizontal_activity.addAll(mList[0].getAllItemsInSection());
                                }
                                else if(mList[0].getName().equals("Diet"))
                                {
                                    mhorizontal_food.addAll(mList[0].getAllItemsInSection());
                                }
                                else if(mList[0].getName().equals("Others"))
                                {
                                    mhorizontal_other.addAll(mList[0].getAllItemsInSection());
                                }
                                mAdapters.notifyDataSetChanged();
                            }

                    }

            }
        }

        /*mMain = new main_beans();
        mMain.setName("Diet");
        singleItem = new ArrayList<horizontal_beans>();
        singleItem.add(new horizontal_beans("2 ", R.drawable.add, "Water",R.drawable.watericon));
        mMain.setAllItemsInSection(singleItem);
        mMainList.add(mMain);
        mAdapter.notifyDataSetChanged();*/

        mDescactup = (TextView) view.findViewById(R.id.text_run_up);
        mDescactdown = (TextView) view.findViewById(R.id.text_run_down);
        mDescfoodup = (TextView) view.findViewById(R.id.text_food_up);
        mDescactdown = (TextView) view.findViewById(R.id.text_food_down);
        mDescsleepup = (TextView) view.findViewById(R.id.text_sleep);
        mInfoAct = (TextView) view.findViewById(R.id.indicator_txt_run);
        mInfofood = (TextView) view.findViewById(R.id.indicator_txt_food);
        mInfoSleep = (TextView) view.findViewById(R.id.indicator_txt_sleep);
        AppBucketDrops.setRalewayThin(getActivity(), mDescactup, mDescactdown, mDescfoodup, mDescsleepup, mInfoAct, mInfofood, mInfoSleep/*mDescactdown,mDescfooddown,mDescfoodup,mDescsleepup,mInfoAct,mInfofood,mInfoSleep*/);
        mFloatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.menu);
        mActivities = (FloatingActionButton) view.findViewById(R.id.menu_item1);
        mBmi = (FloatingActionButton) view.findViewById(R.id.menu_item3);
        mDiet = (FloatingActionButton) view.findViewById(R.id.menu_item2);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Thin.ttf");
        SpannableString mNewTitle0 = new SpannableString("Activities");
        SpannableString mNewTitle1 = new SpannableString("Diet");
        mNewTitle0.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle0.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        SpannableString mNewTitle2 = new SpannableString("Others");
        mNewTitle1.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mNewTitle2.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mActivities.setLabelText(String.valueOf(mNewTitle0));
        mDiet.setLabelText(mNewTitle1.toString());
        mBmi.setLabelText(mNewTitle2.toString());
        mActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatingActionMenu.isOpened()) {
                    mFloatingActionMenu.close(true);
                    DialogSelector ds = new DialogSelector();
                    Bundle bundle = new Bundle();
                    bundle.putString("Label_Main", mActivities.getLabelText());
                    ds.setArguments(bundle);
                    ds.setTargetFragment(new Home(), 0);
                    ds.show(getActivity().getSupportFragmentManager(), "Activities");
                }
            }
        });
        mDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatingActionMenu.isOpened()) {
                    mFloatingActionMenu.close(true);
                    DialogSelector ds = new DialogSelector();
                    Bundle bundle = new Bundle();
                    bundle.putString("Label_Main", mDiet.getLabelText());
                    ds.setArguments(bundle);
                    ds.setTargetFragment(new Home(), 0);
                    ds.show(getActivity().getSupportFragmentManager(), "Diet");
                }

            }
        });
        mBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFloatingActionMenu.isOpened()) {
                    mFloatingActionMenu.close(true);
                    DialogSelector ds = new DialogSelector();
                    Bundle bundle = new Bundle();
                    bundle.putString("Label_Main", mBmi.getLabelText());
                    ds.setArguments(bundle);
                    ds.setTargetFragment(new Home(), 0);
                    ds.show(getActivity().getSupportFragmentManager(), "Others");
                }
            }
        });
        Log.e("mainl", mMainList.size() + "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            names = data.getStringExtra("name");
            titles = data.getStringExtra("title");
            int image = data.getIntExtra("image", 0);
            Bundle b = new Bundle();
            Log.e("bundle", b.getString("viewss") + "ssasas");

            done = true;
        }
    }

    private void populate(List<main_beans> mMainList) {


    }

    public static class DialogSelector extends DialogFragment {

        private RecyclerView mRecycler;
        private Selector_Adapter mAdapter;
        private ArrayList<selector_beans> mActivities, mDiet, mOther;
        private int countclicks1 = 0;
        private View vs;
        private main_beans mMains = new main_beans();

        public DialogSelector() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            return inflater.inflate(R.layout.fragment_dialog_selector, container, false);

        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Bundle b = getArguments();
            final Selector_Adapter mAdapter;

            final String name = b.get("Label_Main").toString();
            mRecycler = (RecyclerView) view.findViewById(R.id.recycler_selector);

            mRecycler.addItemDecoration(new SpacesItemDecoration(10));
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRecycler.setLayoutManager(mLayoutManager);
            mRecycler.setItemAnimator(new DefaultItemAnimator());
            Log.e("name", name);
            mActivities = new ArrayList<>();
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
            SpannableString mNewTitle = new SpannableString("Running");
            mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used0)), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            SpannableString mNewTitle01 = new SpannableString("Walking");
            mNewTitle01.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle01.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle01.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle01.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle01.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used0)), 0, mNewTitle01.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            SpannableString mNewTitle02 = new SpannableString("Cycling");
            mNewTitle02.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle02.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle02.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle02.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle02.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used0)), 0, mNewTitle02.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            mActivities.add(new selector_beans(name, R.drawable.run, mNewTitle));
            mActivities.add(new selector_beans(name, R.drawable.walk, mNewTitle01));
            mActivities.add(new selector_beans(name, R.drawable.cycling, mNewTitle02));
            mDiet = new ArrayList<>();

            SpannableString mNewTitle1 = new SpannableString("Water");
            mNewTitle1.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle1.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used5)), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            SpannableString mNewTitle11 = new SpannableString("Coffee");
            mNewTitle11.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle11.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle11.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle11.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle11.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used4)), 0, mNewTitle11.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            SpannableString mNewTitle12 = new SpannableString("Food");
            mNewTitle12.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle12.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle12.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle12.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle12.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used2)), 0, mNewTitle12.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            SpannableString mNewTitle21 = new SpannableString("Weight");
            mNewTitle21.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle21.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle21.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle21.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle21.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used6)), 0, mNewTitle21.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            SpannableString mNewTitle22 = new SpannableString("HeartRate");
            mNewTitle22.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle22.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle22.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle22.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mNewTitle22.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used7)), 0, mNewTitle22.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);


            mDiet.add(new selector_beans(name, R.drawable.watericon, mNewTitle1));
            mDiet.add(new selector_beans(name, R.drawable.coffeeicon, mNewTitle11));
            mDiet.add(new selector_beans(name, R.drawable.restaurant, mNewTitle12));
            mOther = new ArrayList<>();
            mOther.add(new selector_beans(name, R.drawable.weight, mNewTitle21));
            mOther.add(new selector_beans(name, R.drawable.heart, mNewTitle22));
            if (name.equalsIgnoreCase("Activities")) {
                mAdapter = new Selector_Adapter(getActivity(), mActivities);
                mRecycler.setAdapter(mAdapter);
                RecycleClick.addTo(mRecycler).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent bundle = new Intent();
                        bundle.putExtra("name", name);
                        Log.e("name", name);
                        bundle.putExtra("image", mAdapter.getItem(position).getThubmnail());
                        bundle.putExtra("title", mAdapter.getItem(position).getName());
                        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
                        SpannableString mNewTitle = new SpannableString("0 km ");
                        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used0)), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        horizontal_beans m = new horizontal_beans(mNewTitle, R.drawable.play, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel);
                        if (mhorizontal_activity.size() > 0) {
                            Log.e("size",mhorizontal_activity.size()+"");
                            int count1 = 0;

                            for (int i = 0; i < mhorizontal_activity.size(); i++) {
                                Log.e("here", "1" + m.getTitle() + mhorizontal_activity.get(i).getTitle() + mhorizontal_activity.get(i).getTitle().toString().equalsIgnoreCase(m.getTitle().toString()));
                                if (!mhorizontal_activity.get(i).getTitle().toString().equalsIgnoreCase(m.getTitle().toString())) {
                                    count1++;
                                }
                            }
                            if (count1 >= mhorizontal_activity.size()) {
                                mhorizontal_activity.add(m);
                                Log.e("here", "2" + m.getTitle());
                                int count = 0;
                                if (mMainList != null && mMainList.size() > 0) {
                                    for (int i = 0; i < mMainList.size(); i++) {
                                        int countj = 0;
                                        if (mMainList.get(i).getName().equals(name)) {

                                            mMainList.remove(i);
                                            mMains.setName(name);
                                            mMains.setAllItemsInSection(mhorizontal_activity);
                                            mMainList.add(mMains);
                                            mAdapters.notifyDataSetChanged();
                                            mRecycler1.setAdapter(mAdapters);

                                        } else {
                                            count++;
                                            Log.e("count", count + "" + mMainList.size() + "size");
                                            if (count >= (mMainList.size())) {
                                                mMains.setName(name);
                                                mMains.setAllItemsInSection(mhorizontal_activity);
                                                mMainList.add(mMains);
                                                mAdapters.notifyDataSetChanged();
                                                count = 0;
                                                break;

                                            }
                                        }

                                    }
                                } else {
                                    mMains.setName(name);
                                    mMains.setAllItemsInSection(mhorizontal_activity);
                                    mMainList.add(mMains);
                                    mAdapters.notifyDataSetChanged();

                                }
                            }
                        } else {
                            mhorizontal_activity.add(new horizontal_beans(mNewTitle, R.drawable.play, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel));
                            Log.e("here", "3" + m.getTitle());
                            int count = 0;
                            if (mMainList != null && mMainList.size() > 0) {
                                for (int i = 0; i < mMainList.size(); i++) {
                                    int countj = 0;
                                    if (mMainList.get(i).getName().equals(name)) {
                                        mMainList.remove(i);
                                        mMains.setName(name);
                                        mMains.setAllItemsInSection(mhorizontal_activity);
                                        mMainList.add(mMains);
                                        mAdapters.notifyDataSetChanged();
                                        mRecycler1.setAdapter(mAdapters);


                                    } else {
                                        count++;
                                        Log.e("count", count + "" + mMainList.size() + "size");
                                        if (count >= (mMainList.size())) {
                                            mMains.setName(name);
                                            mMains.setAllItemsInSection(mhorizontal_activity);
                                            mMainList.add(mMains);
                                            mAdapters.notifyDataSetChanged();

                                            count = 0;
                                            break;

                                        }
                                    }

                                }
                            } else {
                                mMains.setName(name);
                                mMains.setAllItemsInSection(mhorizontal_activity);
                                mMainList.add(mMains);
                                mAdapters.notifyDataSetChanged();

                            }
                        }
                        /*    int counti = 0;
                        for (int i = 0; i < mhorizontal_activity.size(); i++) {

                            if (mhorizontal_activity.equals(m)) {
                                continue;
                            } else {
                                counti++;
                                if (counti >= (mhorizontal_activity.size() - 1)) {
                                }
                            }
                        }*/

                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, bundle);
                        dismiss();

                    }
                });
            } else if (name.equalsIgnoreCase("Diet")) {
                mAdapter = new Selector_Adapter(getActivity(), mDiet);
                mRecycler.setAdapter(mAdapter);
                RecycleClick.addTo(mRecycler).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {


                        Intent bundle = new Intent();
                        bundle.putExtra("name", name);
                        bundle.putExtra("image", mAdapter.getItem(position).getThubmnail());
                        bundle.putExtra("title", mAdapter.getItem(position).getName());
                        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
                        SpannableString mNewTitle = new SpannableString("0 glasses");
                        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used3)), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        SpannableString mNewTitle1 = new SpannableString("0 cups");
                        mNewTitle1.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle1.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used4)), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        SpannableString mNewTitle2 = new SpannableString("0 cals");
                        mNewTitle2.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle2.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used2)), 0, mNewTitle2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        horizontal_beans m = new horizontal_beans();
                        if (mAdapter.getItem(position).getName().toString().equals("Water")) {
                            m = new horizontal_beans(mNewTitle, R.drawable.addwater, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel);
                        } else if (mAdapter.getItem(position).getName().toString().equals("Food")) {
                            m = new horizontal_beans(mNewTitle2, R.drawable.addfood, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel);
                        } else if (mAdapter.getItem(position).getName().toString().equals("Coffee")) {
                            m = new horizontal_beans(mNewTitle1, R.drawable.addcoffee, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel);
                        }
                        if (mhorizontal_food.size() > 0) {
                            int count1 = 0;

                            for (int i = 0; i < mhorizontal_food.size(); i++) {
                                Log.e("here", "1" + m.getTitle() + mhorizontal_food.get(i).getTitle() + mhorizontal_food.get(i).getTitle().toString().equalsIgnoreCase(m.getTitle().toString()));
                                if (!mhorizontal_food.get(i).getTitle().toString().equalsIgnoreCase(m.getTitle().toString())) {
                                    count1++;
                                }
                            }
                            if (count1 >= mhorizontal_food.size()) {
                                mhorizontal_food.add(m);
                                Log.e("here", "2" + m.getTitle());
                                int count = 0;
                                if (mMainList != null && mMainList.size() > 0) {
                                    for (int i = 0; i < mMainList.size(); i++) {
                                        int countj = 0;
                                        if (mMainList.get(i).getName().equals(name)) {

                                            mMainList.remove(i);
                                            mMains.setName(name);
                                            mMains.setAllItemsInSection(mhorizontal_food);
                                            mMainList.add(mMains);
                                            mAdapters.notifyDataSetChanged();
                                            mRecycler1.setAdapter(mAdapters);


                                        } else {
                                            count++;
                                            Log.e("count", count + "" + mMainList.size() + "size");
                                            if (count >= (mMainList.size())) {
                                                mMains.setName(name);
                                                mMains.setAllItemsInSection(mhorizontal_food);
                                                mMainList.add(mMains);
                                                mAdapters.notifyDataSetChanged();

                                                count = 0;
                                                break;

                                            }
                                        }

                                    }
                                } else {
                                    mMains.setName(name);
                                    mMains.setAllItemsInSection(mhorizontal_food);
                                    mMainList.add(mMains);
                                    mAdapters.notifyDataSetChanged();

                                }
                            }
                        } else {
                            if (mAdapter.getItem(position).getName().toString().equals("Water")) {
                                mhorizontal_food.add(new horizontal_beans(mNewTitle, R.drawable.addwater, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel));
                            } else if (mAdapter.getItem(position).getName().toString().equals("Food")) {
                                mhorizontal_food.add(new horizontal_beans(mNewTitle2, R.drawable.addfood, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel));
                            } else if (mAdapter.getItem(position).getName().toString().equals("Coffee")) {
                                mhorizontal_food.add(new horizontal_beans(mNewTitle1, R.drawable.addcoffee, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel));
                            }
                            Log.e("here", "3" + m.getTitle());
                            int count = 0;
                            if (mMainList != null && mMainList.size() > 0) {
                                for (int i = 0; i < mMainList.size(); i++) {
                                    int countj = 0;
                                    if (mMainList.get(i).getName().equals(name)) {

                                        mMainList.remove(i);
                                        mMains.setName(name);
                                        mMains.setAllItemsInSection(mhorizontal_food);
                                        mMainList.add(mMains);
                                        mAdapters.notifyDataSetChanged();
                                        mRecycler1.setAdapter(mAdapters);


                                    } else {
                                        count++;
                                        Log.e("count", count + "" + mMainList.size() + "size");
                                        if (count >= (mMainList.size())) {
                                            mMains.setName(name);
                                            mMains.setAllItemsInSection(mhorizontal_food);
                                            mMainList.add(mMains);
                                            mAdapters.notifyDataSetChanged();

                                            count = 0;
                                            break;

                                        }
                                    }

                                }
                            } else {
                                mMains.setName(name);
                                mMains.setAllItemsInSection(mhorizontal_food);
                                mMainList.add(mMains);
                                mAdapters.notifyDataSetChanged();

                            }
                        }
                        /*    int counti = 0;
                        for (int i = 0; i < mhorizontal_activity.size(); i++) {

                            if (mhorizontal_activity.equals(m)) {
                                continue;
                            } else {
                                counti++;
                                if (counti >= (mhorizontal_activity.size() - 1)) {
                                }
                            }
                        }*/

                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, bundle);
                        dismiss();


                    }
                });
            } else if (name.equalsIgnoreCase("Others")) {
                mAdapter = new Selector_Adapter(getActivity(), mOther);
                mRecycler.setAdapter(mAdapter);
                RecycleClick.addTo(mRecycler).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {


                        Intent bundle = new Intent();
                        bundle.putExtra("name", name);
                        bundle.putExtra("image", mAdapter.getItem(position).getThubmnail());
                        bundle.putExtra("title", mAdapter.getItem(position).getName());
                        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
                        SpannableString mNewTitle = new SpannableString("70 bpm ");
                        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used7
                        )), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        SpannableString mNewTitle1 = new SpannableString("70 kgs");
                        mNewTitle1.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle1.setSpan(new StyleSpan(Typeface.BOLD), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        mNewTitle1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.most_used6
                        )), 0, mNewTitle1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        horizontal_beans m = new horizontal_beans();
                        if (mAdapter.getItem(position).getName().toString().equals("HeartRate")) {
                            m = new horizontal_beans(mNewTitle, R.drawable.heartplay, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel);
                        } else {
                            m = new horizontal_beans(mNewTitle1, R.drawable.weightplay, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel);

                        }
                        if (mhorizontal_other.size() > 0) {
                            int count1 = 0;

                            for (int i = 0; i < mhorizontal_other.size(); i++) {
                                Log.e("here", "1" + m.getTitle() + mhorizontal_other.get(i).getTitle() + mhorizontal_other.get(i).getTitle().toString().equalsIgnoreCase(m.getTitle().toString()));
                                if (!mhorizontal_other.get(i).getTitle().toString().equalsIgnoreCase(m.getTitle().toString())) {
                                    count1++;
                                }
                            }
                            if (count1 >= mhorizontal_other.size()) {
                                mhorizontal_other.add(m);
                                Log.e("here", "2" + m.getTitle());
                                int count = 0;
                                if (mMainList != null && mMainList.size() > 0) {
                                    for (int i = 0; i < mMainList.size(); i++) {
                                        int countj = 0;
                                        if (mMainList.get(i).getName().equals(name)) {

                                            mMainList.remove(i);
                                            mMains.setName(name);
                                            mMains.setAllItemsInSection(mhorizontal_other);
                                            mMainList.add(mMains);
                                            mAdapters.notifyDataSetChanged();
                                            mRecycler1.setAdapter(mAdapters);


                                        } else {
                                            count++;
                                            Log.e("count", count + "" + mMainList.size() + "size");
                                            if (count >= (mMainList.size())) {
                                                mMains.setName(name);
                                                mMains.setAllItemsInSection(mhorizontal_other);
                                                mMainList.add(mMains);
                                                mAdapters.notifyDataSetChanged();

                                                count = 0;
                                                break;

                                            }
                                        }

                                    }
                                } else {
                                    mMains.setName(name);
                                    mMains.setAllItemsInSection(mhorizontal_other);
                                    mMainList.add(mMains);
                                    mAdapters.notifyDataSetChanged();

                                }
                            }
                        } else {
                            if (mAdapter.getItem(position).getName().toString().equals("HeartRate")) {
                                mhorizontal_other.add(new horizontal_beans(mNewTitle, R.drawable.heartplay, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel));
                            } else {
                                mhorizontal_other.add(new horizontal_beans(mNewTitle1, R.drawable.weightplay, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getThubmnail(),R.drawable.cancel));

                            }
                            Log.e("here", "3" + m.getTitle());
                            int count = 0;
                            if (mMainList != null && mMainList.size() > 0) {
                                for (int i = 0; i < mMainList.size(); i++) {
                                    int countj = 0;
                                    if (mMainList.get(i).getName().equals(name)) {

                                        mMainList.remove(i);
                                        mMains.setName(name);
                                        mMains.setAllItemsInSection(mhorizontal_other);
                                        mMainList.add(mMains);
                                        mAdapters.notifyDataSetChanged();
                                        mRecycler1.setAdapter(mAdapters);


                                    } else {
                                        count++;
                                        Log.e("count", count + "" + mMainList.size() + "size");
                                        if (count >= (mMainList.size())) {
                                            mMains.setName(name);
                                            mMains.setAllItemsInSection(mhorizontal_other);
                                            mMainList.add(mMains);
                                            mAdapters.notifyDataSetChanged();

                                            count = 0;
                                            break;

                                        }
                                    }

                                }
                            } else {
                                mMains.setName(name);
                                mMains.setAllItemsInSection(mhorizontal_other);
                                mMainList.add(mMains);
                                mAdapters.notifyDataSetChanged();

                            }
                        }
                        /*    int counti = 0;
                        for (int i = 0; i < mhorizontal_activity.size(); i++) {

                            if (mhorizontal_activity.equals(m)) {
                                continue;
                            } else {
                                counti++;
                                if (counti >= (mhorizontal_activity.size() - 1)) {
                                }
                            }
                        }*/

                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, bundle);
                        dismiss();


                    }
                });
            }

        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

        }

        public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
            private int space;

            public SpacesItemDecoration(int space) {
                this.space = space;
            }

            @Override
            public void getItemOffsets(Rect outRect, View view,
                                       RecyclerView parent, RecyclerView.State state) {
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;

                // Add top margin only for the first item to avoid double space between items
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = space;
                } else {
                    outRect.top = 0;
                }
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }


    }

//    @Override
//    public void onStop() {
//        Gson gson = new Gson();
//
//        //  ArrayList<ListAdapterItemsSelected> mSelectedList = new ArrayList<ListAdapterItemsSelected>();
//        String jsonStringa = gson.toJson(mMainList);
//        super.onStop();
//        mMainList.clear();
//        mhorizontal_food.clear();
//        mhorizontal_activity.clear();
//        mhorizontal_other.clear();
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("all", jsonStringa);
//        editor.commit();
//    }

    @Override
    public void onDetach() {
        Log.e("hey", "i was here you know");
//Set the values
        Gson gson = new Gson();

        //  ArrayList<ListAdapterItemsSelected> mSelectedList = new ArrayList<ListAdapterItemsSelected>();
        String jsonStringa = gson.toJson(mMainList);
//Save to SharedPreferences
        mMainList.clear();
        mhorizontal_food.clear();
        mhorizontal_activity.clear();
        mhorizontal_other.clear();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("all", jsonStringa);
        editor.commit();
        super.onDetach();

    }
}
