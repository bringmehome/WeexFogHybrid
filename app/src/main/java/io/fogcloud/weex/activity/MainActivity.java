package io.fogcloud.weex.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.sample.app.R;
import com.taobao.weex.app.ScreenUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import io.fogcloud.helper.download.UpdateWeexJs;

public class MainActivity extends AppCompatActivity implements IWXRenderListener {
    public static final String TAG = "MainActivity";
    WXSDKInstance mInstance;
    ViewGroup mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT) {
//			WebView.setWebContentsDebuggingEnabled(true);
//		}
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();

        mContainer = (ViewGroup) findViewById(R.id.container);
        mInstance = new WXSDKInstance(this); //create weex instance
        mInstance.registerRenderListener(this); //SimpleRenderListener需要开发者来实现

        //sample直接使用Weex命令转化后的js代码,也可以从文件加载或者从服务端下载的方式
//        mInstance.render(TAG,
//                "define(\"@weex-component/localtest\",function(t,e,o){o.exports={data:function(){return{img:\"//gw.alicdn.com/tps/i2/TB1DpsmMpXXXXabaXXX20ySQVXX-512-512.png_400x400.jpg\"}}},o.exports.style={title:{fontSize:20,color:\"#0000FF\"},img:{width:200,height:100}},o.exports.template={type:\"scroller\",children:[{type:\"text\",classList:[\"title\"],attr:{value:\"Hello,Weex!\"}},{type:\"image\",classList:[\"img\"],attr:{src:function(){return this.img}}}]}}),bootstrap(\"@weex-component/localtest\",{transformerVersion:\"0.1.8\"});",
//                new HashMap<String, Object>(),
//                null,
//                ScreenUtil.getDisplayWidth(this),
//                ScreenUtil.getDisplayHeight(this),
//                WXRenderStrategy.APPEND_ASYNC);

        //加载网络地址的js
//        String WEEX_INDEX_URL = "http://www.buyhezi.com/weex/index.js";
        String WEEX_INDEX_URL = "http://192.168.3.199:8081/weex_tmp/h5_render/index.js";
        mInstance.renderByUrl(
                TAG,
                WEEX_INDEX_URL,
                new HashMap<String, Object>(),
                null,
                ScreenUtil.getDisplayWidth(this),
                ScreenUtil.getDisplayHeight(this),
                WXRenderStrategy.APPEND_ASYNC);

//        //加载本地的js
//        File f = new File(Environment.getExternalStorageDirectory() + "/weexsin/index.js");
//        mInstance.render(TAG,
//                ReadTxtFile(f.toString()),
//                new HashMap<String, Object>(),
//                null,
//                ScreenUtil.getDisplayWidth(this),
//                ScreenUtil.getDisplayHeight(this),
//                WXRenderStrategy.APPEND_ASYNC);
//
////        下载文件
//        UpdateWeexJs uweexjs = new UpdateWeexJs();
//        uweexjs.doDownload();
    }

    //读取文本文件中的内容
    public static String ReadTxtFile(String strFilePath) {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        Log.d("---TestFile---", content);
        return content;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mInstance != null) {
            mInstance.onActivityStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mInstance != null) {
            mInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mInstance != null) {
            mInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mInstance != null) {
            mInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mInstance != null) {
            mInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance wxsdkInstance, View view) {
        if (mContainer != null) {
            mContainer.addView(view);
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {

    }

    @Override
    public void onException(WXSDKInstance wxsdkInstance, String s, String s1) {

    }
}
