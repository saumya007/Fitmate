package com.saumya.fitmate.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.saumya.fitmate.extras.Util;

public class BootReciever extends BroadcastReceiver {

    public BootReciever()
    {

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Util.scheduleAlarms(context);
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
