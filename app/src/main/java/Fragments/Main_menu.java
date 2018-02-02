package Fragments;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.coins.black.main_menu.MainActivity;
import com.coins.black.main_menu.R;
import com.coins.black.main_menu.SelfProfile;
import com.coins.black.main_menu.Setting;
import com.coins.black.main_menu.Shop;
import com.coins.black.main_menu.Skills;


public class Main_menu extends Fragment {

    public static boolean flag = true;
    ObjectAnimator animator1;
    ObjectAnimator animator2;
    ObjectAnimator animator3;
    ObjectAnimator animator4;
    ObjectAnimator animator5;
    ObjectAnimator animator6;
    ObjectAnimator animator7;
    ObjectAnimator animator8;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;


    public Main_menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        button1 = (Button) view.findViewById(R.id.play_btn);
        button2 = (Button) view.findViewById(R.id.profile_btn);
        button3 = (Button) view.findViewById(R.id.skills_btn);
        button4 = (Button) view.findViewById(R.id.shop_btn);
        button5 = (Button) view.findViewById(R.id.setting_btn);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelfProfile.class);
                startActivity(intent);
            }
        });



        button3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getActivity(),Skills.class);
                 startActivity(intent);
             }
         });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Shop.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Setting.class);
                startActivity(intent);
            }
        });





        // get the width of screen
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        //animator1 = ObjectAnimator.ofFloat(button1, "translationX", width*(0.04f));
        animator2 = ObjectAnimator.ofFloat(button2, "translationX", width*(0.1f));
        animator3 = ObjectAnimator.ofFloat(button3, "translationX", width*(0.2f));
        animator4 = ObjectAnimator.ofFloat(button4, "translationX", width*(0.3f));
        animator5 = ObjectAnimator.ofFloat(button5, "translationX", width*(0.4f));

        final AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.play(animator2).with(animator3).with(animator4).with(animator5);

        animatorSet.setDuration(1000);

        new Handler().postDelayed(new Runnable() {

            public void run() {

                animatorSet.start();
            }
        }, 500);

        final ImageView star1 = (ImageView) view.findViewById(R.id.star1);
        final ImageView star2 = (ImageView) view.findViewById(R.id.star2);
        final ImageView star3 = (ImageView) view.findViewById(R.id.star3);

        final Handler handler1 = new Handler();

        handler1.postDelayed(new Runnable() {
            public void run() {
                animator6 = ObjectAnimator.ofFloat(star1, "alpha", new float[]{1.0f, 0.0f, 1.0f});
                animator7 = ObjectAnimator.ofFloat(star2, "alpha", new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f});
                animator8 = ObjectAnimator.ofFloat(star3, "alpha", new float[]{1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f});
                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.play(animator6).with(animator7).with(animator8);
                animatorSet1.setDuration(3000).start();
                handler1.postDelayed(this, 3000);
            }
        }, 1000);

        /*MainActivity.ButtonClick(button1);
        MainActivity.ButtonClick(button2);
        MainActivity.ButtonClick(button3);
        MainActivity.ButtonClick(button4);*/

        return view;
    }

}
