/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.ArrayList;

/**
 *
 * @author ben
 */
public class Hotel implements Serializable{
    
    private String id;
    private String name;
    private int startRating;
    private String description;
    
    public Hotel(String id, String name, int startRating, String description) {
        this.id = id;
        this.name = name;
        this.startRating = startRating;
        this.description = description;
    }
    
    public Hotel(ArrayList<String> data) {
        this.id = data.get(0);
        this.name = data.get(1);
        this.startRating = Integer.parseInt(data.get(2));
        this.description = data.get(3);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartRating() {
        return startRating;
    }

    public void setStartRating(int startRating) {
        this.startRating = startRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String[] toArray(){
        String[] result = {this.getId(), this.getName(),String.valueOf(this.getStartRating()), this.getDescription()};
        return result;
    }

    @Override
    public String toString() {
        return "Hotel{" + "id=" + id + ", name=" + name + ", startRating=" + startRating + ", description=" + description + '}';
    }
    
    
    
}
