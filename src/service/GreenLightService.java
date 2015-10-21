package service;

import garrick.example.servicecontrol.MultiServicesActivity;
import data.CommonFunction;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class GreenLightService extends Service {

	private Handler handler = new Handler();
	private String flag = "0";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		handler.postDelayed(showExecution, 2000);
		super.onStart(intent, startId);
	}
	
	@Override
    public void onDestroy() {
		handler.removeCallbacks(showExecution);
		super.onDestroy();
	}
	
	private Runnable showExecution = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.i("GreenLightService", "CommonFunction.isBackground(getApplicationContext()) = " + CommonFunction.isBackground(getApplicationContext()));
			if(CommonFunction.isBackground(getApplicationContext()) == 0) {
        		stopSelf(); //Service自我關閉
        	} else {
        		Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MultiServicesActivity.mBroadcastGreenLightAction);
                broadcastIntent.putExtra("GreenLight", flag);
                sendBroadcast(broadcastIntent);
                
                if (flag.equals("0")) {
                	flag = "1";
                } else {
                	flag = "0";
                }
        	}
			
			handler.postDelayed(this, 2000);
		}
		
	};
}
