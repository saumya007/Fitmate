package com.saumya.fitmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

/**
 * Created by saumyamehta on 7/26/17.
 */

public class Dialog_Otp extends AppCompatActivity {
    private static final String TAG ="1" ;
    private EditText v1;
    private TextView mCount,mInfo;
    private ImageButton continuebtn, edit;
    private Button resend;
    private FirebaseAuth mAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    private String mVerificationId, phone;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthCredential cred;
    private CountDownTimer ctd;
    private long time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.otp_screen);
        mAuth = FirebaseAuth.getInstance();
        v1 = (EditText) findViewById(R.id.v1);
        mCount = (TextView) findViewById(R.id.time);
        mInfo = (TextView)findViewById(R.id.info_txt);
        continuebtn = (ImageButton) findViewById(R.id.continue_btn);
        edit = (ImageButton) findViewById(R.id.editbtn);
        resend = (Button) findViewById(R.id.resend);
        edit.setVisibility(View.GONE);
        resend.setVisibility(View.GONE);
        AppBucketDrops.setRalewayThin(getApplicationContext(),mCount,resend,v1,mInfo);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("mills");
                editor.commit();
                Log.e("millis",sp.getLong("mills",60000)+"");
                Intent i = new Intent(Dialog_Otp.this, Dialog_Sms_Verify.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        if (i != null) {
            if(savedInstanceState == null)
            {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("mills");
                editor.commit();
            }
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final String code = sp.getString("Code", "123456");
            final String verificationId = i.getStringExtra("mVerificationId");
            phone = i.getStringExtra("mPhone");
            mResendToken = i.getParcelableExtra("mResendToken");
            continuebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = v1.getText().toString();
                    Log.e("code", code);
                    Log.e("ver", verificationId);
                    if (TextUtils.isEmpty(v1.getText()) || code.isEmpty()) {
                        Toasty.error(getApplicationContext(), "Please enter a code to continue", Toast.LENGTH_SHORT).show();
                        v1.setError("Cannot accept an empty value");
                    } else {
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove("mills");
                        editor.commit();
                        verifyPhoneNumberWithCode(verificationId, code);
                    }
                }
            });
                ctd = new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {


                        Log.e("counterrrr", "counrrere");
                        if ((millisUntilFinished / 1000) >= 10) {
                            mCount.setText("00" + ":" + millisUntilFinished / 1000);
                        } else {
                            mCount.setText("00" + ":" + "0" + millisUntilFinished / 1000);

                        }

                        SharedPreferences spp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = spp.edit();
                        editor.putLong("mills", millisUntilFinished);
                        editor.commit();
                        time = millisUntilFinished;
                        Log.e("times", time + "");
                    }

                    @Override
                    public void onFinish() {
                        mCount.setText("00" + ":" + "00");
                        edit.setVisibility(View.VISIBLE);
                        resend.setVisibility(View.VISIBLE);
                    }
                };
                ctd.start();

        }
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Resend", mResendToken + "");
                final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                ctd = new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if ((millisUntilFinished / 1000) >= 10) {
                            mCount.setText("00" + ":" + millisUntilFinished / 1000);
                        } else {
                            mCount.setText("00" + ":" + "0" + millisUntilFinished / 1000);
                        }
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putLong("mills", millisUntilFinished);
                        editor.commit();
                    }

                    @Override
                    public void onFinish() {
                        mCount.setText("00" + ":" + "00");
                        edit.setVisibility(View.VISIBLE);
                        resend.setVisibility(View.VISIBLE);
                    }
                };
                ctd.start();
                resendVerificationCode(phone, mResendToken);

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]

                mVerificationInProgress = false;

                cred = credential;
                // signInWithPhoneAuthCredential(credential);
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
                super.onCodeSent(verificationId, token);
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                mVerificationId = verificationId;

                mResendToken = token;

                Log.d(TAG, "onCodeSent:" + verificationId);
                Toasty.success(Dialog_Otp.this, "The Verification Code has been sent successfully", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putLong("times",time);
        super.onSaveInstanceState(outState, outPersistentState);

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            finish();
                            // [START_EXCLUDE]
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("failde", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                Toasty.error(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                                // [END_EXCLUDE]
                            }

                        }
                    }
                });
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
        mVerificationInProgress = true;
    }
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ctd.cancel();
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //if (sp != null) {
            long time = sp.getLong("mills", 60000);
            Log.e("times", time + "");
            ctd = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if ((millisUntilFinished / 1000) >= 10) {
                        mCount.setText("00" + ":" + millisUntilFinished / 1000);
                    } else {
                        mCount.setText("00" + ":" + "0" + millisUntilFinished / 1000);
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putLong("mills", millisUntilFinished);
                    editor.commit();
                }

                @Override
                public void onFinish() {
                    mCount.setText("00" + ":" + "00");
                    edit.setVisibility(View.VISIBLE);
                    resend.setVisibility(View.VISIBLE);
                }
            };
            ctd.start();
        }
    //}
}
