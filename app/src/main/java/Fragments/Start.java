package Fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coins.black.main_menu.R;


public class Start extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_start, container, false);


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view.findViewById(R.id.layout), "alpha", 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.4f, 0.7f, 1.0f, 0.7f, 0.4f, 0.2f, 0.4f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator);
        animatorSet.setDuration(3000).start();

        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                // change the fragment
                Main_menu main_menu_fragment = new Main_menu();
                ChangeFragment(main_menu_fragment);
            }
        };

        handler.postDelayed(runnable, 3000);

        // Inflate the layout for this fragment
        return view;
    }


    public void ChangeFragment(Fragment fragment) {

        FragmentManager fragmentManager = this.getFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);

        transaction.replace(R.id.startFrame, fragment ).commit();
    }

}
