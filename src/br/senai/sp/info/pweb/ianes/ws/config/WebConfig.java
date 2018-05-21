package br.senai.sp.info.pweb.ianes.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getCorsInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public CorsInterceptor getCorsInterceptor() {
        return new CorsInterceptor();
    }

}
