package com.chaosscitech.tinyshorturl.service;

/**
 * 短地址服务接口
 * @author zach.chow
 * @date 2020/11/12
 */
public interface ShortUrlService {

    /**
     * 获取原始URL对应的
     * @param rawUrl 原始URL
     * @return 短URL
     */
    String getShortUrl(String rawUrl);

    /**
     * 获取短地址ID对应的原始URL
     * @param shortUrlId 短地址ID
     * @return 原始URL
     */
    String getRawUrl(String shortUrlId);
}
