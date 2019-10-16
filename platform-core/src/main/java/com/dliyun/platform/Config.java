package com.dliyun.platform;


import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.dliyun.platform.common.ServletContext;
import com.dliyun.platform.common.freemarker.FreemarkerComponent;
import com.dliyun.platform.common.plugin.Plugin;
import com.dliyun.platform.common.plugin.PluginModuleInfo;
import com.dliyun.platform.common.plugin.RegisterPlugin;
import com.dliyun.platform.common.plugin.UpgradeSqlInfo;
import com.dliyun.platform.common.utils.DateUtil;
import com.dliyun.platform.common.utils.HostInfoUtil;
import com.dliyun.platform.core.model.SystemOauthUserBaseInfo;
import com.dliyun.platform.core.model.SystemOauthUserLoginAccount;
import com.dliyun.platform.core.oauth.OauthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 系统启动配置
 *
 * @author jtoms
 */
@Slf4j
@Configuration
@EnableCaching
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.dliyun", "**.plugin"})
@MapperScan(basePackages = "com.dliyun.platform.core.mappers")
public class Config implements WebMvcConfigurer, ApplicationContextAware {

    public static final String VERSION = "__version__";

    private static final String HOST_INFO = "__host_info__";

    private static final String ACCESS_TOKEN = "__access_token__";

    private static String runVersion;

    static {
        runVersion = DateUtil.format("yyyyMMddHHmmss");
    }

    @Value("${isdebug}")
    private boolean isdebug;

    @Value("${thread.pool.corePoolSize}")
    private Integer corePoolSize;

    @Value("${thread.pool.keepAliveSeconds}")
    private Integer keepAliveSeconds;

    @Autowired
    private OauthInterceptor oauthInterceptor;

    private ApplicationContext applicationContext;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void registerPlugins() throws Exception {

        //注册用户插件
        Map<String, Object> pluginsMap = applicationContext.getBeansWithAnnotation(Plugin.class);
        for (String pluginName : pluginsMap.keySet()) {
            Object o = pluginsMap.get(pluginName);
            if (o instanceof RegisterPlugin) {
                Plugin pluginAnnotation = AnnotationUtils.findAnnotation(o.getClass(), Plugin.class);
                if (pluginAnnotation != null) {
                    RegisterPlugin registerPlugin = ((RegisterPlugin) o);

                    List<PluginModuleInfo> listModules = registerPlugin.registerModule();

                    PluginInfo pluginInfo = new PluginInfo();
                    pluginInfo.setKey(pluginAnnotation.key());
                    pluginInfo.setFaicon(pluginAnnotation.faicon());
                    pluginInfo.setName(pluginAnnotation.name());
                    pluginInfo.addModules(listModules);

                    if (PluginInfo.REGISTERED_PLUGINS.containsKey(pluginInfo.getKey())) {
                        log.warn(String.format("注册插件[%s,%s]失败，插件{PluginInfo.key}在系统中必需唯一", pluginAnnotation.name(), pluginName));
                        continue;
                    }
                    PluginInfo.REGISTERED_PLUGINS.put(pluginAnnotation.key(), pluginInfo);

                    log.info(String.format("注册插件[%s,%s]成功", pluginAnnotation.name(), pluginName));

                    List<UpgradeSqlInfo> listUpgradeSqls = registerPlugin.getListUpgradeSqls();
                    if (listUpgradeSqls != null && listUpgradeSqls.size() > 0) {
                        for (UpgradeSqlInfo upgradeSqlInfo : listUpgradeSqls) {
                            log.info(String.format("执行[%s,%s,%s]升级SQL", pluginAnnotation.name(), pluginName, upgradeSqlInfo.getFileName()));
                            try {
                                for (String sql : upgradeSqlInfo.getSqls()) {
                                    jdbcTemplate.execute(sql);
                                    log.info(String.format("执行[ %s ]成功", sql));
                                }
                            } catch (Exception e) {
                                log.error("SQL 执行失败", e);
                            }
                        }
                    }
                }

            } else {
                log.warn(String.format("%s 插件注册失败，@Plugin注解必要实现com.dliyun.platform.common.plugin.RegisterPlugin", pluginName));
            }
        }


        //注册Freemark自定义方法
        Map<String, Object> freemarkersMap = applicationContext.getBeansWithAnnotation(FreemarkerComponent.class);
        freemarker.template.Configuration configuration = this.freeMarkerConfigurer.getConfiguration();
        for (String key : freemarkersMap.keySet()) {
            configuration.setSharedVariable(key, freemarkersMap.get(key));
        }
        this.freeMarkerConfigurer.setConfiguration(configuration);

    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                ServletContext.init(request, response);
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                if (modelAndView != null) {
                    if (isdebug) {
                        runVersion = DateUtil.format("yyyyMMddHHmmss");
                    }

                    request.setAttribute(VERSION, runVersion);

                    request.setAttribute(ACCESS_TOKEN, ServletContext.getAccessToken());
                    request.setAttribute(HOST_INFO, new HostInfoUtil().toString());
                }
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                ServletContext.clean();
            }
        }).addPathPatterns("/**");
        registry.addInterceptor(oauthInterceptor).addPathPatterns("/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.valueOf("application/json;charset=UTF-8"));
        supportedMediaTypes.add(MediaType.valueOf("application/*+json;charset=UTF-8"));
        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        FastJsonConfig config = new FastJsonConfig();

        SerializeConfig serializeConfig = new SerializeConfig();

        Class[] enumClasses = new Class[]{SystemOauthUserLoginAccount.AccountType.class, SystemOauthUserBaseInfo.Gender.class};

        serializeConfig.configEnumAsJavaBean(enumClasses);

        config.setSerializeConfig(serializeConfig);
        config.setSerializerFeatures(SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteEnumUsingToString,
                /*SerializerFeature.WriteMapNullValue,*/
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect);
        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        converters.add(fastJsonHttpMessageConverter);
    }
}

