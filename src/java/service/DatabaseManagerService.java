/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import infrastructure.BenutzerDAO;
import infrastructure.DiplomarbeitDAO;
import java.util.ArrayList;
import java.util.List;
import pojo.Benutzer;
import pojo.Diplomarbeit;

/**
 *
 * @author dople
 */
public class DatabaseManagerService {

    private Benutzer loggedInBenutzer;
    private Benutzer b;
    private BenutzerDAO benutzerDAO;

    private DiplomarbeitDAO diplomarbeitDAO;
    private List<Diplomarbeit> dplist;

    public DatabaseManagerService() {
        benutzerDAO = new BenutzerDAO();
        loggedInBenutzer = new Benutzer();
    }

    //Benutzer
    public Benutzer load(String username) {
        b = benutzerDAO.read(username);
        return b;
    }

    public ArrayList<Benutzer> getAllBenutzer() {
        return benutzerDAO.getAllBenutzer();
    }

    public int updateBenutzer(Benutzer b) {
        return benutzerDAO.updateBenutzer(b);
    }

    public int deleteBenutzer(int id) {
        return benutzerDAO.deleteBenutzer(id);
    }

    public int insertBenutzer(Benutzer b) {
        return benutzerDAO.insert(b);
    }

    public boolean loggedIn() {
        return loggedInBenutzer.getUsername() != null;
    }

    public void loggout() {
        loggedInBenutzer = new Benutzer();
    }

    public Benutzer getB() {
        return b;
    }

    public BenutzerDAO getBenutzerDAO() {
        return benutzerDAO;
    }

    public void setB(Benutzer b) {
        this.b = b;
    }

    public void setBenutzerDAO(BenutzerDAO benutzerDAO) {
        this.benutzerDAO = benutzerDAO;
    }

    public int getNextUserId() {
        return benutzerDAO.getNextIdFromUser();
    }

    public Benutzer getLoggedInBenutzer() {
        return loggedInBenutzer;
    }

    public void setLoggedInBenutzer(Benutzer loggedInBenutzer) {
        this.loggedInBenutzer = loggedInBenutzer;
    }

    //Diplomarbeit:
    public List<Diplomarbeit> show() {

        dplist = diplomarbeitDAO.read();
        return dplist;

    }

}