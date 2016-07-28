package com.sin.ui.component;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sin on 2016/07/15.
 * Email:88635653@qq.com
 */
public class URLHelperModule extends WXModule {
    private static final String WEEX_CATEGORY = "com.taobao.android.intent.category.WEEX";

    @WXModuleAnno
    public void openURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        StringBuilder builder = new StringBuilder("http:");
        builder.append(url);
        Uri uri = Uri.parse(builder.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(WEEX_CATEGORY);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    @WXModuleAnno
    /**
     * 前端调用此接口，不过你得先在application里注册下
     * 前端调用时候需要require
     * var URLHelper = require('@weex-module/myURL');//same as you registered
     * URLHelper.openURL("http://www.taobao.com",function(ts){
     * console.log("url is open at ----- >>>>>  "+ts);
     * });
     */
    public void openURL(String url, String callbackId) {
        //...
        //callback to javascript
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("ts", url);
        result.put("sin", "I m SIN.");
        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId, result);
    }
}
