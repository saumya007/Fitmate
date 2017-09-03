package com.saumya.fitmate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.daasuu.ei.EasingInterpolator;
import com.github.ybq.android.spinkit.animation.interpolator.Ease;

import com.saumya.fitmate.adapters.AppBucketDrops;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifDrawable;

public class NicknameActivity extends AppCompatActivity {
private TextView mInfoWhat;
    private EditText mInput;
    private ImageView mCortana,mEye1,mEye2;
    private ImageButton mContinue;
    private ViewFlipper mFlipper,mFlipper1;

    int count  =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);
        mInfoWhat = (TextView)findViewById(R.id.info_what);
        mInput = (EditText)findViewById(R.id.et_nickname);
        mContinue = (ImageButton)findViewById(R.id.continue_btn);
        mCortana = (ImageView)findViewById(R.id.animation_view);
//        mEye1 = (ImageView)findViewById(R.id.eye1);
//        mEye2 = (ImageView)findViewById(R.id.eye2);

//        final ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(mCortana,
//                PropertyValuesHolder.ofFloat("scaleY", 1.2f),PropertyValuesHolder.ofFloat("scaleX", 1.2f));
//        scaleDown.setDuration(1000);
//
//        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
//        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
//
//        scaleDown.start();
//        scaleDown.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
//                ((Animatable)mCortana.getDrawable()).start();
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//
//            }
//        });
        final AnimatorSet set = new AnimatorSet();
        try {
            GifDrawable gifFromAssets = new GifDrawable( getAssets(), "Untitled2.gif" );
            gifFromAssets.start();
            mCortana.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        final ObjectAnimator animator = ObjectAnimator.ofFloat(mCortana, "translationY", 0, 25, 0);
//        animator.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//
//        animator.setDuration(800);
//        animator.setRepeatCount(ValueAnimator.INFINITE);


//        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(mEye1, "scaleY", 1, 0.75f);
//        animator1.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//
//        animator1.setDuration(800);
//        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(mEye1, "translationX", 0,25);
//        animator2.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//
//        animator2.setDuration(800);
//
//        final ObjectAnimator animator12 = ObjectAnimator.ofFloat(mEye1, "scaleY", 0.75f, 1);
//        animator12.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//
//        animator12.setDuration(800);
//
//        final ObjectAnimator animator22 = ObjectAnimator.ofFloat(mEye2, "translationX", 0,20);
//        animator22.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//
//        animator22.setDuration(800);
//        final ObjectAnimator animator13 = ObjectAnimator.ofFloat(mEye2, "scaleY", 1, 0.75f);
//        animator13.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//
//        animator13.setDuration(800);
//        final ObjectAnimator animator23 = ObjectAnimator.ofFloat(mEye1, "translationX", 25,-20);
//        animator23.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//
//        animator23.setDuration(800);
//        final ObjectAnimator animator14 = ObjectAnimator.ofFloat(mEye2, "scaleY", 0.75f, 1);
//        animator14.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//
//        animator14.setDuration(800);
//        final ObjectAnimator animator24 = ObjectAnimator.ofFloat(mEye2, "translationX", 20,-20);
//        animator24.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//
//        animator24.setDuration(800);
//        final ObjectAnimator animator20 = ObjectAnimator.ofFloat(mEye2, "scaleY", 1, 0);
//        animator20.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//        animator20.setRepeatCount(1);
//        animator20.setDuration(1000);
//        final ObjectAnimator animator21 = ObjectAnimator.ofFloat(mEye2, "scaleY", 0, 1);
//        animator21.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//        animator21.setRepeatCount(1);
//        animator21.setDuration(1000);
//        final ObjectAnimator animator22 = ObjectAnimator.ofFloat(mEye1, "scaleY", 0.6f, 1);
//        animator22.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
//        animator22.setRepeatCount(ValueAnimator.INFINITE);
//        animator22.setDuration(1000);
//
////        final ObjectAnimator animator13 = ObjectAnimator.ofFloat(mEye1, "scaleY", 1,0.75f);
////        animator11.setInterpolator(new EasingInterpolator(com.daasuu.ei.Ease.ELASTIC_IN_OUT));
////        animator11.setStartDelay(1300);
////        animator11.setDuration(800);
////        animator11.setRepeatCount(0);
////        animator11.start();
////        List<Animator> as = new ArrayList<>();
////        as.add(animator);
////        as.add(animator1);
////        as.add(animator12);
////        as.add(animator13);
////        as.add(animator14);
////        as.add(animator15);
////        as.add(animator16);
////        set.playSequentially(as)
//////        ;
//        set.play(animator1).after(500);
//        set.play(animator2).with(animator1).after(500);
//        set.play(animator22).with(animator2).after(500);
//        set.play(animator12).after(animator22).after(1300);
//        set.play(animator13).with(animator12).after(1300);
//        set.play(animator23).with(animator13).after(2100);
//        set.play(animator24).with(animator23).after(2100);
//        set.play(animator14).after(animator24).after(2100);
////        set.play(animator20).after(animator19).after(5000);
////        set.play(animator21).with(animator20).after(5000);
////        set.play(animator22).with(animator21).after(5000);
//        set.start();
//
//        set.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
//                animation.start();
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//            }
//        });


//        final ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(mCortana,
//                PropertyValuesHolder.ofFloat("scaleY", 1.2f),
//                PropertyValuesHolder.ofFloat("scaleX", 1.2f));
//        scaleDown.setDuration(310);
//
//        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
//        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
//
//        scaleDown.start();
//        final ObjectAnimator rotate = ObjectAnimator.ofPropertyValuesHolder(mCortana,
//                PropertyValuesHolder.ofFloat("rotation",0,360));
//        rotate.setDuration(10);
//
//        rotate.setRepeatCount(ObjectAnimator.INFINITE);
//        rotate.setRepeatMode(ObjectAnimator.REVERSE);
//
//        rotate.start();


//        mCortana.setPivotY(mCortana.getHeight());
//        mCortana.setPivotX(mCortana.getWidth());
//        final ObjectAnimator rotation = ObjectAnimator.ofFloat(mCortana,"rotation",360);
//        rotation.setInterpolator(new AccelerateDecelerateInterpolator());
//        rotation.setRepeatCount(0);
//        rotation.setDuration(1000);
//        rotation.start();
//        rotation.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                ((Animatable)mCortana.getDrawable()).start();
//            }
//        });

//        final ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(mCortana,
//                PropertyValuesHolder.ofFloat("scaleY", 1.2f),PropertyValuesHolder.ofFloat("scaleX", 1.2f));
//
//        scaleDown.setDuration(500);
//
//        scaleDown.setRepeatCount(1);
//
//
//        scaleDown.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
//                //((Animatable)mCortana.getDrawable()).start();
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//
//            }
//        });



//        ObjectAnimator alphaa = ObjectAnimator.ofPropertyValuesHolder(mCortana,
//                PropertyValuesHolder.ofFloat("alpha", 1.0f));
//        alphaa.setRepeatCount(ObjectAnimator.INFINITE);
//        alphaa.setDuration(2400);
//        alphaa.start();
//        alphaa.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
////                super.onAnimationRepeat(animation);
////                final RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.57f,  Animation.RELATIVE_TO_SELF, 0.57f);
////                rotate.setDuration(1300);
////                rotate.setInterpolator(new AccelerateDecelerateInterpolator());
////                rotate.setRepeatCount(0);
////                mCortana.startAnimation(rotate);
////
////                rotate.setAnimationListener(new Animation.AnimationListener() {
////                    @Override
////                    public void onAnimationStart(Animation animation) {
////                        ((Animatable)mCortana.getDrawable()).start();
////                    }
////
////                    @Override
////                    public void onAnimationEnd(Animation animation) {
////                        //scaleDown.start();
////                    }
////
////                    @Override
////                    public void onAnimationRepeat(Animation animation) {
////
////                    }
////                });
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
////                final RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.57f,  Animation.RELATIVE_TO_SELF, 0.57f);
////                rotate.setDuration(1300);
////                rotate.setInterpolator(new AccelerateDecelerateInterpolator());
////                rotate.setRepeatCount(0);
////                mCortana.startAnimation(rotate);
////
////                rotate.setAnimationListener(new Animation.AnimationListener() {
////                    @Override
////                    public void onAnimationStart(Animation animation) {
////
////                    }
////
////                    @Override
////                    public void onAnimationEnd(Animation animation) {
////                        //scaleDown.start();
////                    }
////
////                    @Override
////                    public void onAnimationRepeat(Animation animation) {
////
////                    }
////                });
//            }
//        });

//        scaleDown.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
////                Integer r = new Random().nextInt(4);
////                Log.e("Random",r+"");
////                switch(r)
////                {
////                    case 0:
////                        mCortana.setColorFilter(Color.RED);
////                        break;
////                    case 1:
////                        mCortana.setColorFilter(Color.GREEN);
////                        break;
////                    case 2:
////                        mCortana.setColorFilter(Color.BLUE);
////                        break;
////                    case 3:
////                        mCortana.setColorFilter(Color.YELLOW);
////                        break;
////                }
//
//            }
//        });
//        final ObjectAnimator animators = ObjectAnimator.ofFloat(mCortana, "alpha",1,0.5f);
//        animators.setStartDelay(200);
//        animators.setDuration(1500);
//        animators.setRepeatCount(ValueAnimator.INFINITE);
//        animators.start();
//        animators.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                super.onAnimationCancel(animation);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//                super.onAnimationRepeat(animation);
//                if(!set.isRunning())
//                {
//                    set.start();
//                }
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//            }
//        });
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flip2);
////        mEye1.setAnimation(animation);
////        mEye2.setAnimation(animation);
//        animation.start();
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//                set.cancel();
//                animators.cancel();
//                scaleDown.cancel();
////                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flip1);
////
////                animation1.start();
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//Set the schedule function and rate
//        mFlipper = (ViewFlipper)findViewById(R.id.flipper);
//        mFlipper.setInAnimation(animation);
//        mFlipper1 = (ViewFlipper)findViewById(R.id.flipper1);
//        mFlipper1.setInAnimation(animation);
        mInfoWhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flip1);

                animation1.start();
            }
        });
//        mCortana.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!set.isRunning())
//                {
//                    set.start();
//                }
//            }
//        });
    }
}
