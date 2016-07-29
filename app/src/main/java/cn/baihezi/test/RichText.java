//package cn.baihezi.test;
//
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.method.LinkMovementMethod;
//import android.text.style.URLSpan;
//import android.widget.TextView;
//
//import com.taobao.weex.WXSDKInstance;
//import com.taobao.weex.dom.WXDomObject;
//import com.taobao.weex.ui.component.WXComponent;
//import com.taobao.weex.ui.component.WXComponentProp;
//import com.taobao.weex.ui.component.WXVContainer;
//
///**
// * Created by Sin on 2016/07/15.
// * Email:88635653@qq.com
// */
//public class RichText extends WXComponent {
//
//    public RichText(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, String instanceId, boolean isLazy) {
//        super(instance, dom, parent, instanceId, isLazy);
//    }
//
//    @Override
//    protected void initView() {
//        mHost = new TextView(mContext);
//        ((TextView) mHost).setMovementMethod(LinkMovementMethod.getInstance());
//    }
//
//    @WXComponentProp(name = "tel")
//    public void setTelLink(String tel) {
//        SpannableString spannable = new SpannableString(tel);
//        spannable.setSpan(new URLSpan("tel:" + tel), 0, tel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ((TextView) mHost).setText(spannable);
//    }
//}
