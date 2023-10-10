package com.snce.lightcom.services.Imp;

import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.CommandeItem;
import com.snce.lightcom.entities.Fournisseur;
import com.snce.lightcom.repository.CommandeItemReposiroty;
import com.snce.lightcom.repository.CommandeRepository;
import com.snce.lightcom.repository.FournisseurRepository;
import com.snce.lightcom.services.FournisseurService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {
    private FournisseurRepository fournisseurRepository;
    private CommandeRepository commandeRepository;
    private CommandeItemReposiroty commandeItemReposiroty;
    @Override
    public List<Fournisseur> getAllFournisseur() {
        return fournisseurRepository.findAll();
    }

    @Override
    public Fournisseur saveSupplier(Fournisseur fournisseur){
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public Page<Fournisseur> getAllSupplier(String libelle, int page, int size){
        return fournisseurRepository.findByLibelleContainsOrderByIdDesc(libelle , PageRequest.of(page,size));
    }
    @Override
    public void deleteSupplier(Long id){
        if (id != null){
            Optional<Fournisseur> byId = fournisseurRepository.findById(id);
            List<Commande> commandes = commandeRepository.findByFournisseurId(id);
            //List<CommandeItem> commandeItems = commandeItemReposiroty.findAllByCommande(commandes);
            if (byId.isPresent()){
                for (Commande commande : commandes){
                    commandeItemReposiroty.deleteById(commande.getId());




                }

                fournisseurRepository.deleteById(id);
            }
        }

    }
}
