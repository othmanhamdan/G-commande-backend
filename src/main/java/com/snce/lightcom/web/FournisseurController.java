package com.snce.lightcom.web;

import com.snce.lightcom.entities.Fournisseur;
import com.snce.lightcom.services.FournisseurService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@AllArgsConstructor
public class FournisseurController {
    private FournisseurService fournisseurService;

    @GetMapping
    @PostAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<Fournisseur> getAllFournisseur(){
        return fournisseurService.getAllFournisseur();
    }

    @PostMapping
    @PostAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<Fournisseur> save(@RequestBody Fournisseur fournisseur){
        return ResponseEntity.ok(fournisseurService.saveSupplier(fournisseur));
    }
    @GetMapping("/getSupplier")
    @PostAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Fournisseur> getAllSupplierByLibelle(@RequestParam String libelle,
                                                     @RequestParam int page,
                                                     @RequestParam int size){
        return fournisseurService.getAllSupplier(libelle , page,size);
    }

    @DeleteMapping("{id}")
    public void deleteBySupplier(@PathVariable Long id){
        fournisseurService.deleteSupplier(id);
    }
}
