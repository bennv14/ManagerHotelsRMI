/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIServer;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author ben
 */
public class RunServer {
    public static void main(String[] args) throws Exception {
        RMIServer rmiServer = new RMIServer();
        Registry registry = LocateRegistry.createRegistry(1099);
        Naming.rebind("rmi", rmiServer);
        System.out.println("server running");
    }
}
