/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIClient;

import RMIInterface.RMIInterface;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Hotel;

/**
 *
 * @author ben
 */
public class UIListRoomsOfHotel extends JFrame {
    public static final String[] COLUMN = {"ID", "Số phòng", "Loại phòng", "Giá phòng", "Mã khách sạn"};
    public static final String[] COLUMN_HOTEL = {"ID", "Tên", "Số sao", "Mô tả"};
    public static final String[] TYPE_ROOM = {"tất cả", "thường", "vip"};
 
    private ArrayList<String> hotelData;
    private RMIInterface rmi;
    private Table table;
    private JComboBox<String> typeRoom;
    private JTextField priceRoom;

    public UIListRoomsOfHotel(ArrayList<String> hotelData) throws RemoteException, NotBoundException {
        connectRMI();
        this.hotelData = hotelData;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setTitle("Danh sách phòng của " + hotelData.get(1));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel searchBar = new JPanel(new FlowLayout());
        searchBar.add(new JLabel("Loại phòng:"));
        this.typeRoom = new JComboBox<String>(TYPE_ROOM);
        typeRoom.setSelectedIndex(0);
        this.priceRoom = new JTextField("");
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
        this.table.setRowsRooms(rmi.getRoomsOfHotel(hotelData.get(0)));
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    dispose();
                    int selectedRow = table.getSelectedRow();
                    ArrayList<String> data = table.getValuesAtRow(selectedRow);
                    new UpdateRoomOfHotel(COLUMN, data,rmi, hotelData);
                    
                }
            }
        });
        panel.add(this.table, BorderLayout.CENTER);
        
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
        
        JButton updateHotel = new JButton("Chỉnh sửa khách sạn");
        updateHotel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UpdateProperties(COLUMN_HOTEL, hotelData, rmi);
            }
        });
        
        JPanel actPanel = new JPanel(new GridLayout(1, 3,20,20));
        actPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        actPanel.add(callListHotels);
        actPanel.add(updateHotel);
        panel.add(actPanel, BorderLayout.SOUTH);

        int n = Window.getWindows().length;

        this.add(panel);
        this.setVisible(true);
    }
    
    public void search() throws RemoteException{
        int type = this.typeRoom.getSelectedIndex();
        String price = this.priceRoom.getText();
        System.out.println(price);
        this.table.setRowsRooms(rmi.getRoomsByTypeAndPriceAndIdKhachSan(type, price, this.hotelData.get(0)));
    }

    public void connectRMI() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        this.rmi = (RMIInterface) registry.lookup("rmi");
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        ArrayList<String> data = new ArrayList<>();
        data.add("KS04");
        data.add("Khách san 4");
        data.add("4");
        data.add("Khách sạn 4 sao");
        new UIListRoomsOfHotel(data);
    }

}
