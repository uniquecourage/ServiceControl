package service;

import garrick.example.servicecontrol.MainActivity;
import garrick.example.servicecontrol.StopServiceActivity;

import java.util.Date;

import data.CommonFunction;
import data.RelateData;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {

	private Handler handler = new Handler();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
	    handler.postDelayed(showExecution, 1000);
	    super.onStart(intent, startId);
	}
	
	@Override
    public void onDestroy() {
        handler.removeCallbacks(showExecution);
        super.onDestroy();
    }
	
	private Runnable showExecution = new Runnable() {
        public void run() {

        	Log.i("MainService", "CommonFunction.isBackground(getApplicationContext()) = " + CommonFunction.isBackground(getApplicationContext()));
        	if(CommonFunction.isBackground(getApplicationContext()) == 0) {
        		stopSelf(); //Service自我關閉
        	} else {

        		//log目前時間
                Log.i("MainService", new Date().toString());
                
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MainActivity.mBroadcastDataAction);
                broadcastIntent.putExtra("Data", new Date().toString());
                sendBroadcast(broadcastIntent);
        	}
            		
            	
                    
                   
        	handler.postDelayed(this, 1000);
        }
    };
}
