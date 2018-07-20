package com.kisetsu.notice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.kisetsu.notice.activities.ActivityCircle;
import com.kisetsu.notice.activities.ActivityHelpPublish;
import com.kisetsu.notice.activities.ActivityLogin;
import com.kisetsu.notice.activities.ActivityTeamPublish;
import com.kisetsu.notice.drawables.FadedImageDrawable;
import com.kisetsu.notice.fragments.FragmentCircle;
import com.kisetsu.notice.fragments.FragmentNotification;
import com.kisetsu.notice.fragments.FragmentPlatform;
import com.kisetsu.notice.fragments.FragmentSquare;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private static final String SELECTED_POSITION = "selectedPosition";

    private int mCurrentNavPosition;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private BottomNavigationView bottomNavigationView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar=findViewById(R.id.toolbar);

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

        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.pickWheelBarrow_blue));

//        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.header);
//        FadedImageDrawable fadedImage=new FadedImageDrawable(bitmap);
//        View headerView=mNavigationView.getHeaderView(0);
//        ImageView headerImage=headerView.findViewById(R.id.header);
//        headerImage.setImageDrawable(getResources().getDrawable(R.drawable.header));

        bottomNavigationView =findViewById(R.id.navigation);
        // Set up tabs and title
        setBottomNavigation(mCurrentNavPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 动态设置ToolBar状态
        menu.findItem(R.id.publish_team).setVisible(true);
        menu.findItem(R.id.publish_help).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.publish_team:
                startActivity(new Intent(MainActivity.this, ActivityTeamPublish.class));
                break;
            case R.id.publish_help:
                startActivity(new Intent(MainActivity.this, ActivityHelpPublish.class));
                break;
            case R.id.login_register:
                startActivity(new Intent(MainActivity.this, ActivityLogin.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                mCurrentNavPosition = 0;
                break;
            case R.id.schedule:
                startActivity(new Intent(MainActivity.this, ActivityCircle.class));
                break;
            case R.id.setting:
                mCurrentNavPosition = 2;
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
        final FragmentSquare fragmentSquare=FragmentSquare.newInstance();
        final FragmentPlatform fragmentPlatform=FragmentPlatform.newInstance();
        final FragmentNotification fragmentNotification=FragmentNotification.newInstance();
        final FragmentCircle fragmentCircle=FragmentCircle.newInstance();
        bottomNavigationView.setHorizontalScrollBarEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_circles:
                        mToolbar.setTitle(R.string.circles);
                        transaction.replace(R.id.fl_container,fragmentCircle);
                        break;
                    case R.id.navigation_square:
                        mToolbar.setTitle(R.string.squares);
                        transaction.replace(R.id.fl_container, fragmentSquare);
                        break;
                    case R.id.navigation_platform:
                        mToolbar.setTitle(R.string.platforms);
                        transaction.replace(R.id.fl_container, fragmentPlatform);
                        break;
                    case R.id.navigation_notification:
                        mToolbar.setTitle(R.string.notification);
                        transaction.replace(R.id.fl_container, fragmentNotification);
                        break;
                }
                transaction.commit();
                return true;
            }
        });
    }

}
