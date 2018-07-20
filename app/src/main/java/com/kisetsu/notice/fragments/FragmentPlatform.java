package com.kisetsu.notice.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kisetsu.notice.R;
import com.kisetsu.notice.adapters.PagerAdapterPlatform;

//Created by Administrator on 2018/3/25.

public class FragmentPlatform extends Fragment {

    public static FragmentPlatform newInstance() {
        final FragmentPlatform fragment = new FragmentPlatform();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_platform,container,false);
        ViewPager viewPager = rootView.findViewById(R.id.viewpager_platform);
        TabLayout tabLayout = rootView.findViewById(R.id.tabs_platform);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.chocolate));
        PagerAdapterPlatform adapterPlatform = new PagerAdapterPlatform(getFragmentManager(), getResources());
        viewPager.setAdapter(adapterPlatform);
        tabLayout.setupWithViewPager(viewPager,true);
        return rootView;
    }
}
