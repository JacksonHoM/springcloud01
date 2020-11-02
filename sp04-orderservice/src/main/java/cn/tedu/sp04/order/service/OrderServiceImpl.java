package cn.tedu.sp04.order.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.sp04.feign.ItemFeignClient;
import cn.tedu.sp04.feign.UserFeignClient;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemFeignClient itemFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public Order getOrder(String orderId) {
        //获取用户信息
        //TODO:远程调用用户，获取用户数据
        JsonResult<User> user = userFeignClient.getUser(8);
        //获取订单列表信息
        //TODO:远程调用商品服务，获取商品
        JsonResult<List<Item>> items = itemFeignClient.getItems(orderId);
        /**
         * 获取订单
         * 调用用户获取用户数据，调用订单获取订单列表
         */
        Order order = new Order();
        order.setId(orderId);
        order.setItems(items.getData());
        order.setUser(user.getData());
        return order;
    }

    @Override
    public void addOrder(Order order) {
        //减少商品库存
        itemFeignClient.decreaseNumber(order.getItems());
        //增加用户积分
        userFeignClient.addScore(8,1000);
        log.info("增加订单");
    }
}
