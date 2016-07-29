package io.fogcloud.fog2;

import android.content.Context;
import android.util.Log;

import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.fogcloud.easylink.api.EasyLink;
import io.fogcloud.easylink.helper.EasyLinkCallBack;
import io.fogcloud.easylink.helper.EasyLinkParams;
import io.fogcloud.fog2.utils.EasyLinkUtils;
import io.fogcloud.helper.CheckTool;
import io.fogcloud.helper.PaMap;

/**
 * Created by Sin on 2016/07/28.
 * Email:88635653@qq.com
 */
public class Fog extends WXModule {
    private Context mContext = null;
    private String instanceId = null;
    private EasyLinkUtils elu = null;

    private void initFog(){
        if(null == mContext){
            mContext = mWXSDKInstance.getContext();
        }
        if(null == instanceId) {
            instanceId = mWXSDKInstance.getInstanceId();
        }
    }

    /**
     * Let device connect to wifi router by EasyLink
     *
     * @param parameters  parameters from js
     * @param callbackId the id of js, and it will send callback message to this id
     */
    @WXModuleAnno
    public void startEasyLink(String parameters, String callbackId) {

        if (!CheckTool.checkPara(callbackId))
            return;

        initFog();

        if(CheckTool.checkPara(parameters)){
            if(null == elu)
                elu = new EasyLinkUtils(mContext, instanceId);
            elu.startEasyLink(parameters, callbackId);
        }else{
            exeCallBack(callbackId, PaMap.getEmptyMessage(), false);
        }
    }

    /**
     * Call back to js.
     * @param callbackId callback referenece handle
     * @param message callback data
     * @param keepAlive  if keep callback instance alive for later use
     */
    private void exeCallBack(String callbackId, Map<String, Object> message, boolean keepAlive) {
        WXBridgeManager.getInstance().callback(instanceId, callbackId, message, keepAlive);
    }
}
