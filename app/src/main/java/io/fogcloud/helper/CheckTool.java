package io.fogcloud.helper;

/**
 * Created by Sin on 2016/07/29.
 * Email:88635653@qq.com
 */
public class CheckTool {
    /**
     * Check argument, whether it is null or blank
     *
     * @param param the parameters you want to check
     * @return true or false
     */
    public static boolean checkPara(String... param) {
        if (null == param || param.equals("")) {
            return false;
        } else if (param.length > 0) {
            for (String str : param) {
                if (null == str || str.equals("")) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
