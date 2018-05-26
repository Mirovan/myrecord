package ru.myrecord.front.data.model.adapters;

import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;

import java.time.LocalDate;

public class UserProductAdapter {
    private User client;
    private Product product;
    private String date;

    public UserProductAdapter(User client, Product product, String date) {
        this.client = client;
        this.product = product;
        this.date = date;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
