package com.bookstore.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;
@Configuration public class UploadConfig implements WebMvcConfigurer{
 @Override public void addResourceHandlers(ResourceHandlerRegistry registry){registry.addResourceHandler("/uploads/avatars/**").addResourceLocations(Paths.get("uploads","avatars").toAbsolutePath().toUri().toString());}
}
