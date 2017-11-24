package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvanz.CustomTypefaceSpan;
import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.SharedPref;
import com.eduvanz.newUI.fragments.DashboardFragmentNew;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    TextView textViewName, textViewEmail;
    Button buttonSignup, buttonSignIn;
    MainApplication mainApplication;
    FrameLayout frameLayoutDashboard;
    public static NavigationView navigationView;
    public static DrawerLayout drawer;
    SharedPref sharedPref;
    LinearLayout linearLayoutSignup, linearLayoutUserDetail;
    String userFirst="", userLast="", userEmail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        context = getApplicationContext();
        mainApplication = new MainApplication();
        sharedPref = new SharedPref();

        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            userFirst = sharedPreferences.getString("first_name", "");
            userLast = sharedPreferences.getString("last_name", "");
            userEmail = sharedPreferences.getString("user_email", "");
        }catch (Exception e){
            e.printStackTrace();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //**To change the hamburger color on dashboard **/
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        hideMenuOptions();

        textViewName = (TextView) header.findViewById(R.id.textView_name);
        mainApplication.applyTypeface(textViewName, context);
        textViewName.setText(userFirst +" "+userLast);
        textViewEmail = (TextView) header.findViewById(R.id.textView_emailID);
        mainApplication.applyTypeface(textViewEmail, context);
        textViewEmail.setText(userEmail);

        buttonSignup = (Button) header.findViewById(R.id.button_dashboard_signup);
        mainApplication.applyTypeface(buttonSignup, context);
        buttonSignIn = (Button) header.findViewById(R.id.button_dashboard_signin);
        mainApplication.applyTypeface(buttonSignIn, context);

        linearLayoutSignup = (LinearLayout) header.findViewById(R.id.linearLayout_signupdetail_dashboard);
        linearLayoutUserDetail = (LinearLayout) header.findViewById(R.id.linearLayout_userdetail_dashboard);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SignIn.class);
                startActivity(intent);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        frameLayoutDashboard = (FrameLayout) findViewById(R.id.framelayout_dashboard);
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        if(sharedPref.getLoginDone(context)) {
            linearLayoutUserDetail.setVisibility(View.VISIBLE);
            linearLayoutSignup.setVisibility(View.GONE);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_loanApplication).setVisible(true);
            nav_Menu.findItem(R.id.nav_eligibility).setVisible(false);
        }

        getSupportFragmentManager().beginTransaction().add(R.id.framelayout_dashboard, new DashboardFragmentNew()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.dashboard, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myprofile) {
            // Handle the camera action
        } else if (id == R.id.nav_eligibility) {
            Intent intent = new Intent(DashboardActivity.this, EligibilityCheck.class);
            startActivity(intent);
        } else if (id == R.id.nav_howitworks) {
            Intent intent = new Intent(context, HowItWorks.class);
            startActivity(intent);
        } else if (id == R.id.nav_aboutus) {

        } else if (id == R.id.nav_faq) {
            Intent intent = new Intent(DashboardActivity.this, Faq.class);
            startActivity(intent);
        } else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(DashboardActivity.this, ContactUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_more) {
            showMenuOptions();
        } else if (id == R.id.nav_privacypolicy) {

        } else if (id == R.id.nav_termsandconditions) {

        } else if (id == R.id.nav_disclaimer) {

        } else if (id == R.id.nav_fairpracticscode) {

        } else if (id == R.id.nav_logout) {

        }else if (id == R.id.nav_loanApplication) {
            Intent intent = new Intent(context, LoanApplication.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(id == R.id.nav_more){
            drawer.openDrawer(GravityCompat.START);
        }else {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void hideMenuOptions() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_privacypolicy).setVisible(false);
        nav_Menu.findItem(R.id.nav_termsandconditions).setVisible(false);
        nav_Menu.findItem(R.id.nav_disclaimer).setVisible(false);
        nav_Menu.findItem(R.id.nav_fairpracticscode).setVisible(false);
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        nav_Menu.findItem(R.id.nav_loanApplication).setVisible(false);
    }

    private void showMenuOptions() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_more).setVisible(false);
        nav_Menu.findItem(R.id.nav_privacypolicy).setVisible(true);
        nav_Menu.findItem(R.id.nav_termsandconditions).setVisible(true);
        nav_Menu.findItem(R.id.nav_disclaimer).setVisible(true);
        nav_Menu.findItem(R.id.nav_fairpracticscode).setVisible(true);
        nav_Menu.findItem(R.id.nav_logout).setVisible(true);
    }
}
