package data;

import java.util.ArrayList;
import java.util.List;
import android.app.Application;




//繼承Application的全域變數儲存方式
//****************************
//一般AndroidManifest中有一個application標籤，原本沒有android:name屬性
//，將第一步驟建立的類別檔加入，例：android:name=".GlobalSave"
//將值放入與將值讀出
//放入
//GlobalSave globalSave = (GlobalSave)getApplicationContext();
//globalSave.UserID = key_UserID;

//讀出
//GlobalSave globalSave = (GlobalSave)getApplicationContext();
//String UserID = globalSave.UserID;
//****************************
public class GlobalSave extends Application {

	public List<IconCoordinate> _IconCoordinate = new ArrayList<IconCoordinate>();
}
