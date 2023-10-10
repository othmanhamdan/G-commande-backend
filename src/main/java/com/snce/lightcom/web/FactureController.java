package com.snce.lightcom.web;

import com.snce.lightcom.DTO.FactureDTO;
import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.Facture;
import com.snce.lightcom.services.FactureService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/facture")
public class FactureController {
    private FactureService factureService;

    @PostMapping("")
    public ResponseEntity<Facture> save(@RequestBody Facture facture){
        return ResponseEntity.ok(factureService.save(facture));
    }
    @PutMapping("/recueParCompta")
    public void recueParCompta(@RequestBody Facture facture){
        factureService.recueParCompta(facture);
    }
    @PutMapping("")
    @PostAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void reglee(@RequestBody Facture facture){
        factureService.reglee(facture);
    }
    @GetMapping("/getByCommande")
    public List<Facture> getByCommandeItems(@RequestBody Commande commande){
        return factureService.getByCommande(commande);
    }
    /*@GetMapping("/listEchue/{montantMin}/{MontantMax}/{page}/{size}")
    public List<Facture> factureList(@RequestBody Facture facture,
                                     @PathVariable double montantMin,
                                     @PathVariable  double MontantMax,
                                     @PathVariable  int page,
                                     @PathVariable  int size){
        return factureService.listEchue(facture,montantMin,MontantMax,page,size);

    }*/

    @GetMapping("/getAllFactureEchues")
    @PostAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<FactureDTO> getAllFactureEchues(
                                              @RequestParam String numero,
                                              @RequestParam String libelle,
                                              @RequestParam String userName,
                                              @RequestParam int page,
                                              @RequestParam int size) {
        return factureService.getAllCommandeServise(numero,  libelle, userName, page, size);
    }
    @GetMapping("/countListEchue/{montantMin}/{MontantMax}")
    public Long countFactureList(@RequestBody Facture facture,
                                     @PathVariable double montantMin,
                                     @PathVariable  double MontantMax
                                        ){
        return factureService.countByListEchue(facture,montantMin,MontantMax);

    }
    @GetMapping("/getAllFactureReglee")
    @PostAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<FactureDTO> getAllListReglee(@RequestParam String libelle,
                                             @RequestParam String userName,
                                             @RequestParam int page,
                                             @RequestParam int size){
        return factureService.listReglee(libelle,userName,page,size);
    }
    @GetMapping("/countAllListReglee")
    public Long countAllListReglee(@RequestBody Facture facture){
        return factureService.countListReglee(facture);
    }
    @GetMapping("/listImport")
    @PostAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<FactureDTO> listImportTrue(
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_debut,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_fin,
                                    @RequestParam boolean recupEnStock,
                                    @RequestParam boolean recupEncpt,
                                    @RequestParam String libelle,
                                    @RequestParam String numero,
                                    @RequestParam int page,
                                    @RequestParam int size){
        return factureService.listImport(date_debut,date_fin,recupEnStock,recupEncpt,libelle,numero,page,size);
    }
    @GetMapping("/countListImport/{recuEnCpt}/{date_debut}/{date_fin}")
    public Long countListImportTrue(@RequestBody Facture facture,
                                        @PathVariable boolean recuEnCpt,
                                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_debut,
                                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_fin
                                        ){
        return factureService.countListImport(facture,recuEnCpt,date_debut,date_fin);
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<Facture>> getAll(@PathVariable Long id){
        return ResponseEntity.ok(factureService.getAllFactureByCommande(id));
    }

    @PutMapping("confirme")
    @PostAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Facture confirmer(@RequestBody Facture facture){
        return factureService.confirmerReception(facture);
    }

}
