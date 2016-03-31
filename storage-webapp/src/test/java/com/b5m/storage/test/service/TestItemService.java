package com.b5m.storage.test.service;

import com.b5m.core.entity.Attribute;
import com.b5m.core.entity.Condition;
import com.b5m.core.entity.Page;
import com.b5m.storage.entity.Item;
import com.b5m.storage.service.IItemService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 公用Service测试类
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-3-31
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-3-31       Leo.li          1.0             TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class TestItemService {

    @Autowired
    private IItemService itemService;

    @Test
    public void testSaveAndFindOne() {
        Item item = null;
        item = itemService.save(item);
        Assert.assertNull(item);

        item = new Item();
        item.setConsumeAmount(200.02);
        item.setCurrency("curency1");
        item.setDiscountAmount(300.00);
        item.setEffectTime(new Date());
        item.setItemGroup(1);
        item.setItemName("itemName1");
        item.setItemSubGroup(2);
        item.setLevelLimit(1);
        item.setPiid(1000L);
        item.setPrice(100.02);
        item.setStatus("1");
        item.setTimeDuration(1L);
        item.setTimeout(new Date());
        item.setType(1);
        item = itemService.save(item);
        Long id = item.getId();
        Assert.assertNotNull(id);

        Item qItem = itemService.findOne(id);
        Assert.assertNotNull(qItem);

        item.setItemName("itemName2");
        item = itemService.save(item);
        Assert.assertNotNull(item.getId());
        Assert.assertNotEquals(id, item.getId());

        Item fItem = itemService.findOne(item.getId());
        Assert.assertNotNull(fItem);
        Assert.assertEquals(item.getItemName(), "itemName2");

    }

    @Test
    public void testSaveListAndFindAll() {
        Item item = null;
        List<Item> list;

        list = itemService.findAll();
        Assert.assertNotNull(list);

        List<Item> itemList = itemService.findAll(item);
        Assert.assertTrue(list.size() == itemList.size());

        item = new Item();
        List<Item> itemList2 = itemService.findAll(item);
        Assert.assertTrue(list.size() == itemList2.size());

        item.setItemName("itemName2");
        list = itemService.findAll(item);
        Assert.assertNotNull(list);

        list = (List<Item>) itemService.save(list);

        item.setItemName("itemName2");
        itemList2 = itemService.findAll(item);
        Assert.assertTrue(list.size() * 2 == itemList2.size());
    }

    @Test
    public void testSaveInBatchAndFindAllByCondition() {
        List<Item> lst = itemService.findAllByCondition(Condition.eq(Item.ITEM_NAME_COLUMN, "itemName1"));
        Assert.assertNotNull(lst);

        int size = itemService.saveInBatch(lst);
        Assert.assertTrue(size > 0);
    }

    @Test
    public void testDeleteByIdAndFindOne() {
        Item item = new Item();
        item.setItemName("itemName1");
        item = itemService.findOne(item);
        Assert.assertNotNull(item.getId());

        itemService.delete(item.getId());

        item = itemService.findOne(item.getId());
        Assert.assertNull(item);
    }

    @Test
    public void testDeleteByEntityAndFindOne() {
        Item item = new Item();
        item.setItemName("itemName1");
        item = itemService.findOne(item);
        Long id = item.getId();

        itemService.delete(item);

        item = itemService.findOne(id);
        Assert.assertNull(item);
    }

    @Test
    public void testDeleteByConditionAndFindByCondition() {
        List<Condition> lsc = Condition.ilike(Item.ITEM_NAME_COLUMN, "vIp")
                .add(Condition.ge(Item.EFFECT_TIME_COLUMN, new Date())).getCnds();

        List<Item> list = itemService.findAllByCondition(lsc);
        Assert.assertNotNull(list);

        itemService.deleteByCondition(lsc);

        list = itemService.findAllByCondition(lsc);
        Assert.assertTrue(list.size() == 0);
    }

    @Test
    public void testDeleteInBatchAndFindPageByCondition() {
        Page<Item> page = itemService.findPageByCondition(Condition.likeEnd(Item.ITEM_NAME_COLUMN, "2").getCnds(), 1, 2);
        List<Long> ids = new ArrayList<>();
        for (Item item : page.getContent()) {
            ids.add(item.getId());
        }
        itemService.deleteInBatch(ids);

        List<Item> itemList = itemService.findAll(ids);
        Assert.assertNull(itemList);
    }

    @Test
    public void testUpdateAndFindOne() {
        Item item = new Item();
        item.setItemName("itemName2");
        item = itemService.findOne(item);

        Long id = item.getId();
        Assert.assertNotNull(id);

        item.setItemName("itemName3");
        itemService.update(item);

        item = itemService.findOne(id);
        Assert.assertEquals("itemName3", item.getItemName());
    }

    @Test
    public void testUpdateByAttribute() {
        Item item = itemService.findOneByCondition(Condition.eq(Item.ITEM_NAME_COLUMN, "itemName3").getCnds());
        Assert.assertNotNull(item);

        Long id = item.getId();

        Attribute attribute = new Attribute();
        attribute.add(new Attribute.Property(Item.STATUS_COLUMN, "0"));
        attribute.add(new Attribute.Property(Item.PIID_COLUMN, "2222"));

        itemService.updateByAttribute(item.getId(), attribute);

        item = itemService.findOne(id);
        Assert.assertEquals(item.getStatus(), "0");
        Assert.assertEquals(item.getPiid().longValue(), 2222L);

    }

    @Test
    public void testExists() {
        Item item = itemService.findOneByCondition(Condition.eq(Item.ITEM_NAME_COLUMN, "itemName3").getCnds());

        boolean isExists = itemService.exists(item.getId());
        Assert.assertTrue(isExists);

        boolean isExists2 = itemService.exists(item);
        Assert.assertTrue(isExists2);

        item.setId(-1L);
        boolean isExists3 = itemService.exists(item);
        Assert.assertTrue(!isExists3);

        item = null;
        boolean isExists4 = itemService.exists(item);
        Assert.assertTrue(!isExists3);

        Long id = null;
        boolean isExists5 = itemService.exists(id);
        Assert.assertTrue(!isExists5);
    }

}
