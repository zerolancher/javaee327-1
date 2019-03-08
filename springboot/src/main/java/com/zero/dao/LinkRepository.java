package com.zero.dao;


import com.zero.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 友情链接Repository接口
 * @author Administrator
 *
 */
public interface LinkRepository extends JpaRepository<Link, Integer> {

}
