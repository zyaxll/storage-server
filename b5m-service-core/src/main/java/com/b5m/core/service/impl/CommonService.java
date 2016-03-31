package com.b5m.core.service.impl;

import com.b5m.core.dao.mapper.CommonMapper;
import com.b5m.core.entity.Attribute;
import com.b5m.core.entity.Condition;
import com.b5m.core.entity.Page;
import com.b5m.core.entity.PageImpl;
import com.b5m.core.entity.Sort;
import com.b5m.core.service.ICommonService;
import com.b5m.utils.Assert;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 公用Service实现类
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
public abstract class CommonService<T, ID extends Serializable> implements ICommonService<T, ID> {

    private CommonMapper<T, ID> mapper;

    public CommonMapper<T, ID> getMapper() {
        return mapper;
    }

    /**
     * 所有子类需注入mapper对象
     * @param mapper mapper
     */
    public void setMapper(CommonMapper<T, ID> mapper) {
        this.mapper = mapper;
    }

    /**
     * 保存实体
     * <p>若对象中存在主键, 则忽略该主键, 重新生成. 保存成功后主键会反写到实体对象中.</p>
     * @param entity 实体
     * @return 带主键的实体
     */
    @Override
    public T save(T entity) {
        if (null == entity) {
            return null;
        }

        mapper.save(entity);
        return entity;
    }

    /**
     * 保存多个实体
     * <p>循环遍历实体, 然后保存</p>
     * @param iterable 实体列表
     * @return 保存后的实体列表
     */
    @Override
    public Iterable<T> save(Iterable<T> iterable) {
        if (null == iterable || !iterable.iterator().hasNext()) {
            return null;
        }

        for (T entity : iterable) {
            Assert.notNull(entity, "entity is null");
            mapper.save(entity);
        }
        return iterable;
    }

    /**
     * 批量保存实体
     * @param iterable 实体列表
     * @return 保存成功数量
     */
    @Override
    public int saveInBatch(Iterable<T> iterable) {
        if (null == iterable || !iterable.iterator().hasNext()) {
            return 0;
        }
        return mapper.saveInBatch(iterable);
    }

