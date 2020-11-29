package com.chaosscitech.tinyshorturl.controller;

import com.chaosscitech.tinyshorturl.model.ShortUrlModel;
import com.chaosscitech.tinyshorturl.service.ShortUrlService;
import com.chaosscitech.tinyshorturl.util.IdHashUtil;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zach.chow
 * @date 2020/11/12
 */
@Controller
@RequestMapping("/shorturl")
public class ShortUrlController {

    private final static UrlValidator urlValidator = new UrlValidator();

    @Value("${rawurl.host}")
    private String host;

    @Value("${rawurl.home}")
    private String home;

    @Resource
    private ShortUrlService service;

    /**
     * 创建短URL，普通文本格式输入、输出
     * @param rawUrl 原始URL
     * @return 普通文本格式输出
     */
    @PostMapping(value = {"/", ""}, consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String shortUrlPlain(@RequestBody String rawUrl) {
        if (rawUrl != null) {
            rawUrl = rawUrl.replace(host + "//", host + "/");
        }
        valid(rawUrl);
        return service.getShortUrl(rawUrl);
    }

    /**
     * 创建短URL，JSON格式输入、输出
     * @param model 短地址模型（包含原始URL）
     * @return JSON格式输出
     */
    @PostMapping(value = {"/", ""}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> shortUrlJson(@RequestBody ShortUrlModel model) {
        valid(model.getRawUrl());
        String shortUrl = service.getShortUrl(model.getRawUrl());
        return ImmutableMap.<String, Object>of("status", "ok", "url", shortUrl);
    }

    /**
     * 重定向到原始URL
     * @param shortUrlId 短地址ID
     * @return 重定向
     */
    @GetMapping(value = "/{shortUrlId}")
    public String rawUrl(@PathVariable String shortUrlId) {
        if (!IdHashUtil.checkEncodedString(shortUrlId)) {
            throw new IllegalArgumentException("short url ID " + shortUrlId + " not found");
        }
        String rawUrl = service.getRawUrl(shortUrlId);
        if (rawUrl == null) {
            throw new IllegalArgumentException("short url ID " + shortUrlId + " not found");
        }
        return "redirect:" + rawUrl;
    }

    /**
     * 重定向到主页URL
     * @return 重定向
     */
    @RequestMapping(value = {"/", ""}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String home() {
        return "redirect:" + home;
    }

    /**
     * 校验原始URL是否合法
     * @param rawUrl 原始URL
     */
    private void valid(String rawUrl) {
        if (!urlValidator.isValid(rawUrl)) {
            throw new IllegalArgumentException("raw url " + rawUrl + " is invalid");
        }
    }
}
