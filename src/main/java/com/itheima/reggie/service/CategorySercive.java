package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * @author whx
 * @create 2022-07-19 16:25
 */
public interface CategorySercive extends IService<Category> {
    public void remove(long id);
}
