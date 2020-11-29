package com.chaosscitech.tinyshorturl.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 原始URL实体
 * @author zach.chow
 * @date 2020/11/12
 */
@Entity
@Table(name = "tb_raw_url")
@SQLDelete(sql = "UPDATE tb_raw_url SET deleted = 1 WHERE id = ?")
@Where(clause = "deleted = 0")
@Getter
@Setter
@ToString(callSuper = true)
public class RawUrlEntity extends BaseEntity {

    /**
     * 原始URL
     */
    @Column(name = "url", nullable = false)
    private String url;

    /**
     * 描述
     */
    @Column(name = "description", nullable = false)
    private String description = "";
}
