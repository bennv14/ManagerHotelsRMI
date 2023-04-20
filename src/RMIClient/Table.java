/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIClient;

import RMIInterface.RMIInterface;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Hotel;
import model.Room;

/**
 *
 * @author ben
 */
public class Table extends JScrollPane {

    public DefaultTableModel model;
    private String[] column;
    private JTable table;

    public Table(String[] column) throws RemoteException {
        super();
        this.column = column;
        this.model = new DefaultTableModel(null, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);
        
        
        
        table.setRowHeight(25);
        this.setViewportView(table);
    }

    public DefaultTableModel getModel() {
        return this.model;
    }
    
    public JTable getTable(){
        return this.table;
    }
    
    public void setRowsRooms(ArrayList<Room> values ){
        this.model.setRowCount(0);
        for(Room value : values){
            this.model.addRow(value.toArray());
        }
        
    }
    
    public void setRowsHotels(ArrayList<Hotel> values ){
        this.model.setRowCount(0);
        for(Hotel value : values){
            this.model.addRow(value.toArray());
        }
        
    }
    
    public int getSelectedRow(){
        return this.table.getSelectedRow();
    }
    
    public ArrayList<String> getValuesAtRow(int row){
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < this.column.length; i++){
            result.add((String)this.table.getValueAt(row, i));
        }
        return result;
    }
    
    
    public ListSelectionModel getSelectionModel(){
        return this.table.getSelectionModel();
    }
    
}

//table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent event) {
//                if (!event.getValueIsAdjusting()) {
//                    int selectedRow = table.getSelectedRow();
//                    ArrayList<String> data = new ArrayList<>();
//                    int length = column.length;
//                    for (int i = 0; i < length; i++) {
//                        data.add((String) table.getValueAt(selectedRow, i));
//                    }
//                    UpdateProperties update = new UpdateProperties(column, data, rmi, typeSelected, priceSelected);
//                    update.addWindowListener(new WindowAdapter() {
//                        @Override
//                        public void windowClosed(WindowEvent e) {
//                            getValues();
//                            getValues();
//                            getValues();
//                        }
//                    });
//                }
//            }
//        });
