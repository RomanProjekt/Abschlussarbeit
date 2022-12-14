/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import infrastructure.DiplomarbeitDAO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import pojo.Diplomarbeit;

/**
 *
 * @author patri
 */
public class dipSuchenBean  {
    
    
    //Alter Code

    String key = "";
    DiplomarbeitDAO obj;
    List<Diplomarbeit> daList;
    Diplomarbeit selectedDa;
    List<Diplomarbeit> recentDaList;
    boolean render;
    boolean render2;
    private boolean issearchbib;

    //favoriten auch so?
    public dipSuchenBean() {
        this.issearchbib = true;

    }

    @PostConstruct
    void init() {
        render = false;
        render2 = false;
        obj = new DiplomarbeitDAO();
        daList = new ArrayList<>();
        recentDaList = new ArrayList<>(); //favoriten aus der db laden?
    }

    public Object selectDA() {
        daList = obj.Suchleiste(key);
        if (!daList.isEmpty()) {
            render = true;
            selectedDa = daList.get(0); //null check?, andere select bar machen, bei select auf da seite weiterreferenzieren
            return null;
        }
        return null;
    }

    public Object recentDa() {
        //recentDaList.clear();
        int check = 0;
        if (!daList.isEmpty()) {
            render2 = true;
            check = 1;
            if (!recentDaList.isEmpty()) {
                for (Diplomarbeit ar : recentDaList) { //daList) {
                    //    recentDaList.add(ar);
                    if (ar.getDa_id() == selectedDa.getDa_id()) {
                        check = 0;
                    }
                }
            } else {
                //recentDaList.add(selectedDa);
            }
        }
        if (check == 1) {
            recentDaList.add(selectedDa);
        }
        return null;
    }

    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Diplomarbeit> getDaList() {
        return daList;
    }

    public void setDaList(List<Diplomarbeit> daList) {
        this.daList = daList;
    }

    public Diplomarbeit getSelectedDa() {
        return selectedDa;
    }

    public void setSelectedDa(Diplomarbeit selectedDa) {
        this.selectedDa = selectedDa;
    }

    public List<Diplomarbeit> getRecentDaList() {
        return recentDaList;
    }

    public void setRecentDaList(List<Diplomarbeit> recentDaList) {
        this.recentDaList = recentDaList;
    }

    public boolean getRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public boolean getRender2() {
        return render2;
    }

    public void setRender2(boolean render2) {
        this.render2 = render2;
    }

    public boolean isIssearchbib() {
        return issearchbib;
    }

    public void setIssearchbib(boolean issearchbib) {
        this.issearchbib = issearchbib;
    }


    public DiplomarbeitDAO getObj() {
        return obj;
    }

    public void setObj(DiplomarbeitDAO obj) {
        this.obj = obj;
    }

    
    

}
