package com.io.fogcloud.weex.fog2;

import android.content.Context;
import android.util.Log;

import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

import java.util.HashMap;
import java.util.Map;

import io.fogcloud.easylink.api.EasyLink;
import io.fogcloud.easylink.helper.EasyLinkCallBack;
import io.fogcloud.easylink.helper.EasyLinkParams;

/**
 * Created by Sin on 2016/07/28.
 * Email:88635653@qq.com
 */
public class Fog extends WXModule {
    private Context mContext = null;
    private EasyLink el;
    private String callbackid;

    private void initFog(){
            mContext = mWXSDKInstance.getContext();
    }

    @WXModuleAnno
    public void startEasyLink(String ssid,
                                String password,
                                int runSecond,
                                int sleeptime,
                                String extraData,
                                String rc4key,
                                String callbackId){

            mContext = mWXSDKInstance.getContext();
            el = new EasyLink(mWXSDKInstance.getContext());
            callbackid = callbackId;

            EasyLinkParams easylinkPara = new EasyLinkParams();
            easylinkPara.ssid = ssid;
            easylinkPara.password = password;
            easylinkPara.runSecond = runSecond;
            easylinkPara.sleeptime = sleeptime;
            easylinkPara.extraData = extraData;
            easylinkPara.rc4key = rc4key;

            el.startEasyLink(easylinkPara, new EasyLinkCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    Log.d("fog ----- ", "code = "+ code +" messgae" + message);

                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("code", code);
                    result.put("message", message);
                    exeCallBack(result);
                }

                @Override
                public void onFailure(int code, String message) {
                    Log.d("fog ----- ", "code = "+ code +" messgae" + message);
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("code", code);
                    result.put("message", message);
                    exeCallBack(result);
                }
            });
    }

    private void exeCallBack(Map<String, Object> result){
        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackid, result, true);
    }
}