    /**
     * 通过ID删除实体
     * @param id ID
     */
    @Override
    public void delete(ID id) {
        if (null == id) {
            return;
        }

        mapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过实体对象删除
     * <p>
     *     因该方法可能会造成批量删除,
     *     所有除非有必要时才使用, 否则不建议使用该方法
     *     可使用{@link #delete(ID)}
     * </p>
     * @param entity 实体
     */
    @Override
    public void delete(T entity) {
        if (null == entity) {
            return;
        }

        mapper.deleteByEntity(entity);
    }

    /**
     * 删除多个实体对象
     * <p>
     *     因该方法可能会造成批量删除,
     *     所有除非有必要时才使用, 否则不建议使用该方法.
     *     可使用{@link #deleteInBatch(Iterable<ID>)}
     * </p>
     * @param iterable 实体
     */
    @Override
    public void delete(Iterable<T> iterable) {
        if (null == iterable || !iterable.iterator().hasNext()) {
            return;
        }

        for (T entity : iterable) {
            delete(entity);
        }
    }

    /**
     * 批量删除多条数据
     * @param iterable ID列表
     */
    @Override
    public void deleteInBatch(Iterable<ID> iterable) {
        if (null == iterable || !iterable.iterator().hasNext()) {
            return;
        }
        mapper.deleteByPrimaryKeys(iterable);
    }

    /**
     * 通过条件删除数据
     * 其中Condition中可参考{@link Condition}
     * @param iterable 条件列表
     */
    @Override
    public void deleteByCondition(Iterable<Condition> iterable) {
        if (null == iterable || !iterable.iterator().hasNext()) {
            return;
        }
        mapper.deleteByCondition(iterable);
    }

    /**
     * 通过ID更新实体
     * @param entity 实体
     */
    @Override
    public void update(T entity) {
        if (null == entity) {
            return;
        }
        mapper.updateByEntity(entity);
    }

    /**
     * 更新多个实体
     * @param iterable 实体列表
     */
    @Override
    public void update(Iterable<T> iterable) {
        if (null == iterable || !iterable.iterator().hasNext()) {
            return;
        }
        for (T entity : iterable) {
            delete(entity);
        }
    }

    /**
     * 更新属性
     * @param id 对象ID
     * @param entity 属性
     */
    @Override
    public void updateByAttribute(ID id, Attribute entity) {
        mapper.updateByAttribute(id, entity);
    }

    /**
     * 数据是否存在
     * @param id ID
     * @return 存在返回true, 否则false
     */
    @Override
    public boolean exists(ID id) {
        return null != id && mapper.exists(id);
    }

    /**
     * 数据是否存在
     * @param entity 实体
     * @return 存在返回true, 否则false
     */
    @Override
    public boolean exists(T entity) {
        return null != entity && mapper.existsByEntity(entity);
    }

    /**
     * 通过ID获取单个实体
     * @param id ID
     * @return 实体
     */
    @Override
    public T findOne(ID id) {
        if (null == id) {
            return null;
        }
        return mapper.findOne(id);
    }

    /**
     * 获取单个实体
     * @param entity 实体
     * @return 实体
     */
    @Override
    public T findOne(T entity) {
        if (null == entity) {
            return null;
        }
        return mapper.findOneByEntity(entity);
    }

    /**
     * 通过属性获取单个实体
     * @param iterable 属性
     * @return 实体
     */
    @Override
    public T findOneByCondition(Iterable<Condition> iterable) {
        if (null == iterable || !iterable.iterator().hasNext()) {
            return null;
        }

        return mapper.findOneByCondition(iterable);
    }

    @Override
    public T findOneByCondition(Condition... conditions) {
        return findOneByCondition(Arrays.asList(conditions));
    }

    /**
     * 获取实体数量
     * @return 实体数量
     */
    @Override
    public long count() {
        return mapper.count();
    }

    /**
     * 获取实体数量
     * @param entity 实体
     * @return 数量
     */
    @Override
    public long count(T entity) {
        if (null == entity) {
            return count();
        }
        return mapper.countByEntity(entity);
    }

    /**
     * 通过属性获取实体数量
     * @param iterable 属性
     * @return 数量
     */
    @Override
    public long countByCondition(Iterable<Condition> iterable) {
        if (null == iterable || !iterable.iterator().hasNext()) {
            return count();
        }

        return mapper.countByCondition(iterable);
    }

    /**
     * 获取全部实体
     * @return 全部实体
     */
    @Override
    public List<T> findAll() {
        return mapper.findAll();
    }

    /**
     * 通过ID获取多个实体
     * @param iterable ID列表
     * @return 多个实体
     */
    @Override
    public List<T> findAll(Iterable<ID> iterable) {
        if (null == iterable || !iterable.iterator().hasNext()) {
            return null;
        }
        return mapper.findAllByPrimaryKeys(iterable);
    }

    /**
     * 获取全部实体, 可排序
     * @param sort 排序
     * @return 多个实体
     */
    @Override
    public List<T> findAll(Sort sort) {
        if (null == sort) {
            return findAll();
        }
        return mapper.findAllBySort(sort);
    }

    /**
     * 获取多个实体
     * @param entity 实体
     * @return 多个实体
     */
    @Override
    public List<T> findAll(T entity) {
        if (null == entity) {
            return findAll();
        }
        return mapper.findAllByEntity(entity);
    }

    /**
     * 获取多个实体, 可排序
     * @param entity 实体
     * @param sort 排序
     * @return 多个实体
     */
    @Override
    public List<T> findAll(T entity, Sort sort) {
        if (null == entity) {
            if (null == sort) {
                return findAll();
            } else {
                return findAll(sort);
            }
        } else {
            if (null == sort) {
                return findAll(entity);
            }

            return mapper.findAllByEntityAndSort(entity, sort);
        }
    }

    /**
     * 通过属性获取多个实体
     * @param iterable 属性
     * @return 多个实体
     */
    @Override
    public List<T> findAllByCondition(Iterable<Condition> iterable) {
        return mapper.findAllByCondition(iterable);
    }

    /**
     * 通过属性获取多个实体, 可排序
     * @param sort 排序
     * @param iterable 属性
     * @return 多个实体
     */
    @Override
    public List<T> findAllByCondition(Sort sort, Iterable<Condition> iterable) {
        return mapper.findAllByConditionAndSort(iterable, sort);
    }

    /**
     * 通过属性获取多个实体
     * @param conditions 属性
     * @return 多个实体
     */
    @Override
    public List<T> findAllByCondition(Condition... conditions) {
        return mapper.findAllByCondition(Arrays.asList(conditions));
    }

    /**
     * 通过属性获取多个实体, 可排序
     * @param sort 排序
     * @param conditions 属性
     * @return 多个实体
     */
    @Override
    public List<T> findAllByCondition(Sort sort, Condition... conditions) {
        return mapper.findAllByConditionAndSort(Arrays.asList(conditions), sort);
    }

    /**
     * 通过实体获取分页数据, 默认按实体中的Orderby注解排序
     * @param entity 实体
     * @param pageNumber 页号
     * @param pageSize 每页大小
     * @return 分页数据
     */
    @Override
    public Page<T> findPage(T entity, int pageNumber, int pageSize) {
        long totalRecord = count(entity);
        if (0L == totalRecord) {
            return new PageImpl<>();
        }

        PageImpl<T> page = new PageImpl<>(pageNumber, totalRecord, pageSize);
        long first = page.getFirstIndexOfCurrentPage();
        if (first != 0L) {
            first = first - 1;
        }

        if (first < page.getTotalRecordCount()) {
            List<T> content = mapper.findPageByEntity(entity, first, pageSize);
            page.setContent(content);
        }

        return page;
    }

    /**
     * 通过实体获取分页数据, 可排序
     * @param entity 实体
     * @param sort 排序
     * @param pageNumber 页号
     * @param pageSize 每页大小
     * @return 分页数据
     */
    @Override
    public Page<T> findPage(T entity, Sort sort, int pageNumber, int pageSize) {
        long totalRecord = count(entity);
        if (0L == totalRecord) {
            return new PageImpl<>();
        }

        PageImpl<T> page = new PageImpl<>(pageNumber, totalRecord, pageSize);
        long first = page.getFirstIndexOfCurrentPage();
        if (first != 0L) {
            first = first - 1;
        }

        if (first < page.getTotalRecordCount()) {
            List<T> content = mapper.findPageByEntityAndSort(entity, sort, first, pageSize);
            page.setContent(content);
        }

        return page;
    }

    /**
     * 通过属性获取分页数据
     * @param iterable 属性
     * @param pageNumber 页号
     * @param pageSize 每页大小
     * @return 分页数据
     */
    @Override
    public Page<T> findPageByCondition(Iterable<Condition> iterable, int pageNumber, int pageSize) {
        long totalRecord = countByCondition(iterable);
        if (0L == totalRecord) {
            return new PageImpl<>();
        }

        PageImpl<T> page = new PageImpl<>(pageNumber, totalRecord, pageSize);
        long first = page.getFirstIndexOfCurrentPage();
        if (first != 0L) {
            first = first - 1;
        }

        if (first < page.getTotalRecordCount()) {
            List<T> content = mapper.findPageByCondition(iterable, first, pageSize);
            page.setContent(content);
        }

        return page;
    }

    /**
     * 通过属性获取分页数据, 可排序
     * @param iterable 属性
     * @param sort 排序
     * @param pageNumber 页号
     * @param pageSize 每页大小
     * @return 分页数据
     */
    @Override
    public Page<T> findPageByCondition(Iterable<Condition> iterable, Sort sort, int pageNumber, int pageSize) {
        long totalRecord = countByCondition(iterable);
        if (0L == totalRecord) {
            return new PageImpl<>();
        }

        PageImpl<T> page = new PageImpl<>(pageNumber, totalRecord, pageSize);
        long first = page.getFirstIndexOfCurrentPage();
        if (first != 0L) {
            first = first - 1;
        }

        if (first < page.getTotalRecordCount()) {
            List<T> content = mapper.findPageByConditionAndSort(iterable, sort, first, pageSize);
            page.setContent(content);
        }

        return page;
    }
}
