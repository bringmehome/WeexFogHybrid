package io.fogcloud.fog2.utils;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.bridge.WXBridgeManager;

import java.util.HashMap;
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

    /**
     * Initialize EasyLink.
     *
     * @param context Interface to global information about an application environment.
     * @param instanceId id of instance
     */
    public EasyLinkUtils(Context context, String instanceId) {
        this.el = new EasyLink(context);
        this.instanceId = instanceId;
    }

    /**
     * Get SSID, current name of wifi in android.
     *
     * @param callbackId callback referenece handle
     */
    public void getSsid(final String callbackId){
        exeCallBack(callbackId, getResult(PaMap._EL_SUCCESS, el.getSSID()), false);
    }

    /**
     * Start EasyLink, it means we will send ssid and password to the device.
     *
     * @param easylinkpara easylink data
     * @param callbackId   callback referenece handle
     */
    public void startEasyLink(JSONObject easylinkpara, final String callbackId) {
        EasyLinkParams elpa = jsonToParams(easylinkpara);

        if (null != el && null != elpa) {
            el.startEasyLink(elpa, new EasyLinkCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    if (PaMap._EL_STOP == code) {
                        exeCallBack(callbackId, getResult(code, message), false);
                    } else {
                        exeCallBack(callbackId, getResult(code, message), true);
                    }
                }

                @Override
                public void onFailure(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }
            });
        }
    }

    /**
     * Stop send data to device.
     *
     * @param callbackId callback referenece handle
     */
    public void stopEasyLink(final String callbackId) {
        if (null != el) {
            el.stopEasyLink(new EasyLinkCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }

                @Override
                public void onFailure(int code, String message) {
                    exeCallBack(callbackId, getResult(code, message), false);
                }
            });
        }
    }

    /**
     * Creates a new EasyLinkParams with name/value mappings from the JSON string.
     *
     * @param json JSON string
     * @return EasyLinkParams
     */
    private EasyLinkParams jsonToParams(JSONObject json) {
        EasyLinkParams easylinkPara = new EasyLinkParams();

        if (json.containsKey(PaMap._EL_SSID))
            easylinkPara.ssid = json.getString(PaMap._EL_SSID);
        if (json.containsKey(PaMap._EL_PSW))
            easylinkPara.password = json.getString(PaMap._EL_PSW);
        if (json.containsKey(PaMap._EL_WORK))
            easylinkPara.runSecond = json.getInteger(PaMap._EL_WORK);
        if (json.containsKey(PaMap._EL_SLEEP))
            easylinkPara.sleeptime = json.getInteger(PaMap._EL_SLEEP);
        if (json.containsKey(PaMap._EL_EXTRA))
            easylinkPara.extraData = json.getString(PaMap._EL_EXTRA);
        if (json.containsKey(PaMap._EL_RC4))
            easylinkPara.rc4key = json.getString(PaMap._EL_RC4);

            return easylinkPara;
    }

    /**
     * It is old function, it use JSONObject of Android.
     */
//    private EasyLinkParams jsonToParams(String json) {
//        try {
//            JSONObject jjson = new JSONObject(json);
//            Iterator it = jjson.keys();
//            EasyLinkParams easylinkPara = new EasyLinkParams();
//            String key;
//            while (it.hasNext()) {
//                key = it.next().toString();
//                switch (key) {
//                    case PaMap._EL_SSID:
//                        easylinkPara.ssid = jjson.getString(key);
//                        break;
//                    case PaMap._EL_PSW:
//                        easylinkPara.password = jjson.getString(key);
//                        break;
//                    case PaMap._EL_WORK:
//                        easylinkPara.runSecond = jjson.getInt(key);
//                        break;
//                    case PaMap._EL_SLEEP:
//                        easylinkPara.sleeptime = jjson.getInt(key);
//                        break;
//                    case PaMap._EL_EXTRA:
//                        easylinkPara.extraData = jjson.getString(key);
//                        break;
//                    case PaMap._EL_RC4:
//                        easylinkPara.rc4key = jjson.getString(key);
//                        break;
//                }
//            }
//            return easylinkPara;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     * Return the Map from code and message.
     *
     * @param code    code of onSuccess/onFailure
     * @param message message of onSuccess/onFailure
     * @return String map
     */
    private Map<String, Object> getResult(int code, String message) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("message", message);
        return result;
    }

    /**
     * Call back to js.
     *
     * @param callbackId callback referenece handle
     * @param message    callback data
     * @param keepAlive  if keep callback instance alive for later use
     */
    private void exeCallBack(String callbackId, Map<String, Object> message, boolean keepAlive) {
        WXBridgeManager.getInstance().callback(instanceId, callbackId, message, keepAlive);
    }
}


