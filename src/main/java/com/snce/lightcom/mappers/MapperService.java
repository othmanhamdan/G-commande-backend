package com.snce.lightcom.mappers;

import com.snce.lightcom.DTO.CommandeDTO;
import com.snce.lightcom.DTO.CommandeItemDTO;
import com.snce.lightcom.DTO.FactureDTO;
import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.CommandeItem;
import com.snce.lightcom.entities.Facture;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    public CommandeDTO fromCommande(Commande commande){
        CommandeDTO commandeDTO = new CommandeDTO();
        BeanUtils.copyProperties(commande,commandeDTO);
        return commandeDTO;
    }
    public Commande fromCommandeDTO(CommandeDTO commandeDTO){
        return null;
    }
    public FactureDTO fromFacture(Facture facture){
        FactureDTO factureDTO = new FactureDTO();
        BeanUtils.copyProperties(facture,factureDTO);
        return factureDTO;
    }
    public Facture fromFactureDTO(FactureDTO factureDTO){
        Facture facture = new Facture();
        BeanUtils.copyProperties(factureDTO,facture);
        return facture;
    }
    public CommandeItemDTO fromArticle(CommandeItem commandeItem){
        CommandeItemDTO commandeItemDTO = new CommandeItemDTO();
        BeanUtils.copyProperties(commandeItem,commandeItemDTO);
        return commandeItemDTO;
    }
    public CommandeItem fromArticleDTO(CommandeItemDTO commandeItemDTO){
        CommandeItem commandeItem = new CommandeItem();
        BeanUtils.copyProperties(commandeItemDTO,commandeItem);
        return commandeItem;
    }
}
