package com.example.lightcom.web;

import com.example.lightcom.entities.CommandeItem;
import com.example.lightcom.services.CommandeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/commandeItem")
public class CommandeItemController {
    private CommandeService commandeService;

    @GetMapping("/AllItems/{page}/{size}")
    public Page<CommandeItem> listItems(@RequestBody CommandeItem commandeItem,
                                        @PathVariable int page,
                                        @PathVariable int size){
        return commandeService.listItemCommande(commandeItem,page,size);
    }
    @GetMapping("/countAllItems")
    public Long countListItems(@RequestBody CommandeItem commandeItem){
        return commandeService.countListItemCommande(commandeItem);
    }

}
