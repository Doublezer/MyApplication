package com.example.yday_19_02;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicListFragment extends Fragment {


    public MusicListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_music_list, container, false);
        ViewPager viewPager= (ViewPager) view.findViewById(R.id.frg_vp);
        ViewAdapter viewAdapter=new ViewAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewAdapter);
        TabLayout tabLayout= (TabLayout) view.findViewById(R.id.frg_tablayout);
        tabLayout.setupWithViewPager(viewPager,true);
        return view;
    }
    class ViewAdapter extends FragmentPagerAdapter{

        public ViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ChildFragment_01();
                case 1:
                    return new ChildFragment_02();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position==0?"MusicList":"Person";
        }
    }
}
