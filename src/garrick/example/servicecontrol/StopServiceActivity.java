package garrick.example.servicecontrol;

import data.RelateData;
import service.MainService;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StopServiceActivity extends Activity {

	private Button btn_mainpage, btn_slavepage, btn_zoompage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stopservice);
		
		RelateData._StopServiceActivity = StopServiceActivity.this;
		
		initView();
	}
	
	private void initView() {
		btn_mainpage = (Button) findViewById(R.id.btn_mainpage);
		btn_slavepage = (Button) findViewById(R.id.btn_slavepage);
		btn_zoompage = (Button) findViewById(R.id.btn_zoompage);
		
		btn_mainpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(StopServiceActivity.this, MainActivity.class);
				startActivity(i);
				StopServiceActivity.this.finish();
			}
			
		});
		
		btn_slavepage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(StopServiceActivity.this, SlaveActivity.class);
				startActivity(i);
				StopServiceActivity.this.finish();
			}
			
		});
		
		btn_zoompage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(StopServiceActivity.this, ImageZoomActivity.class);
				startActivity(i);
				StopServiceActivity.this.finish();
			}
			
		});
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
		  {
		   // 
		  }
		  super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onStart() {
		Log.i("StopServiceActivity", "Stop MainService");
        Intent intent = new Intent(StopServiceActivity.this, MainService.class);
        stopService(intent);
        
        super.onStart();
	}
}
