v0.0.1
by Sin 2016-07-21

###环境搭建

1、生成weex版本的index.js文件，参考示例[传送门](https://github.com/bringmehome/fogweex)

2、将fogweex\weex_tmp\h5_render\index.js文件后缀名改为.mico，并上传到可以下载的网络路径，如"http://www.buyhezi.com/weex/index.mico"

3、clone本项目，替换UpdateWeexJs中的路径。运行后文件会自动保存到SD卡的weexsin目录下


###这里详细介绍android加载index.js的流程

1、打开项目时候先在myapplication里创建weexsin文件夹

2、然后去云端获取文件更新列表

3、如果有更新，那么启动下载流程UpdateWeexJs.doDownload();

4、下载完成后通过new File加载本地文件

```java
 File f = new File(Environment.getExternalStorageDirectory() + "/weexsin/index.js");
```

5、加载完成后通过ReadTxtFile读取文件内容，并mInstance.render出去就展示出来了


###加载在线文件时候

1、weex当然更能支持在线加载文件的功能，代码如下
```java
String WEEX_INDEX_URL = "http://www.buyhezi.com/weex/index.js";
mInstance.renderByUrl(
        TAG,
        WEEX_INDEX_URL,
        new HashMap<String, Object>(),
        null,
        ScreenUtil.getDisplayWidth(this),
        ScreenUtil.getDisplayHeight(this),
        WXRenderStrategy.APPEND_ASYNC);
 ```

###这里介绍封装自定义模块,我们采用倒叙

 1、MyApplication.java中可以看到注册模块的部分
 ```java
 try {
     WXSDKEngine.registerModule("myURL", URLHelperModule.class);
 } catch (WXException e) {
     WXLogUtils.e(e.getMessage());
 }
```

2、URLHelperModule模块extends WXModule,然后完成方法openURL
```java
public void openURL(String url, String callbackId) {
    //...
    //callback to javascript
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("ts", url);
    result.put("sin", "i m sin.");
    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId, result);
}
```

3、js调用时候先require下
```js
var URLHelper = require('@weex-module/myURL');//same as you registered
URLHelper.openURL("http://www.taobao.com",function(ts){
    console.log("url is open at ----- >>>>>  "+ts);
});
```

4、so easy, 具体见[Extend to Android](http://alibaba.github.io/weex/doc/advanced/extend-to-android.html)