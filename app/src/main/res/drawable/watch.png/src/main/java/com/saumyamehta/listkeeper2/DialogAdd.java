package com.saumyamehta.listkeeper2;

import android.content.SharedPreferences;
import android.gesture.Prediction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.saumyamehta.listkeeper2.adapters.AppBucketDrops;
import com.saumyamehta.listkeeper2.beans.Drops;
import com.saumyamehta.listkeeper2.widgets.BucketPickerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Set;

import es.dmoral.toasty.Toasty;

//import io.realm.Realm;import io.realm.RealmConfiguration;


/**
 * Created by saumyamehta on 6/21/17.
 */

public class DialogAdd extends DialogFragment {
    private EditText mInputWhat;
    private ImageButton mBtnClose;
    private TextView title;
    private BucketPickerView mInputWhen;
    private Button mButtonAdd;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    private Set<String> keys = new ArraySet<>();
    private long now;

    public DialogAdd() {

    }

    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_add_it:
                    addAction();
                    if (!(mInputWhat.getText().toString().equals(" ") || mInputWhat.getText().toString().equals(null) || mInputWhat.getText().toString().isEmpty())) {
                        dismiss();
                    }
                    break;
                case R.id.btnClose:
                    dismiss();
                    break;
            }

        }
    };

    private void addAction() {
        String what = mInputWhat.getText().toString();
        now = System.currentTimeMillis();
        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
//        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(realmConfiguration);
//        Realm realm = Realm.getDefaultInstance();
        if (what.equals(null) || what.isEmpty() || what.equals("")) {
            Toasty.error(getActivity(), "Oops ! you haven't mentioned your goal yet add it again", Toast.LENGTH_SHORT).show();
        } else {
            final Drops drop = new Drops(what, now, mInputWhen.getTime(), false);
            if (mUser != null) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                SharedPreferences spp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (spp.getBoolean("phone",false)) {
                    String phone = mUser.getPhoneNumber();
                    Log.e("phone",phone);
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Drops").child(phone);
                    keys.add(mDatabase.push().getKey());
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putStringSet("keys", keys);
                    editor.commit();
                    Log.e("key", keys + "");
                    mDatabase.push().setValue(drop);

                } else {
                    String name = mUser.getEmail().replace(".", " ").split("@")[0];

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Drops").child(name);
                    keys.add(mDatabase.push().getKey());
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putStringSet("keys", keys);
                    editor.commit();
                    Log.e("key", keys + "");
                    mDatabase.push().setValue(drop);
                }

                Toasty.warning(getActivity(), mInputWhat.getText().toString() + " was added successfully", Toast.LENGTH_SHORT).show();
            }
        }
        ((MainActivity) getActivity()).onResume();
        Log.e("what", what);
        //        realm.copyToRealm(drop);
//        realm.commitTransaction();
//        realm.close();
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInputWhat = (EditText) view.findViewById(R.id.et_drop);
        title = (TextView) view.findViewById(R.id.title);
        mBtnClose = (ImageButton) view.findViewById(R.id.btnClose);
        mInputWhen = (BucketPickerView) view.findViewById(R.id.date_picker1);
        mButtonAdd = (Button) view.findViewById(R.id.btn_add_it);
        mBtnClose.setOnClickListener(mButtonListener);
        mButtonAdd.setOnClickListener(mButtonListener);
        AppBucketDrops.setRalewayThin(getActivity(), mInputWhat, mButtonAdd, title);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference().keepSynced(true);

    }
}
