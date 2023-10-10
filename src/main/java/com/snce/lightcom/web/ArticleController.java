package com.snce.lightcom.web;

import com.snce.lightcom.DTO.CommandeItemDTO;
import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.CommandeItem;
import com.snce.lightcom.services.ArticleService;
import com.snce.lightcom.services.CommandeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;
    private CommandeService commandeService;


    @PostMapping("")
    public ResponseEntity<CommandeItem> saveCommande(@RequestBody CommandeItem commandeItem){
        return ResponseEntity.ok(articleService.save(commandeItem));
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<CommandeItem>> getAll(@PathVariable Long id){
        return ResponseEntity.ok(articleService.getAllCommandeItem(id));
    }
    @GetMapping("/AllArticle")
    public Page<CommandeItemDTO> listItems(@RequestParam String numero,
                                           @RequestParam String unite,
                                           @RequestParam String libelle,
                                           @RequestParam String article,
                                           @RequestParam String uniteDeMesure,
                                           @RequestParam int page,
                                           @RequestParam int size){
        return articleService.listItemCommande(numero,unite,libelle,article,uniteDeMesure,page,size);
    }
    @GetMapping("/countAllItems")
    public Long countListItems(@RequestBody CommandeItem commandeItem){
        return commandeService.countListItemCommande(commandeItem);
    }
}
