package com.snce.lightcom.services;

import com.snce.lightcom.DTO.CommandeItemDTO;
import com.snce.lightcom.entities.CommandeItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArticleService {
    CommandeItem save(CommandeItem commandeItem);

    List<CommandeItem> getAllCommandeItem(Long id);
    Page<CommandeItemDTO> listItemCommande(String numero, String unite, String libelle, String article, String uniteDeMesure, int page, int size);
}
