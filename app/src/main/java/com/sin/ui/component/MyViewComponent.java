package com.sin.ui.component;

import android.widget.TextView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXDomPropConstant;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.view.WXFrameLayout;

/**
 * Created by Sin on 2016/07/15.
 * Email:88635653@qq.com
 */
public class MyViewComponent extends WXComponent {
    public MyViewComponent(WXSDKInstance instance, WXDomObject node,
                           WXVContainer parent, String instanceId, boolean lazy) {
        super(instance, node, parent, instanceId, lazy);
    }

    @Override
    protected void initView() {
        //TODO:your own code ……
    }

    @Override
    public WXFrameLayout getView() {
        //TODO:your own code ………

        return null;
    }

    @WXComponentProp(name = WXDomPropConstant.WX_ATTR_VALUE)
    public void setMyViewValue(String value) {
        ((TextView) mHost).setText(value);
    }
}
