package id.pptik.ilham.sahabatbawaslu.features.dashboard;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.login.LoginActivity;
import id.pptik.ilham.sahabatbawaslu.features.notification.NotificationActivity;
import id.pptik.ilham.sahabatbawaslu.features.signup.SignUpActivity;
import id.pptik.ilham.sahabatbawaslu.features.slidingtab.SlidingTabLayout;
import id.pptik.ilham.sahabatbawaslu.features.slidingtab.SlidingTabsBasicFragment;

public class DashboardActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.toolbar)Toolbar toolbar;
    SlidingTabLayout slidingTabLayout;
    ViewPager viewPager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Status and Toolbar Section
        ButterKnife.bind(this);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));

        toolbar.setTitle(getResources().getString(R.string.dashboard_label));
        setSupportActionBar(toolbar);

        //Sliding section
        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SlidingTabsBasicFragment slidingTabsBasicFragment = new SlidingTabsBasicFragment();
        fragmentTransaction.replace(R.id.sample_content_fragment,slidingTabsBasicFragment);
        fragmentTransaction.commit();*/

        //Pengaturan tab
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new SlidingTabsBasicFragment(getSupportFragmentManager(), this));

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.oranyeTerang));
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.putih));
        slidingTabLayout.setCustomTabView(R.layout.sliding_tab_view, R.id.tv_tab);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        getSupportActionBar().setTitle(R.string.slide_title_berita);
                        break;
                    case 1:
                        getSupportActionBar().setTitle(R.string.slide_title_materi);
                        break;
                    case 2:
                        getSupportActionBar().setTitle(R.string.slide_title_diskusi);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.slidingtab, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pop_up_notifikasi:
                Intent intent = new Intent(DashboardActivity.this, NotificationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.pop_up_edit_profile_slidingtab:
                Toast.makeText(this, "Edit Profile menu clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.pop_up_log_out_slidingtab:
                sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            default:return false;
        }
    }


}
