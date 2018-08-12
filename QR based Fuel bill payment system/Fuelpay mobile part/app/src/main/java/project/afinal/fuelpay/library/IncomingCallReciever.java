package project.afinal.fuelpay.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Method;

public class IncomingCallReciever extends BroadcastReceiver {
    private static String number;
    TelephonyManager telephonyManager;
    SharedPreferencesClass storePreference;


    @Override
    public void onReceive(Context context, Intent intent) {
        storePreference = new SharedPreferencesClass(context);
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING) && storePreference.getString("mood").equals("drive")) {
            String msg = "Now I am driving, I will call back later";
            Toast.makeText(context, "Incoming Call Ringing" + number, Toast.LENGTH_SHORT).show();
            rejectCall();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, msg, null, null);
            Toast.makeText(context, "Message Sent",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void rejectCall() {
        try {
            // Get the getITelephony() method
            Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method method = classTelephony.getDeclaredMethod("getITelephony");
            // Disable access check
            method.setAccessible(true);
            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = method.invoke(telephonyManager);
            // Get the endCall method from ITelephony
            Class<?> telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}