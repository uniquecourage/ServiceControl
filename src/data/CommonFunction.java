package data;

import java.util.List;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class CommonFunction {

	/**
	 * 判斷程式的運行是在前台還後台
	 * 
	 * @param context
	 * @return 0在後台運行  大於0在前台運行  2表示當前主介面是某個指定的Activity
	 */
	public static int isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = "garrick.example.servicecontrol";

		
		String bingMapMainActivityClassName = "garrick.example.servicecontrol.StopServiceActivity";
		List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1); //getRunningTasks在API21以上deprecated
		//List<AppTask> AppTasksInfo = getAppTasks(); //getRunningTasks在API21以上的替代方向參考到的資料 
		if (tasksInfo.size() > 0) {
			ComponentName topConponent = tasksInfo.get(0).topActivity;
			Log.i("TAG",
					"topConponent.getPackageName()..."
							+ topConponent.getPackageName());
			if (packageName.equals(topConponent.getPackageName())) {
				// 當前的App在前台運行
				if (topConponent.getClassName().equals(
						bingMapMainActivityClassName)) {
					// 當前正在運行的是不是指定的Activity
					Log.d("TAG", "指定的Activity在運行");
					return 2;
				}
				Log.i("TAG", "garrick.example.servicecontrol前台運行");
				return 1;
			} else {
				// 當前的App在後台運行
				Log.i("TAG", "garrick.example.servicecontrol後台運行");
				return 0;
			}
		}
		return 0;
	}

	//計算圖片的縮放值
		public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
		    final int height = options.outHeight;
		    final int width = options.outWidth;
		    int inSampleSize = 1;

		    if (height > reqHeight || width > reqWidth) {
		             final int heightRatio = Math.round((float) height/ (float) reqHeight);
		             final int widthRatio = Math.round((float) width / (float) reqWidth);
		             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		    }
		        return inSampleSize;
		}

		//根據路徑獲得圖片並壓縮
		public static Bitmap getSmallBitmap(String filePath) {
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(filePath, options);

	        // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, 480, 800);

	        // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;

	    return BitmapFactory.decodeFile(filePath, options);
	    }
}
