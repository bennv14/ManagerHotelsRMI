/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author ben
 */
public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("List Example");

        String[] listItems = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

        JList<String> list = new JList<>(listItems);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);

        frame.add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }
}
