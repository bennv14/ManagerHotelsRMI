/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIClient;

import RMIInterface.RMIInterface;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Hotel;
import model.Room;

/**
 *
 * @author ben
 */
public class AddElement extends JFrame {

    private ArrayList<JTextField> inpText;
    private String[] properties;
    private RMIInterface rmi;

    public AddElement(String[] properties, RMIInterface rmi) {
        if (properties.length == 4) {
            this.setTitle("Thêm khách sạn");
        } else {
            this.setTitle("Thêm phòng");
        }
        
        this.properties = properties;
        this.rmi = rmi;
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        int length = properties.length;
        JPanel inpPanel = new JPanel(new GridLayout(length, 2, 30, 30));
        inpPanel.setBorder(new EmptyBorder(0, 0, 20, 20));
        this.inpText = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            inpPanel.add(new JLabel(properties[i]));
            this.inpText.add(new JTextField());
            inpPanel.add(this.inpText.get(i));
        }
        inpPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(inpPanel);

        Panel actPanel = new Panel(new GridLayout(1, 3, 10, 10));
        JButton save = new JButton("Lưu");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    save();
                } catch (Exception ex) {
                    System.err.println("Add Element:" + ex);
                }
            }
        });
        JButton cancel = new JButton("Hủy");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cancel();
                } catch (Exception ex) {
                    System.err.println("Add Element:" + ex);

                }
            }
        });
        actPanel.add(save);
        actPanel.add(cancel);

        panel.add(actPanel, BorderLayout.SOUTH);

        int n = Window.getWindows().length;
        if (n >= 2) {
            Window.getWindows()[n - 2].dispose();
        }

        this.setSize(350, 370);
        this.setLocation(200, 200);
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void save() throws RemoteException, NotBoundException {
        this.dispose();
        if (this.properties.length == 4) {
            this.rmi.addHotel(new Hotel(
                    inpText.get(0).getText(),
                    inpText.get(1).getText(),
                    Integer.parseInt(inpText.get(2).getText()),
                    inpText.get(3).getText()));
            new UIListHotels();
        } else {
            this.rmi.addRoom(new Room(
                    inpText.get(0).getText(),
                    inpText.get(1).getText(),
                    inpText.get(2).getText(),
                    Integer.parseInt(inpText.get(3).getText()),
                    inpText.get(4).getText()));
            System.out.println();
            new UIListRooms();
        }
        
    }

    public void cancel() throws RemoteException, NotBoundException {
        if (this.properties.length == 4) {
            new UIListHotels();
        } else {
            new UIListRooms();
        }
        this.dispose();
    }

}
