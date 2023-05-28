package com.example.StockDiplom.config;

import com.example.StockDiplom.models.MemberKey;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new MemberKeyConverter());
    }

    public class MemberKeyConverter implements Converter<String, MemberKey> {
        @Override
        public MemberKey convert(String source) {
            String[] parts = source.split("_");
            Long userId = Long.valueOf(parts[0]);
            Long groupId = Long.valueOf(parts[1]);
            return new MemberKey(userId, groupId);
        }
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
