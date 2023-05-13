/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIClient;

import RMIInterface.RMIInterface;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ben
 */
public class UIListHotels extends JFrame {

    public static final String[] COLUMN = {"ID", "Tên", "Số sao", "Mô tả"};
    private RMIInterface rmi;
    private Table table;

    public UIListHotels() throws RemoteException, NotBoundException {
        super();
        connectRMI();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setTitle("Danh sách khách sạn");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.table = new Table(COLUMN);
        this.table.setRowsHotels(rmi.getAllHotels());
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    dispose();
                    int selectedRow = table.getSelectedRow();
                    ArrayList<String> data = table.getValuesAtRow(selectedRow);
                    System.out.println(data);
                    try {
                        new UIListRoomsOfHotel(data);
                    } catch (RemoteException ex) {
                        Logger.getLogger(UIListHotels.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotBoundException ex) {
                        Logger.getLogger(UIListHotels.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        });
        panel.add(this.table, BorderLayout.CENTER);

        JButton addHotel = new JButton("Thêm khách sạn");
        addHotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddElement add = new AddElement(COLUMN, rmi);
            }
        });
        JButton callListRooms = new JButton("Xem danh sách phòng");
        callListRooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UIListRooms();
                    dispose();
                } catch (Exception ex) {
                    System.out.println("lỗi gọi UIListRooms: " + ex);
                }
            }
        });
        JPanel actPanel = new JPanel(new GridLayout(1, 2, 40, 40));
        actPanel.setBorder(new EmptyBorder(20, 80, 10, 80));
        actPanel.add(addHotel);
        actPanel.add(callListRooms);
        panel.add(actPanel, BorderLayout.SOUTH);

        this.add(panel);
        this.setVisible(true);
    }

    public void connectRMI() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        this.rmi = (RMIInterface) registry.lookup("rmi");
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        new UIListHotels();
    }

}
