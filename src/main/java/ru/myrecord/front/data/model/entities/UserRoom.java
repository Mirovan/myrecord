//package ru.myrecord.front.data.model.entities;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "user_room")
//public class UserRoom {
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
//    @ManyToMany(mappedBy = "room", cascade = CascadeType.ALL)
//    @JoinColumn(name = "room_id")
//    private Room room;
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Room getRoom() {
//        return room;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }
//
//    public UserRoom(User user, Room room) {
//        this.user = user;
//        this.room = room;
//    }
//}
