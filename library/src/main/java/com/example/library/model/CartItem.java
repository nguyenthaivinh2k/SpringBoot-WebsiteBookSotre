package com.example.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;
    private int quantity;
    private double totalPrice;
//    private double unitPrice;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id",referencedColumnName = "shopping_cart_id")
    private ShoppingCart cart;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;

}
