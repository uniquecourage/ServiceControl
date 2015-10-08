package garrick.example.servicecontrol;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;

public class ImageZoomActivity extends Activity {

	private Button btn_mainpage, btn_slavepage, btn_stopservicepage;
	
	private customview.ZoomableRelativeLayout zoomableRelativeLayout;
	public ImageView smartImageView;
	
	private ScaleGestureDetector scaleGestureDetector;
	
	private customview.CustomScrollView scrollview;
	private customview.CustomHorizontalScrollView horizontalScrollView;
	
	private int nowstate;
	private final int dragstate = 1;
	private final int zoomstate = 2;
	private float currentDistance;
	private float lastDistance = -1;
	
	private float centerPointX;
	private float centerPointY;
	private double lastFingerDis;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_imagezoom);
		
		initView();
		
		creatSmallIcon(1, (float)0, (float)0, R.drawable.left_exit);
		creatSmallIcon(2, (float)0, (float)200, R.drawable.foot_rise_red);
	}
	
	private void initView() {
		scrollview = (customview.CustomScrollView) findViewById(R.id.scrollview);
		horizontalScrollView = (customview.CustomHorizontalScrollView) findViewById(R.id.horizontalScrollView);
		zoomableRelativeLayout = (customview.ZoomableRelativeLayout) findViewById(R.id.zoomableRelativeLayout);
		smartImageView = (ImageView) findViewById(R.id.smartimageView);
		smartImageView.setBackgroundResource(R.drawable.cadlightingdrawing);
		//smartImageView.setBackgroundResource(R.drawable.map);
		Bitmap bm = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.cadlightingdrawing);
		smartImageView.setLayoutParams(new RelativeLayout.LayoutParams(bm.getWidth(), bm
                .getHeight()));
		
		btn_mainpage = (Button) findViewById(R.id.btn_mainpage);
		btn_slavepage = (Button) findViewById(R.id.btn_slavepage);
		btn_stopservicepage = (Button) findViewById(R.id.btn_stopservicepage);
		
		btn_mainpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageZoomActivity.this, MainActivity.class);
				startActivity(intent);
				ImageZoomActivity.this.finish();
			}
			
		});
		
		btn_slavepage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageZoomActivity.this, SlaveActivity.class);
				startActivity(intent);
				ImageZoomActivity.this.finish();
			}
			
		});
		
		btn_stopservicepage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ImageZoomActivity.this, StopServiceActivity.class);
				startActivity(intent);
				ImageZoomActivity.this.finish();
			}
			
		});

		
		scaleGestureDetector = new ScaleGestureDetector(this, new OnPinchListener());
		zoomableRelativeLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
		
		
		//setZoomableRelativeLayout();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
		  {
		   // 
		  }
		  super.onConfigurationChanged(newConfig);
	}
	
	private void setZoomableRelativeLayout() {
		zoomableRelativeLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
			    switch (event.getAction() & MotionEvent.ACTION_MASK) {
			    case MotionEvent.ACTION_DOWN:
			    	nowstate = dragstate;
			    	
			    	Log.i("nowstate(ACTION_DOWN)", "nowstate = dragstate");
			    	break;
			    case MotionEvent.ACTION_UP:
			    	nowstate = dragstate;
			    	scrollview.setEnableScrolling(true);
			    	horizontalScrollView.setEnableScrolling(true);
			    	Log.i("nowstate(ACTION_UP)", "nowstate = " + nowstate);
			    	break;
			    case MotionEvent.ACTION_POINTER_DOWN:
			    	scrollview.setEnableScrolling(false);
			    	horizontalScrollView.setEnableScrolling(false);
			    	Log.i("nowstate(ACTION_POINTER_DOWN)", "nowstate = zoomstate");
			    	break;
		        case MotionEvent.ACTION_POINTER_UP:
		        	scrollview.setEnableScrolling(true);
			    	horizontalScrollView.setEnableScrolling(true);
		        	break;
		        case MotionEvent.ACTION_MOVE:

		        		if (event.getPointerCount() >= 2) {
		        			nowstate = zoomstate;
		        			scrollview.setEnableScrolling(false);
					    	horizontalScrollView.setEnableScrolling(false);
					    	/*
		        			float offsetX = event.getX(0) - event.getX(1);
							float offsetY = event.getY(0) - event.getY(1);
							//原點和滑動後點的距離差
							currentDistance = (float) Math.sqrt(offsetX * offsetX
									+ offsetY * offsetY);
							Log.i("zoomstate(ACTION_MOVE)", "currentDistance = " + currentDistance + " ; " + "lastDistance = " + lastDistance);
							if (lastDistance < 0) {
								lastDistance = currentDistance;
							} else {
								if (currentDistance - lastDistance > 5) {
									Log.i("nowstate(ACTION_MOVE)", "放大");
									
									lastDistance = currentDistance;
								} else if (lastDistance - currentDistance > 5) {
									Log.i("nowstate(ACTION_MOVE)", "縮小");
									
									lastDistance = currentDistance;
								}

							}	
		        			*/
					    	
					    	
					    	centerPointBetweenFingers(event);
					    	double fingerDis = distanceBetweenFingers(event);
					    	Log.i("nowstate(ACTION_MOVE)", "fingerDis - lastFingerDis = " + (fingerDis - lastFingerDis));
					    	if (fingerDis > lastFingerDis && (fingerDis - lastFingerDis) > 10) {
					    		Log.i("nowstate(ACTION_MOVE)", "放大");
					    		
					    	} else if (fingerDis < lastFingerDis && (lastFingerDis - fingerDis) > 10) {
					    		Log.i("nowstate(ACTION_MOVE)", "縮小");
					    		
					    	}
					    	lastFingerDis = fingerDis;
					    	
		        		}

		        	
		        	break;
			    }
				return true;
			}
			
		});
	}
	
	//計算兩個手指之間的距離
	private double distanceBetweenFingers(MotionEvent event) {
		float disX = Math.abs(event.getX(0) - event.getX(1));
		float disY = Math.abs(event.getY(0) - event.getY(1));
		return Math.sqrt(disX * disX + disY * disY);
	}
	
	//計算兩個手指之間中心點的座標
	private void centerPointBetweenFingers(MotionEvent event) {
		float xPoint0 = event.getX(0);
		float yPoint0 = event.getY(0);
		float xPoint1 = event.getX(1);
		float yPoint1 = event.getY(1);
		centerPointX = (xPoint0 + xPoint1) / 2;
		centerPointY = (yPoint0 + yPoint1) / 2;
	}
	
	
	private void creatSmallIcon(int iconid, Float x, Float y, int drawableid) {
		LinearLayout ll = new LinearLayout(ImageZoomActivity.this);
		ll.setOrientation(LinearLayout.HORIZONTAL);
	    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
	                LinearLayout.LayoutParams.WRAP_CONTENT,
	                LinearLayout.LayoutParams.WRAP_CONTENT);
	    param.gravity = Gravity.CENTER;
	    ll.setLayoutParams(param);
	    ll.setOrientation(LinearLayout.VERTICAL);
	    
	  //icon
        SmartImageView iv = new SmartImageView(ImageZoomActivity.this);  
        param.gravity = Gravity.TOP;
        iv.setId(iconid);
        iv.setLayoutParams(param);
        iv.setImageResource(drawableid);
        
        ll.addView(iv);
        zoomableRelativeLayout.addView(ll,param);
		
		ll.setX(x);
		ll.setY(y);
		moveIcon(ll, iconid);
	}
	
	//移動Icon
		private int _xDelta;
		private int _yDelta;
		public void moveIcon(LinearLayout ll, int iconid){
			final int mid = iconid;
			ll.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					final int X = (int) event.getRawX();
				    final int Y = (int) event.getRawY();
				    switch (event.getAction() & MotionEvent.ACTION_MASK) {
				    case MotionEvent.ACTION_DOWN:
				    	scrollview.setEnableScrolling(false);
				    	horizontalScrollView.setEnableScrolling(false);
			            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
			            _xDelta = X - lParams.leftMargin;
			            _yDelta = Y - lParams.topMargin;
			            
			            break;
				    case MotionEvent.ACTION_UP:
			        	
			        	float mX = v.getX();
			        	float mY = v.getY();
			        	Log.i("moveIcon(ACTION_UP)", "mX = " + (int)mX + " ; " + "mY = " + (int)mY + " ; " + "mid = " + mid);
			        	
			        	scrollview.setEnableScrolling(true);
				    	horizontalScrollView.setEnableScrolling(true);
			        	
			            break;
			        case MotionEvent.ACTION_POINTER_DOWN:
			            break;
			        case MotionEvent.ACTION_POINTER_UP:
			            break;
			        case MotionEvent.ACTION_MOVE:
			            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
			            layoutParams.leftMargin = X - _xDelta;
			            layoutParams.topMargin = Y - _yDelta;
			            layoutParams.rightMargin = -250;
			            layoutParams.bottomMargin = -250;
			            
			            v.setLayoutParams(layoutParams);
			            break;    
				    }
					
					return true;
				}
				
			});
		}
		
		
		
		private class OnPinchListener extends SimpleOnScaleGestureListener {

		    float startingSpan; 
		    float endSpan;
		    float startFocusX;
		    float startFocusY;

		    public boolean onScaleBegin(ScaleGestureDetector detector) { //縮放開始
		    	scrollview.setEnableScrolling(false);
		    	horizontalScrollView.setEnableScrolling(false);
		    	
		    	startingSpan = detector.getCurrentSpan();
		        startFocusX = detector.getFocusX();
		        startFocusY = detector.getFocusY();

		        Log.i("onScaleBegin", "startingSpan = " + startingSpan + " ; " + "startFocusX = " + startFocusX + " ; " + "startFocusY = " + startFocusY);
		        return true;
		    }


		    public boolean onScale(ScaleGestureDetector detector) { //縮放中
		    	Log.i("onScale", "startingSpan = " + startingSpan + " ; " + "startFocusX = " + startFocusX + " ; " + "startFocusY = " + startFocusY);
		    	zoomableRelativeLayout.scale(detector.getCurrentSpan()/startingSpan, startFocusX, startFocusY); 
		    	return true;
		    }

		    public void onScaleEnd(ScaleGestureDetector detector) { //縮放結束
		    	Log.i("onScaleEnd", "執行onScaleEnd");
		    	scrollview.setEnableScrolling(true);
		    	horizontalScrollView.setEnableScrolling(true);
		    	if(detector.getCurrentSpan()/startingSpan<1){
		    		zoomableRelativeLayout.restore();
		    	}
		    }
		}
		
}
