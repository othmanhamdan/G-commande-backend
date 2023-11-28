package com.snce.lightcom.proj;


public interface CommandeItemProjection {
    String getReferenceArticle();
    String getArticle();
    String getUniteMesure();
    Integer getQuantite();
    Float getPrixUnitaire();
    Float getMontant();
    CommandeProjection getCommande();





}
