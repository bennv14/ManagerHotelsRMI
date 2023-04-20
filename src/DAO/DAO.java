/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Hotel;
import model.Hotel;
import model.Room;

/**
 *
 * @author ben
 */
public class DAO {

    private Connection conn;
    private Statement statement;

    public DAO() {
        try {
            String url = "jdbc:mysql://localhost:3306/qlkhachsan";
            String user = "root";
            String password = "1234";
            this.conn = DriverManager.getConnection(url, user, password);
            this.statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConn() {
        return conn;
    }

    public Statement getStatement() {
        return statement;
    }

    public ArrayList<Room> getAllRooms() {
        String sql = "SELECT * FROM phong";
        ResultSet result = null;
        try {
            result = this.statement.executeQuery(sql);
            ArrayList<Room> hotelss = new ArrayList<>();

            while (result.next()) {
                Room hotels = new Room(
                        result.getString("id"),
                        result.getString("sophong"),
                        result.getString("loaiphong"),
                        result.getInt("giaphong"),
                        result.getString("idKhachSan"));
                hotelss.add(hotels);
            }

            return hotelss;

        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Room> getRoomsOfHotel(String idKhachSan) {
        String sql = "SELECT * FROM phong Where idKhachSan = '" + idKhachSan + "'";
        ResultSet result = null;
        try {
            result = this.statement.executeQuery(sql);
            ArrayList<Room> hotelss = new ArrayList<>();

            while (result.next()) {
                Room hotels = new Room(
                        result.getString("id"),
                        result.getString("sophong"),
                        result.getString("loaiphong"),
                        result.getInt("giaphong"),
                        result.getString("idKhachSan"));
                hotelss.add(hotels);
            }

            return hotelss;

        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Room> getRoomsByPriceAndType(String type, String price) {

        String strSearch = "";
//                + "Where loaiphong = '" + type + "' AND giaphong <= " + price  ;

        if (type != "tất cả" && !price.isBlank()) {
            strSearch += "Where loaiphong = '" + type + "' AND giaphong <= " + price ;
        }
        else if (type != "tất cả"){
            strSearch += "Where loaiphong = '" + type + "'";
        }
        else if(!price.isBlank()){
            strSearch += "Where giaphong <= " + price ;
        }
        String sql = "SELECT * FROM phong " + strSearch;
        System.out.println(sql);
        ResultSet result = null;

        try {
            result = this.statement.executeQuery(sql);
            ArrayList<Room> hotelss = new ArrayList<>();

            while (result.next()) {
                Room hotels = new Room(
                        result.getString("id"),
                        result.getString("sophong"),
                        result.getString("loaiphong"),
                        result.getInt("giaphong"),
                        result.getString("idKhachSan"));
                hotelss.add(hotels);
            }

            return hotelss;

        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Hotel> getAllHotels() {
        String sql = "SELECT * FROM khachsan";
        ResultSet result = null;
        try {
            result = this.statement.executeQuery(sql);
            ArrayList<Hotel> hotels = new ArrayList<>();

            while (result.next()) {
                Hotel hotel = new Hotel(
                        result.getString("id"),
                        result.getString("ten"),
                        result.getInt("sosao"),
                        result.getString("mota"));
                hotels.add(hotel);
            }

            return hotels;

        } catch (SQLException e) {
            return null;
        }
    }

    public void updateRoom(Room room) {
        System.out.println(room);
        String sql = "UPDATE phong SET "
                + "sophong = '" + room.getRoomNumber() + "', "
                + "loaiphong = '" + room.getRoomType() + "', "
                + "giaphong = " + room.getPrice()
                + " WHERE id = '" + room.getId() + "'";

        try {
            this.statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateHotel(Hotel hotel) {
        String sql = "UPDATE khachsan SET "
                + "ten = '" + hotel.getName() + "', "
                + "sosao = " + hotel.getStartRating() + ", "
                + "mota = '" + hotel.getDescription() + "' "
                + "WHERE id = '" + hotel.getId() + "'";

        try {
            this.statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteRoom(String id) {
        String sql = "DELETE FROM phong WHERE id = '" + id + "'";
        try {
            this.statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteHotel(String id) {
        String sql1 = "DELETE FROM phong WHERE idKhachSan = '" + id + "'";
        String sql2 = "DELETE FROM khachsan WHERE id = '" + id + "'";

        try {
            this.statement.executeUpdate(sql1);
            this.statement.executeUpdate(sql2);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addRoom(Room room) {
        String sql = "INSERT INTO phong "
                + "VALUES('"
                + room.getId() + "','"
                + room.getRoomNumber() + "','"
                + room.getRoomType() + "',"
                + room.getPrice() + ",'"
                + room.getIdHotel() + "')";
        try {
            this.statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addHotel(Hotel hotel) {
        String sql = "INSERT INTO khachsan "
                + "VALUES('"
                + hotel.getId() + "', '"
                + hotel.getName() + "', "
                + hotel.getStartRating() + ", '"
                + hotel.getDescription() + "')";
        try {
            this.statement.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new DAO().getRoomsByPriceAndType("thường", "1000000");
    }
}
