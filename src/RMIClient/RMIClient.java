/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIClient;

import RMIInterface.RMIInterface;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import model.Hotel;
import model.Room;

/**
 *
 * @author ben
 */
public class RMIClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        RMIInterface rmiServer = (RMIInterface) registry.lookup("rmi");
        rmiServer.deleteHotel("KS06");
        ArrayList<Room> a = rmiServer.getRoomsByTypeAndPrice(2, "");
//        ArrayList<Hotel> hotels = rmiServer.getAllHotels();
//        for(Hotel hotel : hotels){
//            System.out.println(hotel);
//        }
        for(Room hotel : a){
            System.out.println(hotel);
        }

    }

}
