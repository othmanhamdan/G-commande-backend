package com.example.lightcom.web;

import com.example.lightcom.entities.Commande;
import com.example.lightcom.entities.CommandeItem;
import com.example.lightcom.entities.Facture;
import com.example.lightcom.services.FactureService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
    @PutMapping("/reglee")
    public void reglee(@RequestBody Facture facture){
        factureService.reglee(facture);
    }
    @GetMapping("/getByCommande")
    public List<Facture> getByCommandeItems(@RequestBody Commande commande){
        return factureService.getByCommande(commande);
    }
    @GetMapping("/listEchue/{montantMin}/{MontantMax}/{page}/{size}")
    public List<Facture> factureList(@RequestBody Facture facture,
                                     @PathVariable double montantMin,
                                     @PathVariable  double MontantMax,
                                     @PathVariable  int page,
                                     @PathVariable  int size){
        return factureService.listEchue(facture,montantMin,MontantMax,page,size);

    }
    @GetMapping("/countListEchue/{montantMin}/{MontantMax}")
    public Long countFactureList(@RequestBody Facture facture,
                                     @PathVariable double montantMin,
                                     @PathVariable  double MontantMax
                                        ){
        return factureService.countByListEchue(facture,montantMin,MontantMax);

    }
    @GetMapping("/getAllListReglee/{page}/{size}")
    public List<Facture> getAllListReglee(@RequestBody Facture facture,
                                          @PathVariable int page,
                                          @PathVariable int size){
        return factureService.listReglee(facture,page,size);
    }
    @GetMapping("/countAllListReglee")
    public Long countAllListReglee(@RequestBody Facture facture){
        return factureService.countListReglee(facture);
    }
    @GetMapping("/listImport/{recuEnCpt}/{date_debut}/{date_fin}/{page}/{size}")
    public List<Facture> listImportTrue(@RequestBody Facture facture,
                                    @PathVariable boolean recuEnCpt,
                                    @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_debut,
                                    @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_fin,
                                    @PathVariable int page,
                                    @PathVariable int size){
        return factureService.listImport(facture,recuEnCpt,date_debut,date_fin,page,size);
    }
    @GetMapping("/countListImport/{recuEnCpt}/{date_debut}/{date_fin}")
    public Long countListImportTrue(@RequestBody Facture facture,
                                        @PathVariable boolean recuEnCpt,
                                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_debut,
                                        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_fin
                                        ){
        return factureService.countListImport(facture,recuEnCpt,date_debut,date_fin);
    }

    @PostMapping("/saveFactureDeCommande")
    public Facture saveFactureDeCommande(@RequestBody Facture facture,
                                         @RequestBody List<Commande> commandeGroupe){
        return factureService.saveFactureDeCommande(facture,commandeGroupe);
    }

}
