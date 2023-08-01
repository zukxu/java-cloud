package com.zukxu.minio.utils;

import com.zukxu.minio.config.MinioProperties;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Description: minio交互
 *
 * @author zukxu
 * CreateTime: 2020/10/31 0031 15:36
 */
@Component
public class MinioUtils {
    @Autowired
    private final MinioProperties minio;
    @Autowired
    private MinioClient client;

    public MinioUtils(MinioProperties minio) {
        this.minio = minio;
    }

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName 桶名
     * @return
     */
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return client.bucketExists(bucketName);
    }

    /**
     * 创建 bucket
     *
     * @param bucketName 桶名
     */
    public void makeBucket(String bucketName) {
        try {
            if (!client.bucketExists(bucketName)) {
                client.makeBucket(bucketName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的bucket
     *
     * @return list
     * @throws Exception
     */
    public List<Bucket> getAllBucket() throws Exception {
        // 获取minio中所以的bucket
        return client.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public Optional<Bucket> getBucket(String bucketName) {
        return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        client.removeBucket(bucketName);
    }

    @SneakyThrows
    public Iterable<Result<Item>> listObjects(String bucketName) {
        return client.listObjects(bucketName);
    }

    /**
     * 根据文件前置查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @param recursive  是否递归查询
     * @return MinioItem 列表
     */
    @SneakyThrows
    public List getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        List<Item> list = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = client.listObjects(bucketName, prefix, recursive);
        if (objectsIterator != null) {
            for (Result<Item> result : objectsIterator) {
                Item item = result.get();
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url 外链
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        return client.presignedGetObject(bucketName, objectName, expires);
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        return client.getObject(bucketName, objectName);
    }

    /**
     * 获取文件路径
     *
     * @param bucketName 桶名
     * @param fileName   文件名
     * @return 文件路径
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String fileName) {
        return client.getObjectUrl(bucketName, fileName);
    }

    /**
     * 文件上传
     *
     * @param bucketName 桶名
     * @param objectName 对象名称
     * @param filename   文件名称
     */
    @SneakyThrows
    public void putObject(String bucketName, String objectName, String filename) {
        client.putObject(bucketName, objectName, filename, null);
    }

    /**
     * 文件上传
     *
     * @param bucketName 桶名
     * @param objectName 对象名称
     * @param stream     输入流
     */
    @SneakyThrows
    public void putObject(String bucketName, String objectName, InputStream stream) {
        PutObjectOptions options = new PutObjectOptions(stream.available(), -1L);
        client.putObject(bucketName, objectName, stream, options);
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param objectName
     */
    @SneakyThrows
    public void removeObject(String bucketName, String objectName) {
        client.removeObject(bucketName, objectName);
    }

    /**
     * 获取文件
     *
     * @param bucketName 桶名
     * @param objectName 文件名
     * @return ObjectStat
     */
    @SneakyThrows
    public ObjectStat statObject(String bucketName, String objectName) {
        return client.statObject(bucketName, objectName);
    }
}
