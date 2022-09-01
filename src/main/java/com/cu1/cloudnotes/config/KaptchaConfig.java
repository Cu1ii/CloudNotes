package com.cu1.cloudnotes.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    //返回默认的 kaptcha 配置类
    @Bean
    public Producer kaptchaProducer() {

        Properties properties = new Properties();

        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        //字符范围 输入字符串作为范围
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        // 设置验证码的字符数
        properties.setProperty("kaptcha.textproducer.char.length", "4");

        //要使用哪一个干扰验证码的类
        properties.setProperty("kaptcha.textproducer.noise.impl", "com.google.code.kaptcha.impl.NoNoise");

        Config config = new Config(properties);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }

}