package com.system.entrance.config;

import com.system.common.vo.MinIOProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lutong
 * @name MinIOConfig
 * @data 2025-03-24
 */
@Configuration
public class MinIOConfig {

    @Autowired
    private MinIOProperties minIOProperties;

    @Bean
    public MinioClient getMinioClient() {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minIOProperties.getUrl())
                    .credentials(minIOProperties.getAccessKey(), minIOProperties.getSecretKey())
                    .build();
            return minioClient;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
            System.out.println("e ==>" + e);
            throw new RuntimeException("无法初始化MinIO客户端", e);
        }
    }
}