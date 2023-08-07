package com.example.library.service;

import com.example.library.model.CartItem;
import com.example.library.model.Customer;
import com.example.library.model.Product;
import com.example.library.model.ShoppingCart;
import com.example.library.repository.CartRepository;
import com.example.library.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart findByCustomer(Customer customer){
        return shoppingCartRepository.findByCustomer(customer.getId());
    }
    public ShoppingCart addItemtoCart(Product product, int quantity, Customer customer) {
        ShoppingCart shoppingCart = customer.getShoppingCart();
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        Set<CartItem> cartItems = shoppingCart.getCartItem();
        CartItem item = findCartItem(cartItems,product.getId());
        if (cartItems == null) {
            cartItems = new HashSet<>();
            if (item == null) { // sure
                item = new CartItem();
                item.setProduct(product);
                item.setTotalPrice(quantity * product.getCostPrice());
                item.setQuantity(quantity);
                item.setCart(shoppingCart);
                cartItems.add(item);
                cartRepository.save(item);
            }
        } else {
            if(item == null){
                item = new CartItem();
                item.setProduct(product);
                item.setTotalPrice(quantity * product.getCostPrice());
                item.setQuantity(quantity);
                item.setCart(shoppingCart);
                cartItems.add(item);
                cartRepository.save(item);
            }else {
                item.setQuantity(item.getQuantity()+quantity);
                item.setTotalPrice(item.getQuantity() * product.getCostPrice());
                cartRepository.save(item);
            }
        }
        shoppingCart.setCartItem(cartItems);
        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);
        shoppingCart.setTotalItems(totalItems);
        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setCustomer(customer);
        return shoppingCartRepository.save(shoppingCart);
    }

    public CartItem findCartItem(Set<CartItem> cartItemSet, Long productId) {
        if (cartItemSet == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem c : cartItemSet) {
            if (c.getProduct().getId() == productId) {
                cartItem = c;
            }

        }
        return cartItem;
    }
    public int totalItems(Set<CartItem> cartItems){
        int total=0;
        for (CartItem c:cartItems) {
            total += c.getQuantity();
        }
        return total;
    }
    public double totalPrice(Set<CartItem> cartItems){
        int total=0;
        for (CartItem c:cartItems) {
            total += c.getTotalPrice();
        }
        return total;
    }
    public ShoppingCart updateCart(Product product, int quantity, Customer customer) {
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Set<CartItem> cartItems = shoppingCart.getCartItem();
        CartItem item = findCartItem(cartItems,product.getId());
        item.setQuantity(quantity);
        item.setTotalPrice(quantity*product.getCostPrice());
        cartRepository.save(item);
        shoppingCart.setTotalItems(totalItems(cartItems));
        shoppingCart.setTotalPrice(totalPrice(cartItems));
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }
    public ShoppingCart deleteItem(Product product,Customer customer){
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Set<CartItem> cartItems = shoppingCart.getCartItem();
        CartItem item = findCartItem(cartItems,product.getId());
        cartItems.remove(item);
        cartRepository.delete(item);
        shoppingCart.setTotalItems(totalItems(cartItems));
        shoppingCart.setTotalPrice(totalPrice(cartItems));
        return shoppingCartRepository.save(shoppingCart);
    }
    public ShoppingCart deleteAllItem(Customer customer){
        ShoppingCart shoppingCart = customer.getShoppingCart();
//        Set<CartItem> cartItems = shoppingCart.getCartItem();
//        cartItems.clear();
//        System.out.println(cartItems);
       shoppingCartRepository.deleteById(shoppingCart.getId());
        return null;
    }
}
