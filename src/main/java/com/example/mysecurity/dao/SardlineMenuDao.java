package com.example.mysecurity.dao;

import com.example.mysecurity.entity.SardlineMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SardlineMenu)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-10 10:40:57
 */
@Mapper
public interface SardlineMenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    SardlineMenu queryById(String menuId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SardlineMenu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sardlineMenu 实例对象
     * @return 对象列表
     */
    List<SardlineMenu> queryAll(SardlineMenu sardlineMenu);

    /**
     * 新增数据
     *
     * @param sardlineMenu 实例对象
     * @return 影响行数
     */
    int insert(SardlineMenu sardlineMenu);

    /**
     * 修改数据
     *
     * @param sardlineMenu 实例对象
     * @return 影响行数
     */
    int update(SardlineMenu sardlineMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteById(String menuId);

}