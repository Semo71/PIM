package com.semo_prjects.pim;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.semo_prjects.pim.Alarm.AlarmActivity;
import com.semo_prjects.pim.AlarmReminder.AddReminderActivity;
import com.semo_prjects.pim.AlarmReminder.MainAlarmReminderActivity;
import com.semo_prjects.pim.Login.LoginActivity;
import com.semo_prjects.pim.Login.SetupActivity;
import com.semo_prjects.pim.Todo.MainTaskActivity;

public class MainActivity extends AppCompatActivity {

    //Declaring Variables
    private Toolbar mMainToolbar;
    private BottomNavigationView mMainNavigation;
    private FrameLayout mMainFrame;
    private FirebaseAuth mAuth;
    private MeFragment mMeFragment;
    private HomeFragment mHomeFragment;
    private ChartFragment mChartFragment;



    //Checking the user state whenever app starts and send it to login page if he isn't logged in
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            //Intent
            sendToLogin();
        }
    }




    //Called only once when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing the variables
        mAuth = FirebaseAuth.getInstance();
        mMainToolbar = (Toolbar)findViewById(R.id.mainToolbar);
        mMainNavigation=findViewById(R.id.mainNavigation);
        mMainFrame=findViewById(R.id.mainFrame);
        mMeFragment=new MeFragment();
        mHomeFragment=new HomeFragment();
        mChartFragment=new ChartFragment();

        //Preparing the Action Bar
        setSupportActionBar(mMainToolbar);


        //the main fragment
        getSupportActionBar().setTitle("Home");
        setFragment(mHomeFragment);

        //Activating the items of the navigation bar
        mMainNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.navigationHome:
                        getSupportActionBar().setTitle("Home");
                        setFragment(mHomeFragment);
                        return true;
                        case R.id.navigationMe:
                            getSupportActionBar().setTitle("Me");
                            setFragment(mMeFragment);
                        return true;
                    case R.id.navigationChart:
                        getSupportActionBar().setTitle("Chart");
                        setFragment(mChartFragment);
                        return true;
                     default:
                         return false;

                }
            }
        });
    }



    // Connecting the main menu to Main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
        }

//actions happens when Items of the menu are selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionLogoutButton:
            logout();
            return true;
            case R.id.actionSettingButton:
                Intent setupIntent = new Intent(MainActivity.this,SetupActivity.class);
                startActivity(setupIntent);
                return true;
            case R.id.actionTestButton:
                Intent testIntent = new Intent(MainActivity.this,TestActivity.class);
                startActivity(testIntent);
                return true;
            case R.id.actionAlarmButton:
                Intent alarmIntent = new Intent(MainActivity.this,AlarmActivity.class);
                startActivity(alarmIntent);
                return true;
            case R.id.actionTaskButton:
                Intent taskIntent = new Intent(MainActivity.this,MainTaskActivity.class);
                startActivity(taskIntent);
                return true;
            case R.id.actionAlarmReminderButton:
                Intent alarmReminderIntent = new Intent(MainActivity.this,MainAlarmReminderActivity.class);
                startActivity(alarmReminderIntent);

                return true;


            default:
                return false;
        }
    }






    //method to signout
    private void logout() {
        mAuth.signOut();
        sendToLogin();

    }
    //method to send user to the log in page
    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }


    //method to
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame,fragment);
        fragmentTransaction.commit();

    }

}
