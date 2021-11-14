package com.example;

import com.example.analysis.FileAnalysis;
import com.example.config.JwtFilter;
import com.example.dao.ModelDao;
import com.example.dao.UserDao;
import com.example.data.CsvAdapter;
import com.example.data.LibsvmAdapter;
import com.example.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final UserDao dao;
    private final CsvAdapter csvAdapter;
    private final LibsvmAdapter libsvmAdapter;

    @Autowired
    private            LoginService  loginService;
    @Autowired private ModelDao modelDao;
    @Autowired private FileAnalysis fileAnalysis;
    public Application(UserDao repository, CsvAdapter csvAdapter, LibsvmAdapter libsvmAdapter) {
        this.dao = repository;
        this.csvAdapter = csvAdapter;
        this.libsvmAdapter = libsvmAdapter;
    }
    //@Bean
    //public FilterRegistrationBean jwtFilter(){      //改变默认的url拦截器，对api开头的api进行过滤，查看是否有合法token
    //    final FilterRegistrationBean registrationBean=new FilterRegistrationBean();
    //    registrationBean.setFilter(new JwtFilter());
    //    registrationBean.addUrlPatterns("/api/*");
    //
    //    return registrationBean;
    //}
    public static void main(String[] args) {
    try {
        SpringApplication.run(Application.class, args);
    }catch (Exception e){
        e.printStackTrace();
    }
    }

    @Override
    public void run(String... args) throws Exception {

    }
    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 是否允许请求带有验证信息
        config.setAllowCredentials(true);

        // 允许访问的客户端域名
        // (springboot2.4以上的加入这一段可解决 allowedOrigins cannot contain the special value "*"问题)
        List<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("*");
        config.setAllowedOriginPatterns(allowedOriginPatterns);

        // 设置访问源地址
        // config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}