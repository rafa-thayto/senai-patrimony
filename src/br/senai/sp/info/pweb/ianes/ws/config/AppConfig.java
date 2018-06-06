package br.senai.sp.info.pweb.ianes.ws.config;

import br.senai.sp.info.pweb.ianes.ws.interceptors.AutenticacaoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import(value = { PersistenceConfig.class })
@EnableWebMvc
@ComponentScan("br.senai.sp.info.pweb.ianes.ws")
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public AutenticacaoInterceptor getAutenticacaoInterceptor() {
        return new AutenticacaoInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(getAutenticacaoInterceptor())
                .addPathPatterns("/**");
    }

}
