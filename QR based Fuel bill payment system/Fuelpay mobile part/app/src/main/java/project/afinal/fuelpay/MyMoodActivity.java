package project.afinal.fuelpay;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import project.afinal.fuelpay.library.SharedPreferencesClass;

/**
 * Created by ASUS on 2/5/2018.
 */

public class MyMoodActivity extends Activity {

    TextView moodDetails, mood;
    ImageView imgMood;
    SharedPreferencesClass storePreference;
    long[] mVibratePattern = {50, 0, 50, 100, 200, 300, 0, 0, 0, 50, 100, 200, 300, 0, 0, 0, 50, 50, 50, 500};

    Uri soundURI = Uri.parse("android.resource://project.afinal.fuelpay/"
            + R.raw.car_sound);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mood);

        imgMood = findViewById(R.id.imgMood);
        mood = findViewById(R.id.mood);
        moodDetails = findViewById(R.id.moodDetails);

        storePreference = new SharedPreferencesClass(getApplicationContext());
        if (storePreference.getString("mood").equals("")) {
            normal();
        } else {
            drive();
        }

    }

    public void moodChange(View v) {
        if (storePreference.getString("mood").equals("")) {
            drive();
            notification();
            storePreference.putString("mood", "drive");

        } else {
            normal();
            storePreference.putString("mood", "");
        }
    }

    public void normal() {
        mood.setText("Normal Mood");
        moodDetails.setText("Tap to turn on Driving Mood, currently you are on normal mood.");
        imgMood.setBackgroundResource(R.drawable.normal_mood);
    }

    public void drive() {
        mood.setText("Drive Mood");
        moodDetails.setText("Tap to turn on Normal Mood, your incoming call turn off and automatically send a SMS that you are on driving mood.");
        imgMood.setBackgroundResource(R.drawable.drive_mood);
    }

    public void notification(){
        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), MyMoodActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);


        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Alert from " + getResources().getString(R.string.app_name)+"!")
                .setContentText("You are on normal mood! your incoming call turn off and automatically send a SMS that you are on driving mood.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(soundURI).setVibrate(mVibratePattern)
                .setAutoCancel(true)
                .build();
        mNM.notify(0, mNotify);
    }
}
