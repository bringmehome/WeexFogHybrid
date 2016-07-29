package io.fogcloud.fog2.utils;

import android.content.Context;

import com.taobao.weex.bridge.WXBridgeManager;

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
 * Created by Sin on 2016/07/29.
 * Email:88635653@qq.com
 */
public class EasyLinkUtils {
    private EasyLink el = null;
    private String instanceId;

    public EasyLinkUtils(Context context, String instanceId){
        this.el = new EasyLink(context);
        this.instanceId = instanceId;
    }

    public void startEasyLink(String easylinkpara, final String callbackId){
        EasyLinkParams elpa = jsonToParams(easylinkpara);

        if (null != el && null != elpa) {
            el.startEasyLink(elpa, new EasyLinkCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("code", code);
                    result.put("message", message);

                    if (1001 == code) {
                        exeCallBack(callbackId, result, false);
                    } else {
                        exeCallBack(callbackId, result, true);
                    }
                }

                @Override
                public void onFailure(int code, String message) {
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("code", code);
                    result.put("message", message);
                    exeCallBack(callbackId, result, false);
                }
            });
        }
    }

    /**
     *  Call back to js.
     * @param callbackId callback referenece handle
     * @param message callback data
     * @param keepAlive  if keep callback instance alive for later use
     */
    private void exeCallBack(String callbackId, Map<String, Object> message, boolean keepAlive) {
        WXBridgeManager.getInstance().callback(instanceId, callbackId, message, keepAlive);
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
