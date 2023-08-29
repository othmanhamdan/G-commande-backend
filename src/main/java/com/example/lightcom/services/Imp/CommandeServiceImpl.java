package com.example.lightcom.services.Imp;

import com.example.lightcom.Utils.UtilsDate;
import com.example.lightcom.entities.Commande;
import com.example.lightcom.entities.CommandeItem;
import com.example.lightcom.entities.Fournisseur;
import com.example.lightcom.repository.CommandeItemReposiroty;
import com.example.lightcom.repository.CommandeRepository;
import com.example.lightcom.services.CommandeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private CommandeRepository commandeRepository;
    private CommandeItemReposiroty commandeItemReposiroty;


    //save()
    @Override
    public Commande save(Commande commande) {
        if (commande.getNumero() != null){
            commande.setDate(new Date());
            commande.setDateRelanceFacture(UtilsDate.incDateByDays(commande.getDate(), commande.getDelaiLivraison() + 30 ));   //  date bc + delai liv + 30 jours !!
            commande.setReliquat(getMontant()-commande.getRemise());
            commande.getTypeCommande().toString();
            commandeRepository.save(commande);
        }else {
            Long maxId;
            if(UtilsDate.getYear(commande.getDate())==2014) // TODO à enlever en 2015
                maxId= Long.valueOf(8029);
            else
                maxId = commandeRepository.getMaxIdPrecedant(commande.getDate().getYear());
            if(maxId == null)
                commande.genererNumero();
            else
                commande.genererNumero(commande.getId() - maxId);
            commande.getTypeCommande().toString();
            commandeRepository.save(commande);
        }
        return commande;
    }
    @Override
    public double getMontant(){
        double resul=0;
        List<Commande> commandeSet = commandeRepository.findAll();
        List<CommandeItem> commandeItems=new ArrayList<>();
        //Set<Commande> commandes=new HashSet<Commande>();

        if(!commandeSet.isEmpty())
            for (CommandeItem item : commandeItems)
                resul+=item.getMontant();
        else
            for (Commande commande : commandeSet)
                resul+=getMontant();
        return resul;

    }

    //update()
    @Override
    public Commande update(Commande commande) {
        if (commande.getId() != null){
            Optional<Commande> id = commandeRepository.findById(commande.getId());
            if (id.isPresent()){
                //commande.setReliquat(commande);
                commandeRepository.save(commande);
            }else {
                System.out.println("Identifiant du client invalide");
            }
        }
        return commande;
    }
    //delete()
    public void delete(Long id){
        if (id != null){
            Optional<Commande> idCommande = commandeRepository.findById(id);
            if (idCommande.isPresent()){
                commandeRepository.deleteById(id);
            }else {
                System.out.println("Identifiant du client invalide");
            }
        }
    }

    //valide()
    public void valide(Commande commande){
        Optional<Commande> id = commandeRepository.findById(commande.getId());
        if (id.isPresent()){
            commande.setValide(true);
            commandeRepository.save(commande);
        }else {
            System.out.println("Identifiant du client invalide");
        }
    }

    //cloturer()
    @Override
    public void cloture(Commande commande) {
        Optional<Commande> id = commandeRepository.findById(commande.getId());
        if (id.isPresent()){
            commande.setCloture(true);
            commandeRepository.save(commande);
        }else {
            System.out.println("Identifiant du client invalide");
        }
    }

    //list()
    @Override
    public Page<Commande> getAllCommande(String unite,String numero, String libelle, String nom, int page, int size) {
        return commandeRepository.getAllCommande(unite,numero,libelle,nom, PageRequest.of(page,size));
    }

    //listCommandesNonFacturer()
    @Override
    public Page<Commande> getAllCommandeNonFacturer(Commande commande, int page, int size) {
        Date date = new Date();
        Long utilisateur_id = commande.getUtilisateur().getId();
        String unite = commande.getUnite();
        return commandeRepository.selectCommandeNonFacturer(utilisateur_id,unite,date,PageRequest.of(page,size));
    }

    //countCommandeNonFacturer()
    @Override
    public Long countCommandeNonFacturer(Commande commande){
        Date date = new Date();
        Long utilisateur_id = commande.getUtilisateur().getId();
        String unite = commande.getUnite();
        return commandeRepository.countCommandeNonFacturer(utilisateur_id,unite,date);

    }

    //count()
    @Override
    public Long countCommande(Commande commande) {
        Long utilisateur_id = commande.getUtilisateur().getId();
        String unite = commande.getUnite();
        String numero = commande.getNumero();
        Long fournisseur_id = commande.getFournisseur().getId();
        return commandeRepository.countByUniteAndNumeroContainingAndFournisseurIdAndUtilisateurId(unite,numero,fournisseur_id,utilisateur_id);
    }

    //listItems()
    @Override
    public Page<CommandeItem> listItemCommande(CommandeItem commandeItem, int page, int size) {
        String unite = commandeItem.getCommande().getUnite();
        String fournisseur = commandeItem.getCommande().getFournisseur().getLibelle();
        String numero = commandeItem.getCommande().getNumero();
        String article = commandeItem.getArticle();
        String uniteMesure = commandeItem.getUniteMesure();
        return commandeItemReposiroty.listItemsCommande(unite,numero,fournisseur,article,uniteMesure, PageRequest.of(page,size));
    }
    //countItems()
    @Override
    public Long countListItemCommande(CommandeItem commandeItem) {
        String unite = commandeItem.getCommande().getUnite();
        String numero = commandeItem.getCommande().getNumero();
        String fournisseur = commandeItem.getCommande().getFournisseur().getLibelle();
        String article = commandeItem.getArticle();
        String uniteMesure = commandeItem.getUniteMesure();
        return commandeItemReposiroty.countlistItemsCommande(unite,numero,fournisseur,article,uniteMesure);
    }

    public Page<Commande> getAll(int page, int size){
        return commandeRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public List<Commande> getAlls() {
        return commandeRepository.findAll();
    }
}
