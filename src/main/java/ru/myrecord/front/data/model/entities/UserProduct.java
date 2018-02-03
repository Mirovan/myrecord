//package ru.myrecord.front.data.model.entities;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "user_product")
//public class UserProduct {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private Integer id;
//
//    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//}
