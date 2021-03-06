package customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

//自定ScrollView以可用於處裡滾動停止
public class CustomScrollView extends ScrollView { 

  private boolean enableScrolling = true;

  public boolean isEnableScrolling() {
      return enableScrolling;
  }

  public void setEnableScrolling(boolean enableScrolling) {
      this.enableScrolling = enableScrolling;
  }

  public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
      super(context, attrs, defStyle);
  }

  public CustomScrollView(Context context, AttributeSet attrs) {
      super(context, attrs);
  }

  public CustomScrollView(Context context) {
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
