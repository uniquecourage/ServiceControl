package garrick.example.servicecontrol;

import java.util.ArrayList;
import java.util.List;

import com.loopj.android.image.SmartImageView;

import data.GlobalSave;
import data.IconCoordinate;

import CommonFunction.OnZoomPinchListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TabPageActivity extends FragmentActivity {

	private int i_tabcount;
	private static List<String> list_tabname = new ArrayList<String>();
	
	private static List<IconCoordinate> list_IconCoordinate = new ArrayList<IconCoordinate>();
	private static GlobalSave _globalSave;
	
	private TextView txtView_index;
	private Button btn_main;
	private ViewPager mPager;
	private ViewPagerAdapter mAdapter;
	
	private static customview.ZoomableRelativeLayout zoomableRelativeLayout;
	private ScaleGestureDetector scaleGestureDetector;
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabpage);
		
		list_tabname.clear(); // 進來此頁時須先清除, 不然會累加
		Intent intent = this.getIntent();
		i_tabcount = Integer.parseInt(intent.getStringExtra("tabcount"));
		for (int i = 0 ; i < i_tabcount ; i++) {
			list_tabname.add("tabpage : " + String.valueOf(i + 1));
		}
		
		list_IconCoordinate.clear();
		_globalSave = (GlobalSave)getApplicationContext();
		list_IconCoordinate = _globalSave._IconCoordinate;
		
		initView();
	}
	
	private void initView() {
		txtView_index = (TextView) findViewById(R.id.txtView_index);
		txtView_index.setText("第1頁");
		btn_main = (Button) findViewById(R.id.btn_main);
		btn_main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TabPageActivity.this, MainActivity.class);
				startActivity(i);
				TabPageActivity.this.finish();
			}
			
		});
		
		
		mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) { //狀態改變時調用
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { //在滑動時調用
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) { //頁面跳轉後調用
				// TODO Auto-generated method stub
				txtView_index.setText("第" + String.valueOf(arg0 + 1) + "頁");
			}
			
		});
	}
	
	public static class ViewPagerAdapter extends FragmentPagerAdapter {
		
		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			ViewPagerFragment fragment = new ViewPagerFragment();
			Bundle args = new Bundle();
			args.putString("value", list_tabname.get(position).toString());
			args.putString("index", String.valueOf(position));
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_tabname.size();
		}
	}
	
	public static class ViewPagerFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View backView = inflater.inflate(R.layout.viewpager_map, null); // layout套用自訂xml
			customview.ZoomableRelativeLayout zoomableRelativeLayout_map = (customview.ZoomableRelativeLayout) backView.findViewById(R.id.mapLayout);
			SmartImageView mapSmartImageView = (SmartImageView) backView.findViewById(R.id.imageView1);
			zoomableRelativeLayout_map.setBackgroundColor(0xff00fa9a);
			mapSmartImageView.setImageResource(R.drawable.map);
			
//			RelativeLayout PageView = new RelativeLayout(getActivity());
//			RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
//					RelativeLayout.LayoutParams.MATCH_PARENT,
//					RelativeLayout.LayoutParams.MATCH_PARENT);
//			PageView.setLayoutParams(param);
//			PageView.setBackgroundColor(0xff00fa9a);
//			
//			customview.ZoomableRelativeLayout zoomableRelativeLayout1 = new customview.ZoomableRelativeLayout(getActivity());
//			customview.ZoomableRelativeLayout.LayoutParams param_zoom = new customview.ZoomableRelativeLayout.LayoutParams(
//					RelativeLayout.LayoutParams.MATCH_PARENT,
//					RelativeLayout.LayoutParams.MATCH_PARENT);
//			zoomableRelativeLayout1.setLayoutParams(param_zoom);
			
//			final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getActivity(), new OnZoomPinchListener(zoomableRelativeLayout1));
//			zoomableRelativeLayout1.setOnTouchListener(new OnTouchListener() {
//
//	            @Override
//	            public boolean onTouch(View v, MotionEvent event) {
//	            	
//	                scaleGestureDetector.onTouchEvent(event);
//	                return true;
//	            }
//	        });
			
			final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getActivity(), new OnZoomPinchListener(zoomableRelativeLayout_map));
			zoomableRelativeLayout_map.setOnTouchListener(new OnTouchListener() {

	            @Override
	            public boolean onTouch(View v, MotionEvent event) {
	            	
	                scaleGestureDetector.onTouchEvent(event);
	                return true;
	            }
	        });
			
			
			
			LinearLayout ll = new LinearLayout(getActivity());
			LinearLayout.LayoutParams ll_param = new LinearLayout.LayoutParams(
	                LinearLayout.LayoutParams.WRAP_CONTENT,
	                LinearLayout.LayoutParams.WRAP_CONTENT);
			ll_param.gravity = Gravity.CENTER;
	        ll.setLayoutParams(ll_param);
	        ll.setOrientation(LinearLayout.VERTICAL);
//	        ll.setX(50 * Float.parseFloat(getArguments().getString("index")));
//	        ll.setY(50 * Float.parseFloat(getArguments().getString("index")));
	        
	        ll.setId(Integer.parseInt(getArguments().getString("index")) + 1);
	        ll.setX(list_IconCoordinate.get(Integer.parseInt(getArguments().getString("index"))).get_coordinate_x());
	        ll.setY(list_IconCoordinate.get(Integer.parseInt(getArguments().getString("index"))).get_coordinate_y());
			
			ImageView imageView = new ImageView(getActivity());
			//imageView.setX(50 * Float.parseFloat(getArguments().getString("index")));
			//imageView.setY(50 * Float.parseFloat(getArguments().getString("index")));
			imageView.setImageResource(R.drawable.ic_launcher);
			
			TextView textView = new TextView(getActivity());
			textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			textView.setGravity(Gravity.CENTER);
			textView.setText(getArguments().getString("value"));
			
			// addView使用在自訂Layout布局
			ll.addView(imageView);
//			PageView.addView(zoomableRelativeLayout1);
//			PageView.addView(ll);
//			PageView.addView(textView);
			
			zoomableRelativeLayout_map.addView(ll);
			zoomableRelativeLayout_map.addView(textView);
			
			moveImage(ll);
			return backView;
		}
	}
	
	private static int _xDelta;
	private static int _yDelta;
	public static void moveImage(LinearLayout ll) {
		ll.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				final int X = (int) event.getRawX();
			    final int Y = (int) event.getRawY();
			    switch (event.getAction() & MotionEvent.ACTION_MASK) {
			    case MotionEvent.ACTION_DOWN:
			    	RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
		            _xDelta = X - lParams.leftMargin;
		            _yDelta = Y - lParams.topMargin;
			    	break;
			    case MotionEvent.ACTION_UP:
			    	float mX = v.getX();
		        	float mY = v.getY();
		        	Log.i("moveImage(ACTION_UP)", "mX = " + (int)mX + " ; " + "mY = " + (int)mY);
		        	
		        	// 儲存座標
		        	list_IconCoordinate.set(v.getId() - 1, new IconCoordinate(v.getId(), mX, mY));
		        	_globalSave._IconCoordinate.set(v.getId() - 1, new IconCoordinate(v.getId(), mX, mY));
		        	
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
	
	
}
