package garrick.example.servicecontrol;

import data.GlobalSave;
import data.IconCoordinate;
import service.MainService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String mBroadcastDataAction = "garrick.example.servicecontrol.data";
	private IntentFilter mIntentFilter;
	
	private Button btn_slavepage, btn_stopservicepage, btn_zoompage;
	private Button btn_startservice, btn_stopservice;
	private TextView txtview_control;
	private EditText edittext_tabpage;
	private Button btn_tabpage;
	private Button btn_webviewpage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("MainActivity", "執行onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(mBroadcastDataAction);
		
		initView();
	}

	private void initView() {
		txtview_control = (TextView) findViewById(R.id.txtview_control);
		btn_slavepage = (Button) findViewById(R.id.btn_slavepage);
		btn_stopservicepage = (Button) findViewById(R.id.btn_stopservicepage);
		btn_zoompage = (Button) findViewById(R.id.btn_zoompage);
		btn_startservice = (Button) findViewById(R.id.btn_startservice);
		btn_stopservice = (Button) findViewById(R.id.btn_stopservice);
		edittext_tabpage = (EditText) findViewById(R.id.edittext_tabpage);
		btn_tabpage = (Button) findViewById(R.id.btn_tabpage);
		btn_webviewpage = (Button) findViewById(R.id.btn_webviewpage);
		
		btn_startservice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//start service
				Log.i("MainActivity", "Start MainService");
	            Intent intent = new Intent(MainActivity.this, MainService.class);
	            startService(intent);
			}
			
		});
		
		btn_stopservice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//stop service
				Log.i("MainActivity", "Stop MainService");
	            Intent intent = new Intent(MainActivity.this, MainService.class);
	            stopService(intent);
			}
			
		});
		
		btn_slavepage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, SlaveActivity.class);
				startActivity(i);
				MainActivity.this.finish();
			}
			
		});
		
		btn_stopservicepage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, StopServiceActivity.class);
				startActivity(i);
				MainActivity.this.finish();
			}
			
		});
		
		btn_zoompage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, ImageZoomActivity.class);
				startActivity(i);
				MainActivity.this.finish();
			}
			
		});
		
		btn_tabpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int tabcount = Integer.parseInt(edittext_tabpage.getText().toString().trim());
				
				//放入icon參數
				GlobalSave globalSave = (GlobalSave)getApplicationContext();
				globalSave._IconCoordinate.clear();
				for (int i = 1 ; i <= tabcount ; i++) {
					// 設定各icon初始座標
					IconCoordinate iconCoordinate = new IconCoordinate(i, 50 * Float.parseFloat(String.valueOf(i - 1)), 50 * Float.parseFloat(String.valueOf(i - 1)));
					globalSave._IconCoordinate.add(iconCoordinate);
				}
				
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, TabPageActivity.class);
				intent.putExtra("tabcount", String.valueOf(tabcount));
				startActivity(intent);
				MainActivity.this.finish();
			}
			
		});
		
		btn_webviewpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, WebViewActivity.class);
				startActivity(i);
				MainActivity.this.finish();
			}
			
		});
	}
	
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(mBroadcastDataAction)) {
				txtview_control.setText(intent.getStringExtra("Data"));
			}
		}
		
	};
	
	
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
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
		  {
		   // 
		  }
		  super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	System.exit(0);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
