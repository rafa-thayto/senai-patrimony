package br.senai.sp.info.pweb.ianes.ws.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("br.senai.sp.info.pweb.ianes.ws")
@Import(value = { PersistenceConfig.class, WebConfig.class })
public class AppConfig {
	
	{
		System.out.println("teste");
	}

}
