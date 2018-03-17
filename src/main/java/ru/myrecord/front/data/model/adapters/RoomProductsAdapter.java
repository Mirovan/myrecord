package ru.myrecord.front.data.model.adapters;

import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Room;

import java.util.Set;

/**
 * Комната которой принадлежат услуги
 * */
public class RoomProductsAdapter {
    private Room room;
    private Set<Product> products;

    public RoomProductsAdapter(Room room, Set<Product> products) {
        this.room = room;
        this.products = products;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
