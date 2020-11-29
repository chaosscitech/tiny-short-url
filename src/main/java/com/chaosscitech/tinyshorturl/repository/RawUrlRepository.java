package com.chaosscitech.tinyshorturl.repository;


import com.chaosscitech.tinyshorturl.entity.RawUrlEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 原始URL存储
 * @author zach.chow
 * @date 2020/11/12
 */
public interface RawUrlRepository extends JpaRepository<RawUrlEntity, Long> {
    List<RawUrlEntity> findByUrl(String url, Pageable pageable);
}
