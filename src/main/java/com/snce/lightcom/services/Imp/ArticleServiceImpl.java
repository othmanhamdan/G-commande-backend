package com.snce.lightcom.services.Imp;

import com.snce.lightcom.DTO.CommandeItemDTO;
import com.snce.lightcom.DTO.FactureDTO;
import com.snce.lightcom.Utils.UtilsDate;
import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.CommandeItem;
import com.snce.lightcom.mappers.MapperService;
import com.snce.lightcom.repository.CommandeItemReposiroty;
import com.snce.lightcom.repository.CommandeRepository;
import com.snce.lightcom.services.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private CommandeItemReposiroty commandeItemReposiroty;
    private CommandeRepository commandeRepository;
    private MapperService mapperService;
    @Override
    public CommandeItem save(CommandeItem commandeItem) {
        commandeItem.getCommande().setId(commandeItem.getCommande().getId());
        commandeRepository.save(commandeItem.getCommande());
        return commandeItemReposiroty.save(commandeItem);
    }

    @Override
    public List<CommandeItem> getAllCommandeItem(Long id) {
        return commandeItemReposiroty.getArticleById(id);
    }
    @Override
    public Page<CommandeItemDTO> listItemCommande(String numero,String unite,String libelle,String article,String uniteDeMesure, int page, int size) {

        List<CommandeItem> art = commandeItemReposiroty.listItemsCommande(numero,unite, libelle,article,uniteDeMesure, PageRequest.of(page,size));
        List<CommandeItemDTO> commandeItemDTOS = art.stream()
                .map(findArticle -> mapperService.fromArticle(findArticle)).collect(Collectors.toList());
        return convertListToPage(commandeItemDTOS,PageRequest.of(page,size));
    }
    public Page<CommandeItemDTO> convertListToPage(List<CommandeItemDTO> list, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<CommandeItemDTO> pageList;

        if (list.size() < startItem) {
            pageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            pageList = list.subList(startItem, toIndex);
        }

        return new PageImpl<>(pageList, pageable, list.size());

    }
}
