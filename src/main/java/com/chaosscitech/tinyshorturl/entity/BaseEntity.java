package com.chaosscitech.tinyshorturl.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 基础实体
 * @author zach.chow
 * @date 2020/11/12
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@ToString
public abstract class BaseEntity {

    /**
     * ID，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 是否已删除，默认否
     */
    @Column(name = "deleted", columnDefinition = "bit default 0", nullable = false)
    protected boolean deleted = false;

    /**
     * 创建人，默认空
     */
    @Column(name = "created_by", nullable = false)
    private String createdBy = "";

    /**
     * 创建时间
     */
    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime;

    /**
     * 修改人，默认空
     */
    @Column(name = "updated_by", nullable = false)
    private String updateBy = "";

    /**
     * 创建时间
     */
    @Column(name = "updated_time", nullable = false)
    private Timestamp updatedTime;

    /**
     * 插入前，设置创建时间、修改时间
     */
    @PrePersist
    protected void prePersist() {
        if (this.createdTime == null) {
            createdTime = new Timestamp(System.currentTimeMillis());
        }
        if (this.updatedTime == null) {
            updatedTime = new Timestamp(System.currentTimeMillis());
        }
    }

    /**
     * 修改前，设置更新时间
     */
    @PreUpdate
    protected void preUpdate() {
        this.updatedTime = new Timestamp(System.currentTimeMillis());
    }

    /**
     * 删除前，设置更新时间
     */
    @PreRemove
    protected void preRemove() {
        this.updatedTime = new Timestamp(System.currentTimeMillis());
    }
}
