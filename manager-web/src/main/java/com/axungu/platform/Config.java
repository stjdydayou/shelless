package com.axungu.platform;


import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.axungu.common.ServletContext;
import com.axungu.common.freemarker.FreemarkerComponent;
import com.axungu.common.plugin.*;
import com.axungu.common.utils.DateUtil;
import com.axungu.common.utils.HostInfoUtil;
import com.axungu.platform.core.oauth.OauthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.axungu"})
@MapperScan(basePackages = "com.axungu.platform.core.mappers")
public class Config implements WebMvcConfigurer, ApplicationContextAware {


    public static final String HOST_INFO = "__host_info__";

    public static final String ACCESS_TOKEN = "__access_token__";

    public static final String VERSION = "__version__";

    public static final String URL = "__url__";

    private static String runVersion = "000000";

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void registerPlugins() throws Exception {
        //注册系统配置模块
        this.registerSystemPlugins();

        //注册用户插件
        Map<String, Object> pluginsMap = applicationContext.getBeansWithAnnotation(Plugin.class);
        for (String pluginName : pluginsMap.keySet()) {
            Object o = pluginsMap.get(pluginName);
            if (o instanceof RegisterPlugin) {
                Plugin pluginAnnotation = AnnotationUtils.findAnnotation(o.getClass(), Plugin.class);
                if (pluginAnnotation != null) {
                    List<PluginModuleInfo> listModules = ((RegisterPlugin) o).registerModule();

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
                }

            } else {
                log.warn(String.format("%s 插件注册失败，@Plugin注解必要实现com.axungu.common.plugin.RegisterPlugin", pluginName));
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

    private void registerSystemPlugins() {


        PluginModuleInfo userModuleInfo = new PluginModuleInfo("oauth", "用户管理", "users");

        //注册菜单
        userModuleInfo.add(new PluginMenu("user", "用户管理", "/system/oauth/user/index.htm"));
        userModuleInfo.add(new PluginMenu("role", "角色管理", "/system/oauth/role/index.htm"));

        //注册权限
        userModuleInfo.add(new PluginAuthority("user.find", "查询用户"));
        userModuleInfo.add(new PluginAuthority("user.add", "添加用户"));
        userModuleInfo.add(new PluginAuthority("user.enable", "启用用户"));
        userModuleInfo.add(new PluginAuthority("user.disable", "禁用用户"));
        userModuleInfo.add(new PluginAuthority("user.resetPassword", "重置用户密码"));
        userModuleInfo.add(new PluginAuthority("user.roles", "设置用户角色"));
        userModuleInfo.add(new PluginAuthority("role.find", "查询角色"));
        userModuleInfo.add(new PluginAuthority("role.add", "添加角色"));
        userModuleInfo.add(new PluginAuthority("role.edit", "编辑角色"));
        userModuleInfo.add(new PluginAuthority("role.delete", "删除角色"));
        userModuleInfo.add(new PluginAuthority("role.authority", "设置角色权限"));


        /*------------------*/
        PluginModuleInfo settingModuleInfo = new PluginModuleInfo("setting", "系统设置", "cog");

        //注册菜单
        settingModuleInfo.add(new PluginMenu("config", "参数配置", "/system/config/index.htm"));

        //注册权限
        settingModuleInfo.add(new PluginAuthority("config.find", "查询系统参数"));
        settingModuleInfo.add(new PluginAuthority("config.edit", "编辑系统参数"));


        PluginInfo pluginInfo = new PluginInfo();
        pluginInfo.setKey("system");
        pluginInfo.setFaicon("cogs");
        pluginInfo.setName("系统设置");
        pluginInfo.addModule(userModuleInfo).addModule(settingModuleInfo);
        PluginInfo.REGISTERED_PLUGINS.put(pluginInfo.getKey(), pluginInfo);

    }

    @Bean
    public CacheManager buildCacheManager() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(new XmlConfiguration(getClass().getResource("/ehcache.xml")));
        cacheManager.init();
        return cacheManager;
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
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
                    String url = request.getRequestURL() + (StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "");
                    request.setAttribute(URL, url);

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
//        serializeConfig.configEnumAsJavaBean(AuthType.class, YN.class);

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

