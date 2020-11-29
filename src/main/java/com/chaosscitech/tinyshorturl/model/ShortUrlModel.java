package com.chaosscitech.tinyshorturl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * 短地址模型（包含原始URL）
 * @author zach.chow
 * @date 2020/11/12
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortUrlModel {
    /**
     * 原始URL
     */
    private String rawUrl;
}
