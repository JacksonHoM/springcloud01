package cn.tedu.sp01.service;

import cn.tedu.sp01.pojo.Item;

import java.util.List;

public interface ItemService {

    /** 根据订单Id查询*/
    List<Item> getItems(String orderId);
    /** 根据订单删除库存*/
    void decreaseNumbers(List<Item> list);
}
