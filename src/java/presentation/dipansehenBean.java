/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import infrastructure.DiplomarbeitDAO;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import pojo.Diplomarbeit;
import service.DatabaseManagerService;

/**
 *
 * @author hp
 */
public class dipansehenBean {

    private DiplomarbeitDAO dbService =  new DiplomarbeitDAO();
    private Diplomarbeit aktDip;
    
    //Variablen
    private String titel;
    private String autor;
    private String schule;
    private Date date;
    private int seitenanzahl = 0;
    private DatabaseManagerService dms;
    private int aktuelle_id;
    private String buttonId;
    private String result;
    private String imagepath;

    private String bildnamegleich;
    private String pdfnamegleich;
    
    //downlaod-Counter
    private int download_count;
    
    
    
    

    public dipansehenBean() {

    }

    @PostConstruct
    void init() {
//        dbService = new DiplomarbeitDAO();
    }

    public DatabaseManagerService getDms() {
        return dms;
    }

    public void setDms(DatabaseManagerService dms) {
        this.dms = dms;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String Titel) {
        this.titel = Titel;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSchule() {
        return schule;
    }

    public void setSchule(String schule) {
        this.schule = schule;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSeitenanzahl() {
        return seitenanzahl;
    }

    public void setSeitenanzahl(int seitenanzahl) {
        this.seitenanzahl = seitenanzahl;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public DiplomarbeitDAO getDbService() {
        return dbService;
    }

    public void setDbService(DiplomarbeitDAO dbService) {
        this.dbService = dbService;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public int getAktuelle_id() {
        return aktuelle_id;
    }

    public void setAktuelle_id(int aktuelle_id) {
        this.aktuelle_id = aktuelle_id;
    }

    public String getBildnamegleich() {
        return bildnamegleich;
    }

    public void setBildnamegleich(String bildnamegleich) {
        this.bildnamegleich = bildnamegleich;
    }

    public String getPdfnamegleich() {
        return pdfnamegleich;
    }

    public void setPdfnamegleich(String pdfnamegleich) {
        this.pdfnamegleich = pdfnamegleich;
    }

    public Diplomarbeit getAktDip() {
        return aktDip;
    }

    public void setAktDip(Diplomarbeit aktDip) {
        this.aktDip = aktDip;
    }

    //Funktionen
    public String werteanzeigen(Diplomarbeit dip) {
        this.aktDip = dip;
        this.autor = dms.getOneAutor(aktDip.getDa_id()).getFullName();
        this.schule = dms.getOneSchule(aktDip.getSchule_id()).getName();
        dms.getAktuellPicture(this.aktDip);
        return "dipansehen.xhtml";
    }

    
  //---------------------Diplomarbeit download---------------------------------
    public Object download() throws IOException {

        FacesContext fc = (FacesContext) FacesContext.getCurrentInstance();
        ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
        ExternalContext externalContext = fc.getExternalContext();

        externalContext.responseReset();
        externalContext.setResponseContentType(aktDip.getTitle() + "/.pdf");
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + aktDip.getTitle());

        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + aktDip.getTitle() + ".pdf" + "\"");
        String server_diplomarbeit_pfad = sc.getRealPath("").replaceAll("\\\\", "/").replaceAll("/build", "") + "/resources/pdf/";
        File file = new File(server_diplomarbeit_pfad + aktDip.getTitle() + ".pdf");

        OutputStream outputStream;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            outputStream = externalContext.getResponseOutputStream();
            byte[] buffer = new byte[5000];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }

//        Files.copy(file.toPath(), outputStream);
        fc.responseComplete();
        
        
        return null;

    }

    
    
    

    public String imagepath_auslesen(int id) {
        Diplomarbeit dip = dms.getDiplomarbeit(id);
//        System.out.println(dip);
        String var_imagepath = dip.getBild();
//        System.out.println(var_imagepath);
        return var_imagepath;
    }

    public String bildanzeigen() {
        return this.imagepath;
    }

    //Favouriten
    public void speichern() {
        int b_id = dms.getLoggedInBenutzer().getUser_id();
        int res = dms.insertFavouriten(aktDip.getDa_id(), b_id);
        if (res == 1) {
            titel = "Success";
        };
    }

    public void löschenDiplomarbeit(ActionEvent event) {
        dms.deleteDiplomarbeit(aktDip);
    }
    
    
    //-----------------Download-Count-Diplomarbeit-------------------------
    
    public void download_count_diplomarbeit(ActionEvent ex) {
        
        download_count =  (int) ex.getSource();
        download_count += 1;
        dbService.count(download_count, aktDip);
                     
    }
    
    


//----------------Alter Code - Diplomarbeit ansehen--------------------------

    public void diplomarbeitansehen() {

        FacesContext fc = (FacesContext) FacesContext.getCurrentInstance();
        ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
        String server_diplomarbeit_pfad = sc.getRealPath("").replaceAll("\\\\", "/").replaceAll("/build", "") + "/resources/pdf/";

        try {

            //Pfad anpassen
            File pdfFile = new File(server_diplomarbeit_pfad + aktDip.getTitle() + ".pdf");

            if (pdfFile.exists()) {

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Desktop is not supported!");

                }

            } else {
                System.out.println("File is not exists!");
            }

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
}