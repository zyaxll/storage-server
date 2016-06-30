package com.b5m.core.service;

import com.b5m.core.entity.Attribute;
import com.b5m.core.entity.Condition;
import com.b5m.core.entity.Page;
import com.b5m.core.entity.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 公用Service接口
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-25
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-25       Leo.li          1.0             TODO
 */
public interface ICommonService<T, ID extends Serializable> {

    /**
     * 保存实体
     * <p>若对象中存在主键, 则忽略该主键, 重新生成. 保存成功后主键会反写到实体对象中.</p>
     * @param entity 实体
     * @return 带主键的实体
     */
    T save(T entity);

    /**
     * 保存多个实体
     * <p>循环遍历实体, 然后保存</p>
     * @param iterable 实体列表
     * @return 保存后的实体列表
     */
    Iterable<T> save(Iterable<T> iterable);

    /**
     * 批量保存实体
     * @param iterable 实体列表
     * @return 保存成功数量
     */
    int saveInBatch(Iterable<T> iterable);

    /**
     * 通过ID删除实体
     * @param id ID
     */
    void delete(ID id);

    /**
     * 通过实体对象删除
     * <p>
     *     因该方法可能会造成批量删除,
     *     所有除非有必要时才使用, 否则不建议使用该方法
     *     可使用{@link #delete(ID)}
     * </p>
     * @param entity 实体
     */
    void delete(T entity);

    /**
     * 删除多个实体对象
     * <p>
     *     因该方法可能会造成批量删除,
     *     所有除非有必要时才使用, 否则不建议使用该方法.
     *     可使用{@link #deleteInBatch(Iterable<ID>)}
     * </p>
     * @param iterable 实体
     */
    void delete(Iterable<T> iterable);

    /**
     * 批量删除多条数据
     * @param iterable ID列表
     */
    void deleteInBatch(Iterable<ID> iterable);

    /**
     * 通过条件删除数据
     * 其中Condition中可参考{@link Condition}
     * @param iterable 条件列表
     */
    void deleteByCondition(Iterable<Condition> iterable);

    /**
     * 通过ID更新实体
     * @param entity 实体
     */
    void update(T entity);

    /**
     * 更新多个实体
     * @param iterable 实体列表
     */
    void update(Iterable<T> iterable);

    /**
     * 更新属性
     * @param id 对象ID
     * @param entity 属性
     */
    void updateByAttribute(ID id, Attribute entity);

    /**
     * 数据是否存在
     * @param id ID
     * @return 存在返回true, 否则false
     */
    boolean exists(ID id);

    /**
     * 数据是否存在
     * @param entity 实体
     * @return 存在返回true, 否则false
     */
    boolean exists(T entity);

    /**
     * 通过ID获取单个实体
     * @param id ID
     * @return 实体
     */
    T findOne(ID id);

    /**
     * 获取单个实体
     * @param entity 实体
     * @return 实体
     */
    T findOne(T entity);

    /**
     * 通过属性获取单个实体
     * @param iterable 属性
     * @return 实体
     */
    T findOneByCondition(Iterable<Condition> iterable);

    /**
     * 通过属性获取单个实体
     * @param conditions 属性
     * @return 实体
     */
    T findOneByCondition(Condition... conditions);

    /**
     * 获取实体数量
     * @return 实体数量
     */
    long count();

    /**
     * 获取实体数量
     * @param entity 实体
     * @return 数量
     */
    long count(T entity);

    /**
     * 通过属性获取实体数量
     * @param iterable 属性
     * @return 数量
     */
    long countByCondition(Iterable<Condition> iterable);

    /**
     * 获取全部实体
     * @return 全部实体
     */
    List<T> findAll();

    /**
     * 通过ID获取多个实体
     * @param iterable ID列表
     * @return 多个实体
     */
    List<T> findAll(Iterable<ID> iterable);

    /**
     * 获取全部实体, 可排序
     * @param sort 排序
     * @return 多个实体
     */
    List<T> findAll(Sort sort);

    /**
     * 获取多个实体
     * @param entity 实体
     * @return 多个实体
     */
    List<T> findAll(T entity);

    /**
     * 获取多个实体, 可排序
     * @param entity 实体
     * @param sort 排序
     * @return 多个实体
     */
    List<T> findAll(T entity, Sort sort);

    /**
     * 通过属性获取多个实体
     * @param iterable 属性
     * @return 多个实体
     */
    List<T> findAllByCondition(Iterable<Condition> iterable);

    /**
     * 通过属性获取多个实体
     * @param conditions 属性
     * @return 多个实体
     */
    List<T> findAllByCondition(Condition... conditions);

    /**
     * 通过属性获取多个实体, 可排序
     * @param sort 排序
     * @param iterable 属性
     * @return 多个实体
     */
    List<T> findAllByCondition(Sort sort, Iterable<Condition> iterable);

    /**
     * 通过属性获取多个实体, 可排序
     * @param sort 排序
     * @param conditions 属性
     * @return 多个实体
     */
    List<T> findAllByCondition(Sort sort, Condition... conditions);

    /**
     * 通过实体获取分页数据, 默认按实体中的Orderby注解排序
     * @param entity 实体
     * @param pageNumber 页号
     * @param pageSize 每页大小
     * @return 分页数据
     */
    Page<T> findPage(T entity, int pageNumber, int pageSize);

    /**
     * 通过实体获取分页数据, 可排序
     * @param entity 实体
     * @param sort 排序
     * @param pageNumber 页号
     * @param pageSize 每页大小
     * @return 分页数据
     */
    Page<T> findPage(T entity, Sort sort, int pageNumber, int pageSize);

    /**
     * 通过属性获取分页数据
     * @param iterable 属性
     * @param pageNumber 页号
     * @param pageSize 每页大小
     * @return 分页数据
     */
    Page<T> findPageByCondition(Iterable<Condition> iterable, int pageNumber, int pageSize);

    /**
     * 通过属性获取分页数据, 可排序
     * @param iterable 属性
     * @param sort 排序
     * @param pageNumber 页号
     * @param pageSize 每页大小
     * @return 分页数据
     */
    Page<T> findPageByCondition(Iterable<Condition> iterable, Sort sort, int pageNumber, int pageSize);

}
