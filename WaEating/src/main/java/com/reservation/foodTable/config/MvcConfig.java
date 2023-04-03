package com.reservation.foodTable.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      String dirName = "onetoone-image";
      Path photosDir = Paths.get(dirName);
      
      String photosPath = photosDir.toFile().getAbsolutePath();
      registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + photosPath + "/");
      
      
         String bannerImagesDirName = "banner";
         Path bannerImagesDir = Paths.get(bannerImagesDirName);

         String bannerImagesPath = bannerImagesDir.toFile().getAbsolutePath();

         registry.addResourceHandler("/" + bannerImagesDirName + "/**").addResourceLocations("file:/" + bannerImagesPath + "/");
         
         
         String ImagesDirName = "restaurant-photos";
         Path ImagesDir = Paths.get(ImagesDirName);

         String ImagesPath = ImagesDir.toFile().getAbsolutePath();

         registry.addResourceHandler("/" + ImagesDirName + "/**").addResourceLocations("file:/" + ImagesPath + "/");
   }
}