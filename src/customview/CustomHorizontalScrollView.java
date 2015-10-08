package customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class CustomHorizontalScrollView extends HorizontalScrollView {

	private boolean enableScrolling = true;

	  public boolean isEnableScrolling() {
	      return enableScrolling;
	  }

	  public void setEnableScrolling(boolean enableScrolling) {
	      this.enableScrolling = enableScrolling;
	  }
	
	public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
	      super(context, attrs, defStyle);
	  }

	  public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
	      super(context, attrs);
	  }

	  public CustomHorizontalScrollView(Context context) {
	      super(context);
	  }
	  
	  @Override
	  public boolean onTouchEvent(MotionEvent ev) {
	      switch (ev.getAction()) {
	          case MotionEvent.ACTION_DOWN:
	              // if we can scroll pass the event to the superclass
	              if (enableScrolling) return super.onTouchEvent(ev);
	              // only continue to handle the touch event if scrolling enabled
	              return enableScrolling; // mScrollable is always false at this point
	          default:
	              return super.onTouchEvent(ev);
	      }
	  }



	  @Override
	  public boolean onInterceptTouchEvent(MotionEvent ev) {

	      if (isEnableScrolling()) {
	          return super.onInterceptTouchEvent(ev);
	      } else {
	          return false;
	      }
	  }
}
