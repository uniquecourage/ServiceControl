package garrick.example.servicecontrol;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SlaveActivity extends Activity {

    private IntentFilter mIntentFilter;
	
	private Button btn_mainpage, btn_stopservicepage, btn_zoompage;
	private TextView txtview_control;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slave);
		
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(MainActivity.mBroadcastDataAction);
		
		initView();
	}
	
	private void initView() {
		txtview_control = (TextView) findViewById(R.id.txtview_control);
		btn_mainpage = (Button) findViewById(R.id.btn_mainpage);
		btn_stopservicepage = (Button) findViewById(R.id.btn_stopservicepage);
		btn_zoompage = (Button) findViewById(R.id.btn_zoompage);
		
		btn_mainpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SlaveActivity.this, MainActivity.class);
				startActivity(i);
				SlaveActivity.this.finish();
			}
			
		});
		
		btn_stopservicepage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SlaveActivity.this, StopServiceActivity.class);
				startActivity(i);
				SlaveActivity.this.finish();
			}
			
		});
		
		btn_zoompage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SlaveActivity.this, ImageZoomActivity.class);
				startActivity(i);
				SlaveActivity.this.finish();
			}
			
		});
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(MainActivity.mBroadcastDataAction)) {
				txtview_control.setText(intent.getStringExtra("Data"));
			}
		}
		
	};
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
		  {
		   // 
		  }
		  super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    registerReceiver(mReceiver, mIntentFilter);
	}
	
	@Override
	protected void onPause() {
	    unregisterReceiver(mReceiver);
	    super.onPause();
	}
}
