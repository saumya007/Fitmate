package com.saumyamehta.listkeeper2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.saumyamehta.listkeeper2.adapters.AppBucketDrops;
import com.saumyamehta.listkeeper2.adapters.CustomViewTarget;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;

public class Verification extends AppCompatActivity {
    private static final long SYNC_ID = 1;
    private static final long SYNC_SMS = 2;
    private Button mGoogleSignin, mSms;
    public static final int RC_SIGN_IN = 1;
    private FirebaseAuth.AuthStateListener mListener;
    private FirebaseAuth mAuth;
    private ImageView mProgress;
    private ChasingDots mChasing;
    private String SEQ_ID = "one";
    private ShowcaseView mShowcase;
    private EditText mEmail, mPass;
    private TextView mOR;
    private ImageView mLogo;
    private boolean phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_google_sign_in);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPass = (EditText) findViewById(R.id.et_pass);
        mOR = (TextView) findViewById(R.id.txt_or);
        mSms = (Button) findViewById(R.id.button_sms);
        mGoogleSignin = (Button) findViewById(R.id.button_google);
        mLogo = (ImageView) findViewById(R.id.iv_logo);
        AppBucketDrops.setRalewayThin(getApplicationContext(), mEmail, mGoogleSignin, mOR, mPass, mSms);
        AppBucketDrops.setRalewayThin(getApplicationContext(), mGoogleSignin);
        String fontPath = "fonts/Raleway-Thin.ttf";
        Typeface mTypeFace = Typeface.createFromAsset(getAssets(), fontPath);

        TextPaint titlePaint = new TextPaint();
        titlePaint.setTextSize(150);
        titlePaint.setColor(ContextCompat.getColor(getApplicationContext(), R.color.text_title));
        titlePaint.setAntiAlias(true);
        titlePaint.setTypeface(mTypeFace);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(55);
        textPaint.setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(mTypeFace);

        mShowcase = new ShowcaseView.Builder(this, true)
                .setTarget(new CustomViewTarget(R.id.button_google, 195, 50, this))
                .setContentTitle("Sync your items !")
                .setContentText("Tap the button to sync your acivities")
                .setContentTitlePaint(titlePaint)
                .setContentTextPaint(textPaint)
                .singleShot(SYNC_ID)
                .setStyle(com.saumyamehta.listkeeper2.R.style.CustomShowcaseTheme2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShowcase.hide();
                        String fontPath = "fonts/Raleway-Thin.ttf";
                        Typeface mTypeFace = Typeface.createFromAsset(getAssets(), fontPath);
                        TextPaint titlePaint = new TextPaint();
                        titlePaint.setTextSize(150);
                        titlePaint.setColor(ContextCompat.getColor(getApplicationContext(), R.color.text_title));
                        titlePaint.setAntiAlias(true);
                        titlePaint.setTypeface(mTypeFace);
                        TextPaint textPaint = new TextPaint();
                        textPaint.setTextSize(55);
                        textPaint.setColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
                        textPaint.setAntiAlias(true);
                        textPaint.setTypeface(mTypeFace);
                        mShowcase = new ShowcaseView.Builder(Verification.this, true)

                                .setTarget(new CustomViewTarget(R.id.button_sms, 175, 50, Verification.this))
                                .setContentTitle("Verify with phone !")
                                .setContentText("Click on the button to verify with your phone")
                                .setContentTitlePaint(titlePaint)
                                .setContentTextPaint(textPaint)
                                .singleShot(SYNC_SMS)
                                .setStyle(com.saumyamehta.listkeeper2.R.style.CustomShowcaseTheme2)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mShowcase.hide();
                                    }
                                })
                                .build();
                        final Animation fade = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.fade);
                        mShowcase.startAnimation(fade);
                    }
                })
                .build();
        final Animation fade = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade);
        mShowcase.startAnimation(fade);
        mGoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = false;
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("phone", phone);
                editor.commit();
                signIn();

            }
        });
        mSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = true;
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("phone", phone);
                editor.commit();
                Verify();
            }
        });
        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    final BitmapDrawable[] drawable = new BitmapDrawable[1];
                    Glide.with(getApplicationContext()).load(firebaseAuth.getCurrentUser().getPhotoUrl()).asBitmap().into(new SimpleTarget<Bitmap>(100, 100) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {


                        }
                    });
                    String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
                    String email = firebaseAuth.getCurrentUser().getEmail();
                    if (phone != null && !TextUtils.isEmpty(phone)) {
                        Toasty.success(Verification.this, "Welcome  to the app", Toast.LENGTH_SHORT).show();
                        mEmail.setVisibility(View.GONE);
                        mPass.setVisibility(View.GONE);

                    } else if (email != null) {
                        if (mEmail.getVisibility() == View.GONE || mPass.getVisibility() == View.GONE) {
                            mEmail.setVisibility(View.VISIBLE);
                            mPass.setVisibility(View.VISIBLE);
                        }


                        mEmail.setTag(mEmail.getKeyListener());
                        mEmail.setKeyListener(null);
                        mPass.setTag(mEmail.getKeyListener());
                        mPass.setKeyListener(null);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mPass.setBackground(ContextCompat.getDrawable(Verification.this, R.drawable.bg_et_drop));
                        } else {
                            mPass.setBackgroundDrawable(ContextCompat.getDrawable(Verification.this, R.drawable.bg_et_drop));

                        }
                        Toasty.success(Verification.this, "Welcome " + email, Toast.LENGTH_SHORT).show();

                    }
                    if(mEmail.getVisibility() == View.VISIBLE && mPass.getVisibility() == View.VISIBLE){
                    mEmail.setTranslationY(500);
                    mPass.setTranslationY(500);
                    mProgress.setTranslationY(500);

                        mEmail.setTag(mEmail.getKeyListener());
                        mEmail.setKeyListener(null);
                        mPass.setTag(mEmail.getKeyListener());
                        mPass.setKeyListener(null);

                    }
                    mLogo.setScaleX(2);
                    mLogo.setScaleY(2);
                    mLogo.setTranslationY(300);
                    new AsyncTaskActivity().execute();
                    mGoogleSignin.setVisibility(View.GONE);
                    mSms.setVisibility(View.GONE);
                    mOR.setVisibility(View.GONE);

                    //Toast.makeText(Verification.this, "Welcome " + firebaseAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                    mChasing.start();


                } else {

                }
            }
        };
        initBackgroundImage();
        mProgress = (ImageView) findViewById(R.id.progressBar1);
        mChasing = new ChasingDots();
        mChasing.setBounds(0, 0, 100, 100);
        mProgress.setImageDrawable(mChasing);

    }

    private void Verify() {
        showDialogSms();
    }

    private void signIn() {
        Log.e("Text", mEmail.getText().toString());
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mActiveInfo = connectivityManager.getActiveNetworkInfo();
        boolean connection = mActiveInfo != null && mActiveInfo.isConnected();
        if (connection) {
            if (mGoogleSignin.getText().toString().equals("Tap to Sign up")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mPass.setBackground(ContextCompat.getDrawable(Verification.this, R.drawable.bg_et_drop));
                } else {
                    mPass.setBackgroundDrawable(ContextCompat.getDrawable(Verification.this, R.drawable.bg_et_drop));

                }

                Log.e("Text", mEmail.getText().toString());
                try {
                    mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPass.getText().toString())
                            .addOnCompleteListener(Verification.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Log.e("Text", mEmail.getText().toString());
                                        // Sign in success, update UI with the signed-in user's information
                                        Toasty.success(Verification.this, "Success", Toast.LENGTH_SHORT).show();
                                        mGoogleSignin.setVisibility(View.GONE);
                                        mSms.setVisibility(View.GONE);
                                        mOR.setVisibility(View.GONE);
                                        mChasing = new ChasingDots();
                                        mChasing.setBounds(0, 0, 100, 100);
                                        mProgress.setImageDrawable(mChasing);
                                        mChasing.start();
                                        mLogo.setScaleX(2);
                                        mLogo.setScaleY(2);
                                        mLogo.setTranslationY(300);
                                        mEmail.setTranslationY(500);
                                        mPass.setTranslationY(500);
                                        mProgress.setTranslationY(500);
                                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("Email", mEmail.getText().toString());
                                        editor.putString("Password", mPass.getText().toString());
                                        editor.commit();
                                        mEmail.setTag(mEmail.getKeyListener());
                                        mEmail.setKeyListener(null);
                                        mPass.setTag(mEmail.getKeyListener());
                                        mPass.setKeyListener(null);
                                        new AsyncTaskActivity().execute();
                                    } else {

                                        mEmail.setKeyListener((KeyListener) mEmail.getTag());

                                        mPass.setKeyListener((KeyListener) mPass.getTag());
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            Toasty.warning(Verification.this, "User with this email already exists !", Toast.LENGTH_SHORT).show();
                                            mGoogleSignin.setText("Tap to Sign In");
                                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                            if (mPass.getText().toString().length() < 6) {
                                                Toasty.error(Verification.this, "Password should be of 6 characters or more",
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toasty.error(Verification.this, "Either Username or Password is incorrect",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        // If sign in fails, display a message to the user.
                                        Log.w("failed", "signInWithEmail:failure", task.getException());


                                    }
                                }
                            });
                } catch (IllegalArgumentException iae) {
                    mEmail.setKeyListener((KeyListener) mEmail.getTag());

                    mPass.setKeyListener((KeyListener) mPass.getTag());
                    if (mEmail.getText().toString().isEmpty() || mPass.getText().toString().isEmpty()) {
                        if (mEmail.getText().toString().isEmpty()) {
                            mEmail.setError("Enter a valid email address");
                            mEmail.setFocusable(true);
                            Toasty.error(Verification.this, "Cant have an empty email", Toast.LENGTH_SHORT).show();
                        } else if (mPass.getText().toString().isEmpty()) {
                            mEmail.setError("Enter a valid password");
                            mEmail.setFocusable(true);
                            Toasty.error(Verification.this, "Cant have an empty password", Toast.LENGTH_SHORT).show();

                        } else if (mEmail.getText().toString().isEmpty() && mPass.getText().toString().isEmpty()) {
                            Toasty.error(Verification.this, "Cant enter a empty email and password", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            } else {
                mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPass.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // Sign in success, update UI with the signed-in user's information
                                    Toasty.success(Verification.this, "Success", Toast.LENGTH_SHORT).show();
                                    mGoogleSignin.setVisibility(View.GONE);
                                    mSms.setVisibility(View.GONE);
                                    mOR.setVisibility(View.GONE);
                                    mChasing = new ChasingDots();
                                    mChasing.setBounds(0, 0, 100, 100);
                                    mProgress.setImageDrawable(mChasing);
                                    mChasing.start();
                                    mLogo.setScaleX(2);
                                    mLogo.setScaleY(2);
                                    mLogo.setTranslationY(300);
                                    mEmail.setTranslationY(500);
                                    mPass.setTranslationY(500);
                                    mProgress.setTranslationY(500);
                                    mEmail.setTag(mEmail.getKeyListener());
                                    mEmail.setKeyListener(null);
                                    mPass.setTag(mEmail.getKeyListener());
                                    mPass.setKeyListener(null);
                                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("Email", mEmail.getText().toString());
                                    editor.putString("Password", mPass.getText().toString());


                                    editor.commit();
                                    new AsyncTaskActivity().execute();


                                } else {
                                    mEmail.setKeyListener((KeyListener) mEmail.getTag());

                                    mPass.setKeyListener((KeyListener) mPass.getTag());
                                    // If sign in fails, display a message to the user.
                                    Log.w("failed", "signInWithEmail:failure", task.getException());
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                            mPass.setBackground(ContextCompat.getDrawable(Verification.this, R.drawable.bg_incorrect));
                                        } else {
                                            mPass.setBackgroundDrawable(ContextCompat.getDrawable(Verification.this, R.drawable.bg_incorrect));

                                        }
                                        Toasty.error(Verification.this, "Either Username or Password is incorrect",
                                                Toast.LENGTH_SHORT).show();
                                    } else if (task.getException() instanceof FirebaseAuthException) {
                                        Toasty.warning(Verification.this, "Please  sign up !", Toast.LENGTH_SHORT).show();
                                        mGoogleSignin.setText("Tap to Sign up");
                                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        if (mPass.getText().toString().length() < 6) {
                                            Toasty.error(Verification.this, "Password should be of 6 characters or more",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toasty.error(Verification.this, "Either Username or Password is incorrect",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toasty.error(Verification.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }

                                // ...
                            }
                        });
            }
//            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//            startActivityForResult(signInIntent, RC_SIGN_IN);
////            startActivityForResult(
////                    AuthUI.getInstance()
////                            .createSignInIntentBuilder()
////                            .setAvailableProviders(
////                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
////                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
////                            .setIsSmartLockEnabled(!BuildConfig.DEBUG)
////                            .build(), RC_SIGN_IN);


        } else {
            Toasty.error(this, "Internet connection required", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mListener);
        if (mEmail.getVisibility() == View.VISIBLE && mPass.getVisibility() == View.VISIBLE) {
            mEmail.setTag(mEmail.getKeyListener());
            mPass.setTag(mPass.getKeyListener());
            mEmail.setKeyListener((KeyListener) mEmail.getTag());
            Log.e("Tag", mEmail.getTag() + "");
            mPass.setKeyListener((KeyListener) mPass.getTag());
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Log.i("email", sp.getString("Email", ""));
            Log.i("pass", sp.getString("Password", ""));
            mEmail.setText(sp.getString("Email", ""));
            mPass.setText(sp.getString("Password", ""));
        }
    }

    private void initBackgroundImage() {
        final RelativeLayout imgBack = (RelativeLayout) findViewById(R.id.rels);

        Glide.with(this).load(R.drawable.background).asBitmap().into(new SimpleTarget<Bitmap>(100, 100) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                imgBack.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background));
            }
        });
    }

    private void showDialogSms() {
        Intent i = new Intent(Verification.this, Dialog_Sms_Verify.class);
        startActivity(i);
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            Log.e("haha", result.getStatus().getStatusCode() + "");
//            handleSignInResult(result);
//
//        }
//
//    }

//    private void handleSignInResult(GoogleSignInResult result) {
//        if (result.isSuccess()) {
//            Log.e("Success", "hahaha");
//            // Google Sign In was successful, authenticate with Firebase
//            mGoogleSignin.setVisibility(View.GONE);
//            mChasing = new ChasingDots();
//            mChasing.setBounds(0, 0, 100, 100);
//            mProgress.setImageDrawable(mChasing);
//            mChasing.start();
//
//            GoogleSignInAccount account = result.getSignInAccount();
//            firebaseAuthWithGoogle(account);
//
//        } else {
//            Log.e("Failed", "hahaha" + result.getStatus().getStatusCode());
//            // Google Sign In failed, update UI appropriately
//            // ...
//        }
//    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toasty.error(Verification.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                        // ...
//                    }
//                });
//    }

    private class AsyncTaskActivity extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Handler h = new Handler();

            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Verification.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
