/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIServer;

import DAO.DAO;
import RMIInterface.RMIInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import model.Hotel;
import model.Room;

/**
 *
 * @author ben
 */
public class RMIServer extends UnicastRemoteObject implements RMIInterface{
    
    private DAO dao;

    public RMIServer() throws RemoteException {
        super();
        this.dao = new DAO();
    }
    
    @Override
    public ArrayList<Room> getAllRooms() throws RemoteException {
        return this.dao.getAllRooms();
    }
    
    public ArrayList<Room> getRoomsOfHotel(String id) throws RemoteException{
        return this.dao.getRoomsOfHotel(id);
    }
    
    @Override
    public ArrayList<Hotel> getAllHotels() throws RemoteException {
        return this.dao.getAllHotels();
    }

    @Override
    public ArrayList<Room> getRoomsByTypeAndPrice(int type, String price) throws RemoteException {
        return this.dao.getRoomsByPriceAndType(type, price);
    }

    @Override
    public void updateRoom(Room room) throws RemoteException {
        this.dao.updateRoom(room);
    }

    @Override
    public void updateHotel(Hotel hotel) throws RemoteException {
        this.dao.updateHotel(hotel);
    }

    @Override
    public void deleteRoom(String id) throws RemoteException {
        this.dao.deleteRoom(id);
    }

    @Override
    public void deleteHotel(String id) throws RemoteException {
        this.dao.deleteHotel(id);
    }

    @Override
    public void addRoom(Room room) throws RemoteException {
        this.dao.addRoom(room);
    }

    @Override
    public void addHotel(Hotel hotel) throws RemoteException {
        this.dao.addHotel(hotel);
    }

    @Override
    public ArrayList<Room> getRoomsByTypeAndPriceAndIdKhachSan(int type, String price, String idKhachSan) throws RemoteException {
        return this.dao.getRoomsByPriceAndTypeAndIdKhachSan(type, price, idKhachSan);
    }

    
}
