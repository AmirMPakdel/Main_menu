package com.coins.black.main_menu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import Fragments.AddictedFragment;
import Fragments.BullyFragment;
import Fragments.DetectiveFragment;
import Fragments.DoctorFragment;
import Fragments.GodfatherFragment;
import Fragments.GuerrillaFragment;
import Fragments.MafiaFragment;
import Fragments.MurdererFragment;
import Fragments.PoliceFragment;
import Fragments.SalesGunsFragment;
import Fragments.SniperFragment;
import Fragments.SpyFragment;
import Fragments.TerroristFragment;
import Fragments.ThiefFragment;

import static com.coins.black.main_menu.MainActivity.log;

public class Skills extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        // Making Activity Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        log("full screened");
        
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_police, container, false);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    PoliceFragment police = new PoliceFragment();
                    return police;
                case 1:
                    AddictedFragment addicted = new AddictedFragment();
                    return addicted;
                case 2:
                    BullyFragment bully = new BullyFragment();
                    return bully;
                case 3:
                    DetectiveFragment detective = new DetectiveFragment();
                    return detective;
                case 4:
                    DoctorFragment doctor = new DoctorFragment();
                    return doctor;
                case 5:
                    GodfatherFragment godfather = new GodfatherFragment();
                    return godfather;
                case 6:
                    GuerrillaFragment guerrilla = new GuerrillaFragment();
                    return guerrilla;
                case 7:
                    MafiaFragment mafia = new MafiaFragment();
                    return mafia;
                case 8:
                    MurdererFragment murderer = new MurdererFragment();
                    return murderer;
                case 9:
                    SalesGunsFragment salesGuns = new SalesGunsFragment();
                    return salesGuns;
                case 10:
                    SniperFragment sniper = new SniperFragment();
                    return sniper;
                case 11:
                    SpyFragment spy = new SpyFragment();
                    return spy;
                case 12:
                    TerroristFragment terrorist = new TerroristFragment();
                    return terrorist;
                case 13:
                    ThiefFragment thief = new ThiefFragment();
                    return thief;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show count total pages.
            return 14;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";

            }
            return null;
        }
    }
}
