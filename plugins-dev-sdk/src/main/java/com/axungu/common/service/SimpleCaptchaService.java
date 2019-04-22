package com.axungu.common.service;

import java.awt.image.BufferedImage;


/**
 * @author jtoms
 */
public interface SimpleCaptchaService {
    /**
     * 生成一个图片验证码
     *
     * @param token
     * @return BufferedImage
     */
    BufferedImage generateImage(String token);

    /**
     * 生成一个图片验证码
     *
     * @param token
     * @return base64的字符串
     */
    String generateCaptcha(String token);

    /**
     * 比较验证码
     *
     * @param captcha
     * @param token
     * @param destroySession
     * @return
     */
    boolean compareCaptcha(String captcha, String token, boolean destroySession);
}
