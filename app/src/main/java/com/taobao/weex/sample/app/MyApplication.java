package com.taobao.weex.sample.app;

import android.app.Application;
import android.os.Environment;

import com.sin.ui.component.URLHelperModule;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.utils.WXLogUtils;

import java.io.File;

/**
 * Created by sospartan on 6/7/16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WXSDKEngine.init(this, null, null, new MyImageAdapter(), null);//设置自定义的adadpter实现图片显示、http请求等能力

        String filePath = "";
        String _FILE_ROOT_PATH = "";
        if (externalMemoryAvailable()) {
            filePath = Environment.getExternalStorageDirectory() + "/weexdownload/";
            _FILE_ROOT_PATH = Environment.getExternalStorageDirectory() + "";
        } else {
            filePath = Environment.getDataDirectory() + "/weexdownload/";
            _FILE_ROOT_PATH = Environment.getDataDirectory() + "";
        }

        if (checkPara(filePath)) {
            File f = new File(filePath);
            if (!f.exists()) {
                if (!f.mkdir()) {
                    f.mkdirs();
                }
            }
        }

        try {
            WXSDKEngine.registerModule("myURL", URLHelperModule.class);//'myURL' is the name you'll use in javascript
        } catch (WXException e) {
            WXLogUtils.e(e.getMessage());
        }
    }

    /**
     * 手机是否存在SD卡
     *
     * @return
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static boolean checkPara(String... param) {
        if (null == param || param.equals("")) {
            return false;
        } else if (param.length > 0) {
            for (String str : param) {
                if (null == str || str.equals("") || str.equals("null") || str.equals("undefined")) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
