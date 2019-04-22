package com.axungu.platform.core.patchca;


import com.axungu.common.service.SimpleCaptchaService;
import com.axungu.common.Token;
import com.axungu.platform.core.patchca.color.SingleColorFactory;
import com.axungu.platform.core.patchca.filter.predefined.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

@Slf4j
@Component
public class SimpleCaptchaServiceImpl implements SimpleCaptchaService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public BufferedImage generateImage(String token) {
        try {
            ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
            cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
            Random random = new Random();
            switch (random.nextInt(4)) {
                case 0:
                    cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
                    break;
                case 1:
                    cs.setFilterFactory(new MarbleRippleFilterFactory());
                    break;
                case 2:
                    cs.setFilterFactory(new DoubleRippleFilterFactory());
                    break;
                case 3:
                    cs.setFilterFactory(new WobbleRippleFilterFactory());
                    break;
                case 4:
                    cs.setFilterFactory(new DiffuseRippleFilterFactory());
                    break;
                default:
                    break;

            }

            Captcha captcha = cs.getCaptcha();
            if (Token.checkToken(token)) {
                Cache<String, String> cache = cacheManager.getCache("captchaSessionCache", String.class, String.class);
                cache.put(token, captcha.getChallenge());
            } else {
                log.warn("生成验证码的 Token 无效");
            }
            return captcha.getImage();
        } catch (Exception e) {
            // TODO: handle exception
            log.error("error", e);
            return null;
        }
    }

    /**
     * 产生一个验证码图片二进制流
     */
    @Override
    public String generateCaptcha(String token) {
        try {
            BufferedImage image = generateImage(token);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            Base64 b64 = new Base64();
            String data = b64.encodeToString(baos.toByteArray());
            data = "data:image/png;base64," + data;
            return data;
        } catch (Exception e) {
            log.error("convert image to byte error: ", e);
        }
        return null;
    }

    /**
     * 比较验证码
     *
     * @param captcha
     * @param token
     * @param destroySession
     */
    @Override
    public boolean compareCaptcha(String captcha, String token, boolean destroySession) {
        Cache<String, String> cache = cacheManager.getCache("captchaSessionCache", String.class, String.class);
        String extistCaptcha = cache.get(token);
        if (destroySession) {
            cache.remove(token);
        }
        log.debug("SESSION captcha " + extistCaptcha);
        log.debug("INPUT captcha " + captcha);
        return StringUtils.equalsIgnoreCase(extistCaptcha, captcha);
    }
}
