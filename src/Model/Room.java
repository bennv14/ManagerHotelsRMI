/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class Room implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String roomNumber;
    private String roomType;
    private int price;
    private String idHotel;

    public Room(String id, String roomNumber, String roomType, int price, String idHotel) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.idHotel = idHotel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }
    
    public String[] toArray(){
        String[] result = {this.getId(), this.getRoomNumber(), this.getRoomType(), String.valueOf(this.getPrice()), this.getIdHotel()};
        return result;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", roomNumber=" + roomNumber + ", roomType=" + roomType + ", price=" + price + ", idHotel=" + idHotel + '}';
    }
    
}
