package com.system.common.component;

import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.vo.MinIOProperties;
import io.minio.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.stream.StreamSupport;

/**
 * @author lutong
 * @name MinIOFileUtil
 * @data 2025-03-24
 */

@Slf4j
@Component
public class MinIOComponent {

    @Autowired
    private MinIOProperties minIOProperties;

    @Autowired
    private MinioClient minioClient;


    /**
     *  创建桶
     * @param bucketName 桶名称
     */
    public void createBucket(String bucketName) throws Exception {
        if (!StringUtils.hasLength(bucketName)) {
            throw new CustomerAuthenticationException("桶名不能为" + bucketName);
        }

        minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucketName)
                .build());
    }

    /**
     *  创建文件夹
     * @param bucketName 桶名
     * @param folderName 文件夹名称
     * @return
     * @throws Exception
     */
    public ObjectWriteResponse createBucketFolder(String bucketName, String folderName) throws Exception {
        if (!checkBucketExist(bucketName)) {
            throw new CustomerAuthenticationException("桶不存在，无法创建文件夹");
        }
        if (!StringUtils.hasLength(folderName)) {
            throw new RuntimeException("创建的文件夹名不能为空");
        }

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(folderName + "/")
                .stream(new ByteArrayInputStream(new byte[0]), 0, 0)
                .build();

        ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);
        return objectWriteResponse;
    }


    /**
     *  检查桶是否存在
     * @param bucketName 桶名称
     * @return boolean true-存在 false-不存在
     */
    public boolean checkBucketExist(String bucketName) throws Exception {
        if (!StringUtils.hasLength(bucketName)) {
            throw new CustomerAuthenticationException("桶名不能为空");
        }

        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     *  删除文件
     * @param bucketName 桶名
     * @param objectName 文件所在的路径 + 文件名称  例如：image/img/123.jpg
     * @return
     */
    public void deleteBucketFile(String bucketName, String objectName) {
        if (!StringUtils.hasLength(bucketName) || !StringUtils.hasLength(objectName)) {
            throw new CustomerAuthenticationException("桶名或文件名不能为空");
        }

        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.info("桶【" + bucketName + "】删除文件【" + objectName + "】失败！");
            throw new CustomerAuthenticationException("桶【" + bucketName + "】删除文件【" + objectName + "】失败！");
        }
    }


    /**
     *  删除文件夹
     * @param bucketName 桶名
     * @param folderPath 文件夹名称
     * @return
     */
    public void deleteBucketFolder(String bucketName, String folderPath) {
        if (!StringUtils.hasLength(bucketName) || !StringUtils.hasLength(folderPath)) {
            throw new CustomerAuthenticationException("桶名或文件名不能为空");
        }

        try {
            Iterable<Result<Item>> objects = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(folderPath)
                            .recursive(true)
                            .build()
            );

            StreamSupport.stream(objects.spliterator(), true)
                    .forEach(itemResult -> {
                        try {
                            minioClient.removeObject(RemoveObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(itemResult.get().objectName())
                                    .build()
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            log.info("桶【" + bucketName + "】删除文件夹【" + folderPath + "】失败！");
            throw new CustomerAuthenticationException("桶【" + bucketName + "】删除文件夹【" + folderPath + "】失败！");
        }
    }

    /**
     *  根据文件后缀，获取响应头类型
     * @param fileName
     * @return
     */
    public String getFileContentType(String fileName){
        String returnFileName = fileName.substring(fileName.lastIndexOf("."));
        if (returnFileName.equals(".jpeg") || returnFileName.equals(".png") || returnFileName.equals(".jpg")) {
            return "image/jpeg";
        } else if (returnFileName.equals(".mp4")) {
            return "video/mp4";
        } else if (returnFileName.equals(".html")) {
            return "text/html";
        } else if (returnFileName.equals(".css")) {
            return "text/css";
        } else if (returnFileName.equals(".js")) {
            return "application/javascript";
        } else if (returnFileName.equals(".pdf")) {
            return "application/pdf";
        } else {
            return "application/octet-stream";
        }
    }
}
