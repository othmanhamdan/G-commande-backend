package com.snce.lightcom.proj;

import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.Fournisseur;
import org.springframework.data.rest.core.config.Projection;

public interface FournisseurProjection {
    String getLibelle();

}
