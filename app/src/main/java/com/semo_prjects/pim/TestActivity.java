package com.semo_prjects.pim;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.semo_prjects.pim.AlarmReminder.MainAlarmReminderActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestActivity extends AppCompatActivity {

    private CircleImageView programmer_was_provoked;
    //private int mScore;
    //private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

       // if (savedInstanceState != null) {
           // mScore = savedInstanceState.getInt("ScoreKey");
            //mIndex = savedInstanceState.getInt("IndexKey");}


            programmer_was_provoked = findViewById(R.id.programmer_was_provoked);
            programmer_was_provoked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(TestActivity.this, programmer_was_provoked);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());


                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            Intent alarmReminderIntent = new Intent(TestActivity.this,MainAlarmReminderActivity.class);
                            startActivity(alarmReminderIntent);


                            Toast.makeText(
                                    TestActivity.this,
                                    "Hi nigga  ",
                                    Toast.LENGTH_LONG
                            ).show();
                            return true;
                        }
                    });

                    popup.show(); //showing popup menu
                }
            });
        }

        // Popup Menu Code    https://www.javatpoint.com/android-popup-menu-example

    }

