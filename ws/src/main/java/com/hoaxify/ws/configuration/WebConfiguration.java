package com.hoaxify.ws.configuration;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.hoaxify.ws.configuration.AppConfiguration;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{
	/*@Value("${upload-path}")
	String uploadPath; Aynı şekilde kendimiz oluşturduğumuz class ile yapıcaz */
	
	@Autowired
	AppConfiguration appConfiguration;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//http://localhost:8080/images/profile.png şeklinde görebileceğiz ve client üzerinden de buraya request atıcaz
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:./"+appConfiguration.getUploadPath()+"/")
		.setCacheControl(CacheControl.maxAge(365,TimeUnit.DAYS));//Cache'e ekleme
	}
 /* fotoğraflara web sitesi üzerinden erişmeyi - eriştiğimiz dosyayı ya profil fotosuna koyabilmeyi sağladık*/
	
	@Bean
	CommandLineRunner createStorageDirectories() { //Yine uygulama çalışırken compile zamanında çalışacak bir command yazdık
		return (args) ->{
			File folder = new File(appConfiguration.getUploadPath()); //Amacımız fotoğrafları koyacağımız klasörü compile time'da oluşturmak
			boolean folderExist = folder.exists() && folder.isDirectory();
			if(!folderExist) {
				folder.mkdir();
			}
		};
	}
}
