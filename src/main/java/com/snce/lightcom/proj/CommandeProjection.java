package com.snce.lightcom.proj;



import java.util.Date;

public interface CommandeProjection {
    String getNumero();
    Date getDate();
    String getUnite();
    String getModePaiment();
    String getDelaiLivraison();
    String getDelaiPaiment();
    String getLieuLivraison();
    FournisseurProjection getFournisseur();



}
