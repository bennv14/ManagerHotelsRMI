/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIClient;

import RMIInterface.RMIInterface;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import model.Hotel;
import model.Room;

/**
 *
 * @author ben
 */
public class UpdateProperties extends JDialog {

    private ArrayList<JTextField> inpText;
    private String[] properties;
    private RMIInterface rmi;

    public UpdateProperties(String[] properties, ArrayList<String> data, RMIInterface rmi) {

        if (properties.length == 4) {
            this.setTitle("Chỉnh sửa khách sạn");
        } else {
            this.setTitle("Chỉnh sửa phòng");
        }
        this.properties = properties;
        this.rmi = rmi;
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        int length = properties.length;
        JPanel inpPanel = new JPanel(new GridLayout(length, 2, 30, 30));
        inpPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.inpText = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            inpPanel.add(new JLabel(properties[i]));
            this.inpText.add(new JTextField(data.get(i)));
            if (i == 0) {
                this.inpText.get(i).setEditable(false);
            }
            inpPanel.add(this.inpText.get(i));
            this.inpText.get(i).setPreferredSize(new Dimension(170, 25));

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
                } catch (RemoteException ex) {
                    System.out.println(".actionPerformed()");;
                } catch (NotBoundException ex) {
                    Logger.getLogger(UpdateProperties.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JButton cancel = new JButton("Hủy");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cancel();
                } catch (RemoteException ex) {
                    Logger.getLogger(UpdateProperties.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(UpdateProperties.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JButton delete = new JButton("Xóa");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delete();
                } catch (RemoteException ex) {
                    Logger.getLogger(UpdateProperties.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(UpdateProperties.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        actPanel.add(save);
        actPanel.add(cancel);
        actPanel.add(delete);
        panel.add(actPanel, BorderLayout.SOUTH);

        this.setSize(350, (this.properties.length + 1) * 65);
        this.setLocation(300, 300);
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void save() throws RemoteException, NotBoundException {
        if (this.properties.length == 4) {
            this.rmi.updateHotel(new Hotel(
                    inpText.get(0).getText(),
                    inpText.get(1).getText(),
                    Integer.parseInt(inpText.get(2).getText()),
                    inpText.get(3).getText()
            ));

            new UIListHotels();
        } else {
            this.rmi.updateRoom(new Room(
                    inpText.get(0).getText(),
                    inpText.get(1).getText(),
                    inpText.get(2).getText(),
                    Integer.parseInt(inpText.get(3).getText()),
                    inpText.get(4).getText()));
            new UIListRooms(0, "");
        }
        this.dispose();
    }

    public void delete() throws RemoteException, NotBoundException {
        if (this.properties.length == 4) {
            this.rmi.deleteHotel(this.inpText.get(0).getText());
            new UIListHotels();
        } else {
            this.rmi.deleteRoom(this.inpText.get(0).getText());
            new UIListRooms(0, "");
        }

        this.dispose();
    }

    public void cancel() throws RemoteException, NotBoundException {
        if (this.properties.length == 4) {
            new UIListHotels();
        } else {
            new UIListRooms(0, "");
        }

        this.dispose();
    }

}
