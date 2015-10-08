package CommonFunction;

import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class OnZoomPinchListener extends SimpleOnScaleGestureListener {
	float startingSpan; 
    float endSpan;
    float startFocusX;
    float startFocusY;
    customview.ZoomableRelativeLayout zoomableRelativeLayout;
    
    public OnZoomPinchListener(customview.ZoomableRelativeLayout zoomableRelativeLayout) {
    	this.zoomableRelativeLayout = zoomableRelativeLayout;
    }
    
    
    public boolean onScaleBegin(ScaleGestureDetector detector) { //縮放開始
    	startingSpan = detector.getCurrentSpan();
        startFocusX = detector.getFocusX();
        startFocusY = detector.getFocusY();
        
        Log.i("onScaleBegin", "startingSpan = " + startingSpan + " ; " + "startFocusX = " + startFocusX + " ; " + "startFocusY = " + startFocusY);
        return true;
    }
    
    public boolean onScale(ScaleGestureDetector detector) { //縮放中
    	Log.i("onScale", "startingSpan = " + startingSpan + " ; " + "startFocusX = " + startFocusX + " ; " + "startFocusY = " + startFocusY);
    	this.zoomableRelativeLayout.scale(detector.getCurrentSpan()/startingSpan, startFocusX, startFocusY); 
    	return true;
    }
    
    public void onScaleEnd(ScaleGestureDetector detector) { //縮放結束
    	Log.i("onScaleEnd", "執行onScaleEnd");
    	if(detector.getCurrentSpan()/startingSpan<1){
    		this.zoomableRelativeLayout.restore();
    	}
    }
}
