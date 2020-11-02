package cn.tedu.sp02.item.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class itemServiceImpl implements ItemService {

    @Override
    public List<Item> getItems(String orderId) {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(1,"商品1",10));
        items.add(new Item(2,"商品1",12));
        items.add(new Item(3,"商品1",13));
        items.add(new Item(4,"商品1",104));
        items.add(new Item(5,"商品1",19));
        return items;
    }

    @Override
    public void decreaseNumbers(List<Item> list) {
        for(Item item:list){
            log.info("减少商品库存"+item);
        }
    }
}
