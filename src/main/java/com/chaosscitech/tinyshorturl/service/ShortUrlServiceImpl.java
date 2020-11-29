package com.chaosscitech.tinyshorturl.service;

import com.chaosscitech.tinyshorturl.entity.RawUrlEntity;
import com.chaosscitech.tinyshorturl.repository.RawUrlRepository;
import com.chaosscitech.tinyshorturl.util.IdHashUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 短地址服务实现
 * @author zach.chow
 * @date 2020/11/12
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Resource
    private RawUrlRepository repository;

    @Value("${shorturl.protocol}")
    private String protocol;

    @Value("${shorturl.host}")
    private String host;

    @Value("${shorturl.path}")
    private String path;

    private String prefix;

    @PostConstruct
    public void init() {
        prefix = protocol + "://" + host + path + "/";
    }

    /**
     * 获取原始URL对应的
     * @param rawUrl 原始URL
     * @return 短URL
     */
    @Override
    @Transactional
    @Cacheable("default#3600")
    public String getShortUrl(String rawUrl) {
        List<RawUrlEntity> rawUrlEntities = repository.findByUrl(rawUrl, PageRequest.of(0, 1));
        RawUrlEntity rawUrlEntity;
        if (CollectionUtils.isEmpty(rawUrlEntities)) {
            rawUrlEntity = new RawUrlEntity();
            rawUrlEntity.setUrl(rawUrl);
            rawUrlEntity = repository.save(rawUrlEntity);
        } else {
            rawUrlEntity = rawUrlEntities.get(0);
        }
        return prefix + encodeUrl(rawUrlEntity.getId());
    }

    /**
     * 获取短地址ID对应的原始URL
     * @param shortUrlId 短地址ID
     * @return 原始URL
     */
    @Override
    @Cacheable(value = "default#3600", unless = "#result==null")
    public String getRawUrl(String shortUrlId) {
        long id = decodeUrl(shortUrlId);
        Optional<RawUrlEntity> rawUrl = repository.findById(id);
        return rawUrl.map(RawUrlEntity::getUrl).orElse(null);
    }

    /**
     * 将原始URL对应的ID编码成短地址ID
     * @param id 原始URL对应的ID
     * @return 短地址ID
     */
    private String encodeUrl(long id) {
        return IdHashUtil.encode(id);
    }

    /**
     * 将短地址ID解码成原始URL对应的ID
     * @param shortUrlId 短地址ID
     * @return 原始URL对应的ID
     */
    private long decodeUrl(String shortUrlId) {
        return IdHashUtil.decode(shortUrlId);
    }
}
