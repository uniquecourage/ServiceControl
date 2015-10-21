package garrick.example.servicecontrol;

import service.BlueLightService;
import service.GreenLightService;
import service.MainService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MultiServicesActivity extends Activity {

	public static final String mBroadcastBlueLightAction = "garrick.example.servicecontrol.bluelight";
	public static final String mBroadcastGreenLightAction = "garrick.example.servicecontrol.greenlight";
	private IntentFilter mIntentFilter_bluelight;
	private IntentFilter mIntentFilter_greenlight;
	
	private ImageView imgView_bluelight, imgView_greenlight;
	private TextView txtView_bluelight, txtView_greenlight;
	private Button btn_stop_all_services;
	
	private boolean bluelight_flag = false;
	private boolean greenlight_flag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiservices);
		
		mIntentFilter_bluelight = new IntentFilter();
		mIntentFilter_bluelight.addAction(mBroadcastBlueLightAction);
		
		mIntentFilter_greenlight = new IntentFilter();
		mIntentFilter_greenlight.addAction(mBroadcastGreenLightAction);
		
		initView();
	}
	
	private void initView() {
		imgView_bluelight = (ImageView) findViewById(R.id.imgView_bluelight);
		imgView_greenlight = (ImageView) findViewById(R.id.imgView_greenlight);
		txtView_bluelight = (TextView) findViewById(R.id.txtView_bluelight);
		txtView_greenlight = (TextView) findViewById(R.id.txtView_greenlight);
		btn_stop_all_services = (Button) findViewById(R.id.btn_stop_all_services);
		
		imgView_bluelight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bluelight_flag == false) {
					bluelight_flag = true;
					txtView_bluelight.setText("ON");
					Log.i("MultiServicesActivity", "Start BlueLightService");
		            Intent intent = new Intent(MultiServicesActivity.this, BlueLightService.class);
		            startService(intent);
				} else {
					bluelight_flag = false;
					txtView_bluelight.setText("OFF");
					Log.i("MultiServicesActivity", "Stop BlueLightService");
		            Intent intent = new Intent(MultiServicesActivity.this, BlueLightService.class);
		            stopService(intent);
				}
			}
			
		});
		
		imgView_greenlight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (greenlight_flag == false) {
					greenlight_flag = true;
					txtView_greenlight.setText("ON");
					Log.i("MultiServicesActivity", "Start GreenLightService");
		            Intent intent = new Intent(MultiServicesActivity.this, GreenLightService.class);
		            startService(intent);
				} else {
					greenlight_flag = false;
					txtView_greenlight.setText("OFF");
					Log.i("MultiServicesActivity", "Stop GreenLightService");
		            Intent intent = new Intent(MultiServicesActivity.this, GreenLightService.class);
		            stopService(intent);
				}
			}
			
		});
		
		btn_stop_all_services.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bluelight_flag = false;
				greenlight_flag = false;
				txtView_bluelight.setText("OFF");
				txtView_greenlight.setText("OFF");
				
				Intent intent01 = new Intent(MultiServicesActivity.this, BlueLightService.class);
	            stopService(intent01);
	            Intent intent02 = new Intent(MultiServicesActivity.this, GreenLightService.class);
	            stopService(intent02);
			}
			
		});
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("mReceiver", "intent.getAction() = " + intent.getAction());
			if (intent.getAction().equals(mBroadcastBlueLightAction)) {
				if (intent.getStringExtra("BlueLight").equals("1")) {
					imgView_bluelight.setImageResource(R.drawable.bulb_blue);
				} else {
					imgView_bluelight.setImageResource(R.drawable.bulb);
				}
			}
			
			if (intent.getAction().equals(mBroadcastGreenLightAction)) {
				if (intent.getStringExtra("GreenLight").equals("1")) {
					imgView_greenlight.setImageResource(R.drawable.bulb_green);
				} else {
					imgView_greenlight.setImageResource(R.drawable.bulb);
				}
			}
		}
		
	};
	
	@Override
	public void onResume() {
	    super.onResume();
	    registerReceiver(mReceiver, mIntentFilter_bluelight);
	    registerReceiver(mReceiver, mIntentFilter_greenlight);
	}
	
	@Override
	protected void onPause() {
	    unregisterReceiver(mReceiver);
	    super.onPause();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	Intent intent = new Intent(MultiServicesActivity.this, MainActivity.class);
	    	startActivity(intent);
	    	MultiServicesActivity.this.finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
