package com.osc.ecommerce.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary getCloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dfl3sbjog",
                "api_key", "494628779125567",
                "api_secret", "Wi8H-CesIXW9Zrb2w4NMhQit3Yc"));
    }

}
