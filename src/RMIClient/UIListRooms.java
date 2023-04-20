/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIClient;

import static RMIClient.UIListHotels.COLUMN;
import RMIInterface.RMIInterface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author ben
 */
public class UIListRooms extends JFrame {

    public static final String[] COLUMN = {"ID", "Số phòng", "Loại phòng", "Giá phòng", "Mã khách sạn"};
    public static final String[] TYPE_ROOM = {"tất cả", "thường", "vip"};
 
    private RMIInterface rmi;
    private DefaultTableModel model;
    private Table table;
    private int typeSelected;
    private String priceSelected;
    private JComboBox<String> typeRoom;
    private JTextField priceRoom;

    public UIListRooms(int typeSelected, String priceSelected) throws RemoteException, NotBoundException {
        connectRMI();
        this.typeSelected = typeSelected;
        this.priceSelected = priceSelected;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setTitle("Danh sách phòng");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel searchBar = new JPanel(new FlowLayout());
        searchBar.add(new JLabel("Loại phòng:"));
        this.typeRoom = new JComboBox<String>(TYPE_ROOM);
        typeRoom.setSelectedIndex(this.typeSelected);
        this.priceRoom = new JTextField(priceSelected);
        priceRoom.setPreferredSize(new Dimension(150, 30));
        
        
        searchBar.add(typeRoom);
        searchBar.add(new JLabel("Giá phòng:"));
        searchBar.add(priceRoom);
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    search();
                } catch (RemoteException ex) {
                    Logger.getLogger(UIListRooms.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        searchBar.add(btnSearch);

        panel.add(searchBar, BorderLayout.NORTH);

        this.table = new Table(COLUMN);
        this.table.setRowsRooms(rmi.getAllRooms());
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    dispose();
                    int selectedRow = table.getSelectedRow();
                    ArrayList<String> data = table.getValuesAtRow(selectedRow);
                    UpdateProperties update = new UpdateProperties(COLUMN, data,rmi);
                    
                }
            }
        });
        panel.add(this.table, BorderLayout.CENTER);

        JButton addHotel = new JButton("Thêm phòng");
        addHotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddElement add = new AddElement(COLUMN, rmi);
            }
        });
        JButton callListHotels = new JButton("Xem danh sách khách sạn");
        callListHotels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UIListHotels();
                    dispose();
                } catch (Exception ex) {
                    System.out.println("lỗi gọi UIListRooms: " + ex);
                }
            }
        });
        JPanel actPanel = new JPanel(new GridLayout(1, 2, 40, 40));
        actPanel.setBorder(new EmptyBorder(20, 70, 10, 70));
        actPanel.add(addHotel);
        actPanel.add(callListHotels);
        panel.add(actPanel, BorderLayout.SOUTH);

        int n = Window.getWindows().length;
        if (n >= 2) {
            Window.getWindows()[n - 2].dispose();
        }

        this.add(panel);
        this.setVisible(true);
    }

    public void setTypeSelected(int typeSelected) {
        this.typeSelected = typeSelected;
    }

    public int getTypeSelected() {
        return typeSelected;
    }
    
    public void search() throws RemoteException{
        String type = TYPE_ROOM[this.typeRoom.getSelectedIndex()];
        String price = this.priceRoom.getText();
        this.table.setRowsRooms(rmi.getRoomsByTypeAndPrice(type, price));
    }

    public void connectRMI() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        this.rmi = (RMIInterface) registry.lookup("rmi");
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        new UIListRooms(0, "");
    }

}
