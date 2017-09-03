package com.saumya.fitmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.saumya.fitmate.adapters.AppBucketDrops;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

/**
 * Created by saumyamehta on 7/23/17.
 */

public class Dialog_Sms_Verify extends AppCompatActivity {
    private static final String TAG ="1" ;
    private EditText mCountry, mPhone;
    private TextView info;
    private ImageButton mContinue;
    private android.support.v7.widget.AppCompatSpinner mSpinner;
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private PhoneAuthCredential cred;


    private View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.continue_btn:
                    proceed();

                    break;

            }
            if ((mCountry.getText().toString().equals(" ") || mCountry.getText().toString().equals(null) || mCountry.getText().toString().isEmpty()) || (mPhone.getText().toString().equals(" ") || mPhone.getText().toString().equals(null) || mPhone.getText().toString().isEmpty())) {
                Toasty.error(Dialog_Sms_Verify.this, "Please provide a number  to continue! ", Toast.LENGTH_SHORT).show();
            } else {
            }
        }
    };

    private void proceed() {
        if (!validatePhoneNumber()) {
            return;
        }

        startPhoneNumberVerification("+" + mCountry.getText().toString() + mPhone.getText().toString());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification("+" + mCountry.getText().toString() + mPhone.getText().toString());
        }
        // [END_EXCLUDE]
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhone.setError("Please enter a valid phone number");
        }
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("mPhone", mPhone.getText().toString());
            editor.commit();
        return true;
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }
//    private void resendVerificationCode(String phoneNumber,
//                                        PhoneAuthProvider.ForceResendingToken token) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                getActivity(),               // Activity (for callback binding)
//                mCallbacks,         // OnVerificationStateChangedCallbacks
//                mResendToken);             // ForceResendingToken from callbacks
//    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            cred = credential;
                            finish();
                            // [START_EXCLUDE]
                            // updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI

                            // [END_EXCLUDE]
                        }
                    }
                });
    }


    private void signOut() {
        mAuth.signOut();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sms);
        final ArrayList<String> countrycodes = new ArrayList<>();
        final String[] countryAreaCodes = getResources().getStringArray(R.array.countryCodes);
        final ArrayList<String> mDial = new ArrayList<>();
        final ArrayList<String> countries = new ArrayList<>();
        final ArrayList<String> countriesnames = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mCountry = (EditText) findViewById(R.id.v1);
        mPhone = (EditText) findViewById(R.id.v2);
        mContinue = (ImageButton) findViewById(R.id.continue_btn);
        mContinue.setOnClickListener(mButtonListener);
        info = (TextView)findViewById(R.id.info_txt);
        mCountry.setKeyListener(null);
        mSpinner = (android.support.v7.widget.AppCompatSpinner) findViewById(R.id.spin);
        AppBucketDrops.setRalewayThin(Dialog_Sms_Verify.this, mCountry, mPhone,info);
        Locale[] locale = Locale.getAvailableLocales();

        countrycodes.clear();
        mDial.clear();
        countries.clear();
        countriesnames.clear();
//        for (int i=0;i<countryAreaCodes.length;i++) {
//            if(!countrycodes.contains(loc.getCountry())) {
//                countrycodes.add(loc.getCountry());
//            }
//        }
        for (int i = 0; i < countryAreaCodes.length; i++) {

            mDial.add(countryAreaCodes[i].split(",")[0]);
            if (countries.size() <= countryAreaCodes.length) {
                countries.add(countryAreaCodes[i].split(",")[1]);
            }
            Log.i("coll", countryAreaCodes[i].split(",")[1] + "");
        }
        for (int i = 0; i < countries.size(); i++) {

            Locale loc = new Locale("", countries.get(i));
            if (countriesnames.size() <= countries.size()) {
                countriesnames.add(loc.getDisplayCountry());
            }
        }
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        Log.i("coll", "" + tm.getSimCountryIso().toUpperCase() + "");
        final ArrayAdapter<String> mCountries = new ArrayAdapter<String>(Dialog_Sms_Verify.this, R.layout.spinner_layout, countriesnames);
        mSpinner.setAdapter(mCountries);
        for (int i = 0; i < countryAreaCodes.length; i++) {
            Log.e("countryyy:", countryAreaCodes[i].split(",")[1] + tm.getSimCountryIso().toUpperCase().trim() + countryAreaCodes[i].split(",")[1].trim().toString().equals(tm.getSimCountryIso().toUpperCase().trim().toString()));
            if (countryAreaCodes[i].split(",")[1].trim().equals(tm.getSimCountryIso().toUpperCase().trim())) {
                mSpinner.setSelection(i);
                mSpinner.setPrompt(countriesnames.get(i));
                mCountry.setText(countryAreaCodes[i].split(",")[0]);
                Log.e("passes", "hahah");
                break;
            } else {
                Log.e("fails", "hahah");
            }
        }

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < countries.size(); i++) {
                    if (countriesnames.get(position).equals(parent.getItemAtPosition(position).toString())) {//Log.e("Prompt",loc.getDisplayCountry()+"  "+ position+" pos " +countryAreaCodes[position]+"ha "+ mCountries.getItem(position));

                        mCountry.setText(countryAreaCodes[position].split(",")[0]);
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("mSpinner", position);
                        editor.putString("mCountry", countryAreaCodes[position].split(",")[0]);
                        editor.commit();
                    }
                }
                Log.e("Prompt", parent.getItemAtPosition(position).toString() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                updateui(credential);
                mVerificationInProgress = false;

                cred = credential;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential

                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                //               resendVerificationCode(mPhone.getText().toString(),mResendToken);
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]

                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]

                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                //super.onCodeSent(verificationId, token);
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                mVerificationId = verificationId;

                mResendToken = token;

                Log.d(TAG, "onCodeSent:" + verificationId + "token" + token);
                Toasty.success(Dialog_Sms_Verify.this, "The Verification Code has been sent successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Dialog_Sms_Verify.this, Dialog_Otp.class);

                i.putExtra("mVerificationId", verificationId);

                i.putExtra("mPhone", mPhone.getText().toString());
                i.putExtra("mResendToken", mResendToken);

                startActivity(i);
                finish();

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };

    }

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        outState = new Bundle();
//        outState.putString("mPhone",mPhone.getText().toString());
//    }

    @Override
    protected void onResume() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sp != null) {
            if (TextUtils.isEmpty(mPhone.getText().toString())) {
                mPhone.setText(sp.getString("mPhone", ""));
            }
        }
//        if(mAuth.getCurrentUser()!=null) {
//            if (mAuth.getCurrentUser().getPhoneNumber() != null) {
//                mPhone.setText(mAuth.getCurrentUser().getPhoneNumber().substring(3, 12));
//            }
//        }
        super.onResume();

    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mPhone.setText(savedInstanceState.getString("mPhone"));
//    }

    private void updateui(PhoneAuthCredential credential) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //  credential = PhoneAuthProvider.getCredential(mVerificationId, credential.getSmsCode());

        SharedPreferences.Editor editor = sp.edit();
        Log.e("cres", credential.getSmsCode() + "gh");
        if (credential != null) {
            if (credential.getSmsCode() != null) {

                editor.putString("Code", credential.getSmsCode());

            } else {
                editor.putString("Code", getString(R.string.instant_validation));
            }
        }
        editor.commit();

    }

    @Override
    public void onBackPressed() {

    }
}
