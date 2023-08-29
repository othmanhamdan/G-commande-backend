package com.example.lightcom.web;

import com.example.lightcom.entities.Commande;
import com.example.lightcom.services.CommandeService;
import com.sun.deploy.net.HttpResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@AllArgsConstructor
@RequestMapping("/commande")
public class CommandeController {
    private final CommandeService commandeService;

    @PostMapping("")
    public ResponseEntity<Commande> saveCommande(@RequestBody Commande commande){
        return ResponseEntity.ok(commandeService.save(commande));
    }
    @PutMapping("")
    public ResponseEntity<Commande> updateCommande(@RequestBody Commande commande){
        return ResponseEntity.ok(commandeService.update(commande));
    }
    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id){
        commandeService.delete(id);
    }
    @PutMapping("/valide")
    public void valide(@RequestBody Commande commande){
        commandeService.valide(commande);
    }
    @PutMapping("/cloture")
    public void cloture(@RequestBody Commande commande){
        commandeService.cloture(commande);
    }
    @GetMapping("/getAllCommande")
    public Page<Commande> getAllCommande(@RequestParam String unite,
                                         @RequestParam String numero,
                                         @RequestParam String libelle,
                                         @RequestParam String nom,
                                         @RequestParam int page,
                                         @RequestParam int size){
       return commandeService.getAllCommande(unite,numero,libelle,nom,page, size);
    }
    @GetMapping("/getAllNonFacturer/{page}/{size}")
    public Page<Commande> getAllCommandeNonFacture(@RequestBody Commande commande,
                                                   @PathVariable int page,
                                                   @PathVariable int size){
        return commandeService.getAllCommandeNonFacturer(commande, page, size);
    }

    @GetMapping("/countCommandeNonFacturer")
    public Long countCommandeNonFacturer(@RequestBody Commande commande){
        return commandeService.countCommandeNonFacturer(commande);
    }

    @GetMapping("/countCommande")
    public Long countCommande(@RequestBody Commande commande){
        return commandeService.countCommande(commande);
    }
    @GetMapping("/getAll/{page}/{size}")
    public Page<Commande> getAlls(@PathVariable int page, @PathVariable int size) {
        //TimeUnit.SECONDS.sleep(5);
        return commandeService.getAll(page,size);
    }
    @GetMapping("/getAlls")
    public List<Commande> getAll(){
        return commandeService.getAlls();
    }
}
