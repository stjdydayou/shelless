package com.axungu.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jtoms
 */
public class PatternUtils {


    /**
     * Email正则表达式
     */
    public static final String EMAIL_EXPRESSION = "^([_A-Za-z0-9-])+@(([A-Za-z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+";

    /**
     * 中国大陆手机号
     */
    public static final String MOBILE_EXPRESSION = "^1[3|4|5|6|7|8][0-9]{9}";

    /**
     * 域名
     * DNS规定，域名中的标号都由英文字母和数字组成，每一个标号不超过63个字符，也不区分大小写字母。
     * 标号中除连字符（-）外不能使用其他的标点符号。级别最低的域名写在最左边，而级别最高的域名写在最右边。
     * 由多个标号组成的完整域名总共不超过255个字符。
     */
    public static final String DOMAIN_NAME_EXPRESSION = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";

    /**
     * 强密码正则表达式
     */
    public static final String STRONG_PASSWORD_EXPRESSION = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$";

    /**
     * IPV4正则表达式
     */
    public static final String IPV4_EXPRESSION = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";


    private static final String IMAGE_SRC_REGEX = "<img.+?src=\"(.+?)\".+?/?>";


    /**
     * 正则表达试的验证
     *
     * @param value
     * @param expression
     * @return
     */
    public static boolean validExpression(String value, String expression) {
        return value.matches(expression);
    }


    public static List<String> getImgUrlFromHtml(String htmlStr) {
        Matcher matcher = Pattern.compile(IMAGE_SRC_REGEX).matcher(htmlStr);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group(1));
        }
        return listImgUrl;
    }
}
