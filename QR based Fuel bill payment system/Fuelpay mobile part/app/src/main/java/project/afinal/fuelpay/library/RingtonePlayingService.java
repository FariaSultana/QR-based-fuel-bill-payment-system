package project.afinal.fuelpay.library;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import project.afinal.fuelpay.FuelAlarmActivity;
import project.afinal.fuelpay.R;

public class RingtonePlayingService extends Service {

    private boolean isRunning;
    private Context context;
    private int startId;

    long[] mVibratePattern = {50, 0, 50, 100, 200, 300, 0, 0, 0, 50, 100, 200, 300, 0, 0, 0, 50, 50, 50, 500};

    Uri soundURI = Uri.parse("android.resource://project.afinal.fuelpay/"
            + R.raw.car_sound);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), FuelAlarmActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);


        Notification mNotify = new Notification.Builder(this)
                .setContentTitle("Alert from " + getResources().getString(R.string.app_name)+"!")
                .setContentText("Your fuel is almost empty, please reload!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setSound(soundURI).setVibrate(mVibratePattern)
                .setAutoCancel(true)
                .build();

        String state = intent.getExtras().getString("extra");

        Log.e("what is going on here  ", state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }


        if (!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");

            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;

        } else if (!this.isRunning && startId == 0) {
            Log.e("if there was not sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;

        } else if (this.isRunning && startId == 1) {
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;

        } else {
            Log.e("if there is sound ", " and you want end");

            this.isRunning = false;
            this.startId = 0;
        }
        return START_NOT_STICKY;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        this.isRunning = false;
    }


}
