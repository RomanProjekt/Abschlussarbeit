/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infrastructure;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojo.Autor;
import service.ConnectionManager;

/**
 *
 * @author hp
 */
public class AutorDAO implements Serializable {
    
    int autor_id;
    
    
    
    public List<Autor> getAllAutor() {
        
        Autor retVal;
        List<Autor> listautor = new ArrayList<>();;
        
        try (
                Connection con = ConnectionManager.getInst().getConn();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from autoren")) {
            while (rs.next()) {
                retVal = new Autor(rs.getInt(1), rs.getString(2), rs.getInt(3));
                listautor.add(retVal);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  //rs.close(); stmt.close(); con.close(); because of try-with-resources Statement

        return listautor;
    }
    
    public List<Autor> getAllAutor(int id) {
        
        Autor retVal;
        List<Autor> listautor = new ArrayList<>();;
        
        try (
                Connection con = ConnectionManager.getInst().getConn();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from autoren where da_id = " + id + " order by id")) {
            while (rs.next()) {
                retVal = new Autor(rs.getInt(1), rs.getString(2), rs.getInt(3));
                listautor.add(retVal);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  //rs.close(); stmt.close(); con.close(); because of try-with-resources Statement

        return listautor;
    }
    
    public Autor read(int da_id) {
        
        Autor retVal = null;
        try (
                Connection con = ConnectionManager.getInst().getConn();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM autoren WHERE da_id = " + da_id)) {
            while (rs.next()) {
                retVal = new Autor(rs.getInt(1), rs.getString(2), rs.getInt(3));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  //rs.close(); stmt.close(); con.close(); because of try-with-resources Statement

        return retVal;
        
    }
    
    public void insert(String fullname, int da_id) {
        
        try (
                Connection con = ConnectionManager.getInst().getConn();
                PreparedStatement pstmt
                = con.prepareStatement("INSERT INTO autoren"
                        + "(fullname, da_id) VALUES (?, ?)");) {
            
            pstmt.setString(1, fullname);
            pstmt.setInt(2, da_id);
            pstmt.executeUpdate();
            pstmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  //rs.close(); stmt.close(); con.clo

    }
    
    public void insertAutorList(List<Autor> insAut, int da_id) {
        try (
                Connection con = ConnectionManager.getInst().getConn();
                PreparedStatement pstmt
                = con.prepareStatement("INSERT INTO autoren"
                        + "(fullname, da_id) VALUES (?, ?)");) {
            
            for (Autor autor : insAut) {
                
                pstmt.setString(1, autor.getFullName());
                pstmt.setInt(2, da_id);
                pstmt.executeUpdate();
            }
            pstmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  //rs.close(); stmt.close(); con.clo   
    }
    
    public void insertAutorMap(HashMap<Integer, Autor> insMap) {
        try (
                Connection con = ConnectionManager.getInst().getConn();
                PreparedStatement pstmt
                = con.prepareStatement("INSERT INTO autoren"
                        + "(fullname, da_id) VALUES (?, ?)");) {
            
            for (Map.Entry<Integer, Autor> entry : insMap.entrySet()) {
                Integer key = entry.getKey();
                Autor value = entry.getValue();
                
                pstmt.setString(1, value.getFullName());
                pstmt.setInt(2, value.getDa_id());
                pstmt.executeUpdate();
            }
            pstmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  //rs.close(); stmt.close(); con.clo   
    }
    
    public void updateAutors(HashMap<Integer, Autor> editMap) {
        try (
                Connection con = ConnectionManager.getInst().getConn();
                PreparedStatement pstmt = con.prepareStatement("UPDATE autoren set fullname = ? where id = ?");) {
            for (Map.Entry<Integer, Autor> entry : editMap.entrySet()) {
                Integer key = entry.getKey();
                Autor value = entry.getValue();
                pstmt.setString(1, value.getFullName());
                pstmt.setInt(2, value.getAutor_id());
                pstmt.execute();
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAutor(int id) {
        try (
                Connection con = ConnectionManager.getInst().getConn();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM autoren where id = ?");) {
            
            pstmt.setInt(1, id);
            pstmt.execute();
            
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAutorDip(int dip_id) {
        try (
                Connection con = ConnectionManager.getInst().getConn();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM autoren where da_id = ?");) {
            
            pstmt.setInt(1, dip_id);
            pstmt.execute();
            
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAutors(HashMap<Integer, Autor> delMap) {
        try (
                Connection con = ConnectionManager.getInst().getConn();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM autoren where id = ?");) {
            for (Map.Entry<Integer, Autor> entry : delMap.entrySet()) {
                Integer key = entry.getKey();
                Autor value = entry.getValue();
                
                pstmt.setInt(1, value.getAutor_id());
                pstmt.execute();
                
            }
            
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(int da_id) {

//        private int autor_id;
//        private String fullName;
//        private String da_id;
    }
    
}
