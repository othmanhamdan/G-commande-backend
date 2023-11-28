package com.snce.lightcom.web;

import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.services.CommandeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/commande")
public class CommandeController {
    private final CommandeService commandeService;

    @PostMapping("")
    @PostAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<Commande> saveCommande(@RequestBody Commande commande){
        return ResponseEntity.ok(commandeService.save(commande));
    }
    @PutMapping("")
    @PostAuthorize("hasAnyAuthority('USER','ADMIN')")
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
    @PostMapping("/cloture")
    public Commande cloture(@RequestBody Commande commande){
        return commandeService.cloture(commande);
    }

    @GetMapping("/getAllCommande")
    @CrossOrigin(origins = "http://localhost:4200")
    @PostAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Commande> getAllCommandeController( @RequestParam String unite,
                                                    @RequestParam String numero,
                                                    @RequestParam String libelle,
                                                    @RequestParam String nom,
                                                    @RequestParam int page,
                                                    @RequestParam int size){
       return commandeService.getAllCommande(unite,numero,libelle,nom,page, size);
    }
    @GetMapping("/getAllCommandeNonCloturer")
    public Page<Commande> getAllCommandeNonCloturer(@RequestParam String unite,
                                                    @RequestParam String nom,
                                                    @RequestParam int page,
                                                    @RequestParam int size){
        return commandeService.getAllCommandeNonCloturer(unite,nom,page, size);
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
    @GetMapping("/{id}")
    public Optional<Commande> getById(@PathVariable Long id){
        return commandeService.getCommandeById(id);
    }
}
