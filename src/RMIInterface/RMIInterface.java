/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Hotel;
import model.Room;

/**
 *
 * @author ben
 */
public interface RMIInterface extends Remote{
    public ArrayList<Room> getAllRooms() throws RemoteException;
    public ArrayList<Room> getRoomsByTypeAndPrice(String type, String price) throws RemoteException;
    public ArrayList<Hotel> getAllHotels() throws RemoteException;
    public void updateRoom (Room room) throws RemoteException;
    public void updateHotel (Hotel hotel) throws RemoteException;
    public void deleteRoom(String id) throws RemoteException;
    public void deleteHotel(String id) throws RemoteException;
    public void addRoom(Room room) throws RemoteException;
    public void addHotel(Hotel hotel) throws RemoteException;
}
