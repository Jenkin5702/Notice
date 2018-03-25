package com.kisetsu.notice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kisetsu.notice.adapters.NavigationPagerAdapter;
import com.kisetsu.notice.utilities.RoundedBitmapDrawable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private static final String SELECTED_POSITION = "selectedPosition";

    private int mCurrentNavPosition;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ToolType[] mToolTypes = ToolType.values();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Enable opening of drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Add drawer listeners
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // Set up tabs and title
        if (savedInstanceState == null) {
        }
        setBottomNavigation(mCurrentNavPosition);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.circles:
                mCurrentNavPosition = 0;
                break;
            case R.id.platforms:
                mCurrentNavPosition = 1;
                break;
            case R.id.squares:
                mCurrentNavPosition = 2;
                break;
            case R.id.notifications:
                mCurrentNavPosition = 3;
                break;
            case R.id.setting:
                mCurrentNavPosition = 4;
                break;
            default:
                Log.w(TAG, "Unknown drawer item selected");
                break;
        }

        menuItem.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentNavPosition = savedInstanceState.getInt(SELECTED_POSITION, 0);
        final Menu menu = mNavigationView.getMenu();
        final MenuItem menuItem = menu.getItem(mCurrentNavPosition);
        menuItem.setChecked(true);
        setBottomNavigation(mCurrentNavPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_POSITION, mCurrentNavPosition);
    }

    public void setBottomNavigation(int position){
        final ViewPager viewPager = findViewById(R.id.viewpager);
        final BottomNavigationView navigationView=findViewById(R.id.navigation);
        final NavigationPagerAdapter adapter=
                new NavigationPagerAdapter(getSupportFragmentManager(),getResources());
        viewPager.setAdapter(adapter);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_circle:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_platform:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_notification:
                        viewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });
    }

//    private void setupTabs(int position) {
//        final ViewPager viewPager = findViewById(R.id.viewpager);
//        final TabLayout tabLayout = findViewById(R.id.tabs);
//        final ToolPagerAdapter toolPagerAdapter = new ToolPagerAdapter(getSupportFragmentManager(), getResources(), mToolTypes[position]);
//        tabLayout.removeAllTabs();
//        tabLayout.setTabsFromPagerAdapter(toolPagerAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        viewPager.setAdapter(toolPagerAdapter);
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }
}
