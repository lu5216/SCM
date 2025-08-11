package com.system.common.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author lutong
 * @name MinioProperties
 * @data 2025-03-24
 */

@Data
@Component
@RefreshScope
public class MinIOProperties {
    /**
     * 服务地址
     */
    @Value("${minio.url}")
    private String url;

    /**
     * 用户名
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * 密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 如果是true，则用的是https而不是http,默认值是true
     */
    @Value("${minio.secure}")
    private Boolean secure;

    /**
     * 存储桶名称
     */
    @Value("${minio.bucketName}")
    private String bucketName;

    public Boolean getSecure() {
        if (secure == null) {
            return true;
        }
        return secure;
    }
}
