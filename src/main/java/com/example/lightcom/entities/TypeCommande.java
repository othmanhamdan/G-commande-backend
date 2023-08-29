package com.example.lightcom.entities;

import lombok.Getter;

public enum TypeCommande {
    ACHATLOCAL("ACHATLOCAL"),IMPORTATION("IMPORTATION");

    @Getter
    protected String libelle;

    TypeCommande(String libelle){
        this.libelle = libelle;
    }

    public String toString() {
        return libelle.toString();
    }
}
