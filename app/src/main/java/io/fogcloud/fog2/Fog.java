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
import io.fogcloud.helper.PaMap;

/**
 * Created by Sin on 2016/07/28.
 * Email:88635653@qq.com
 */
public class Fog extends WXModule {
    private Context mContext = null;
    private EasyLink el = null;
    private String callbackid = null;

    /**
     * Let device connect to wifi router by EasyLink
     * @param easylinkpara
     * @param callbackId
     */
    @WXModuleAnno
    public void startEasyLink(String easylinkpara,String callbackId) {

        mContext = mWXSDKInstance.getContext();
        el = new EasyLink(mWXSDKInstance.getContext());
        callbackid = callbackId;
        EasyLinkParams elpa = jsonToParams(easylinkpara);

        if (null != el && null != elpa) {
            el.startEasyLink(elpa, new EasyLinkCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    Log.d("fog ----- ", "code = " + code + " messgae" + message);

                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("code", code);
                    result.put("message", message);

                    if (1001 == code) {
                        exeCallBack(result, false);
                    } else {
                        exeCallBack(result, true);
                    }
                }

                @Override
                public void onFailure(int code, String message) {
                    Log.d("fog ----- ", "code = " + code + " messgae" + message);
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("code", code);
                    result.put("message", message);
                    exeCallBack(result, false);
                }
            });
        }
    }

    /**
     * Call back to js.
     * @param result
     * @param keepAlive
     */
    private void exeCallBack(Map<String, Object> result, boolean keepAlive) {
        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackid, result, keepAlive);
    }

    /**
     * Creates a new EasyLinkParams with name/value mappings from the JSON string.
     * @param json JSON string
     * @return EasyLinkParams
     */
    private EasyLinkParams jsonToParams(String json){
        try {
            JSONObject jjson = new JSONObject(json);
            Iterator it = jjson.keys();
            EasyLinkParams easylinkPara = new EasyLinkParams();
            String key;
            while (it.hasNext()){
                key = it.next().toString();
                switch (key){
                    case PaMap._EL_SSID:
                        easylinkPara.ssid = jjson.getString(key);
                        break;
                    case PaMap._EL_PSW:
                        easylinkPara.password = jjson.getString(key);
                        break;
                    case PaMap._EL_WORK:
                        easylinkPara.runSecond = jjson.getInt(key);
                        break;
                    case PaMap._EL_SLEEP:
                        easylinkPara.sleeptime = jjson.getInt(key);
                        break;
                    case PaMap._EL_EXTRA:
                        easylinkPara.extraData = jjson.getString(key);
                        break;
                    case PaMap._EL_RC4:
                        easylinkPara.rc4key = jjson.getString(key);
                        break;
                }
            }
            return easylinkPara;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
