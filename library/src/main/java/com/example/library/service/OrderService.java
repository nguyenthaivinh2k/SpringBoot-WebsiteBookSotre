package com.example.library.service;

import com.example.library.model.CartItem;
import com.example.library.model.Order;
import com.example.library.model.OrderDetail;
import com.example.library.model.ShoppingCart;
import com.example.library.repository.CartRepository;
import com.example.library.repository.OderDetailRepository;
import com.example.library.repository.OderRepository;
import com.example.library.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OderRepository oderRepository;
    @Autowired
    OderDetailRepository oderDetailRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    public List<Order> findAll(){
      return  oderRepository.findAll();
    }
    public void saveOder(ShoppingCart shoppingCart){
        Order order = new Order();
        order.setOrderStatus("PENDING");
        order.setOderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem i : shoppingCart.getCartItem()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(i.getQuantity());
            orderDetail.setUnitPrice(i.getProduct().getCostPrice());
            orderDetail.setTotalPrice(i.getTotalPrice());
            oderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
            cartRepository.delete(i);
        }
        order.setOrderDetails(orderDetailList);
        shoppingCart.setCartItem(new HashSet<>());
        shoppingCart.setTotalItems(0);
        shoppingCart.setTotalPrice(0);
        shoppingCartRepository.save(shoppingCart);
        oderRepository.save(order);
    }
}
