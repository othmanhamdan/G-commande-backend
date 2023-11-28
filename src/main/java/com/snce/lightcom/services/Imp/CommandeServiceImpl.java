package com.snce.lightcom.services.Imp;

import com.snce.lightcom.Utils.UtilsDate;
import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.CommandeItem;
import com.snce.lightcom.entities.Facture;
import com.snce.lightcom.repository.CommandeItemReposiroty;
import com.snce.lightcom.repository.CommandeRepository;
import com.snce.lightcom.repository.FactureRepository;
import com.snce.lightcom.services.CommandeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private CommandeRepository commandeRepository;
    private CommandeItemReposiroty commandeItemReposiroty;
    private FactureRepository factureRepository;


    //save()
    @Override
    public Commande save(Commande commande) {
        if (!commande.getNumero().equals("")){

            commande.setNumero(commande.getNumero().toLowerCase());
            commande.setUnite(commande.getUnite().toLowerCase());
            commande.setDateRelanceFacture(UtilsDate.incDateByDays(commande.getDate(), commande.getDelaiLivraison() + 30 ));   //  date bc + delai liv + 30 jours !!
            commande.setReliquat(commande.getReliquat()-commande.getRemise());
            commande.getTypeCommande().toString();
            System.out.println(commande);
            commandeRepository.save(commande);
        }
        if (commande.getNumero().equals("")) {
            System.out.println(commande.getDate());
            Long maxId;
            if(UtilsDate.getYear(commande.getDate())==2014) // TODO à enlever en 2015
                maxId= Long.valueOf(8029);
            else
                maxId = commandeRepository.getMaxIdPrecedant(UtilsDate.getYear(commande.getDate()));
            System.out.println(maxId);
            if(maxId == null)
                commande.genererNumero();
            else
                commande.genererNumero(commande.getId() - maxId);
            commande.getTypeCommande().toString();
            System.out.println(commande);
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
            System.out.println(id);
            if (id.isPresent()){
                double nvRemise = commande.getRemise();
                double drReliquat = id.get().getReliquat();
                double drRemise = id.get().getRemise();
                double reliquat = drReliquat + (nvRemise - drRemise);
                commande.setReliquat(reliquat);
                System.out.println(reliquat);
                System.out.println(commande);
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
            List<CommandeItem> idCommandeItem = commandeItemReposiroty.getArticleById(id);
            List<Facture> idFacture = factureRepository.getFactureByCommandeId(id);

            if (idCommande.isPresent()){
                if (!idCommandeItem.isEmpty()){
                    for (CommandeItem item: idCommandeItem){
                        commandeItemReposiroty.deleteById(item.getId());
                    }
                }
                if (!idFacture.isEmpty()){
                    for (Facture facture : idFacture){
                        factureRepository.deleteById(facture.getId());
                    }
                }
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
    public Commande cloture(Commande commande) {
        Optional<Commande> id = commandeRepository.findById(commande.getId());
        if (id.isPresent()){
            commande.setCloture(true);
            commandeRepository.save(commande);
        }else {
            System.out.println("Identifiant du client invalide");
        }
        return commande;
    }

    //list()
    @Override
    public Page<Commande> getAllCommande(String unite,String numero, String libelle, String nom, int page, int size) {
        return commandeRepository.getAllCommande(unite.toLowerCase(),numero.toLowerCase(),libelle.toLowerCase(),nom.toLowerCase(), PageRequest.of(page,size));
    }

    //listCommandesNonFacturer()
    @Override
    public Page<Commande> getAllCommandeNonFacturer(Commande commande, int page, int size) {
        Date date = new Date();
        Long utilisateur_id = commande.getAppUser().getId();
        String unite = commande.getUnite();
        return commandeRepository.selectCommandeNonFacturer(utilisateur_id,unite,date,PageRequest.of(page,size));
    }

    //countCommandeNonFacturer()
    @Override
    public Long countCommandeNonFacturer(Commande commande){
        Date date = new Date();
        Long utilisateur_id = commande.getAppUser().getId();
        String unite = commande.getUnite();
        return commandeRepository.countCommandeNonFacturer(utilisateur_id,unite,date);

    }

    //count()
    @Override
    public Long countCommande(Commande commande) {
        Long utilisateur_id = commande.getAppUser().getId();
        String unite = commande.getUnite();
        String numero = commande.getNumero();
        Long fournisseur_id = commande.getFournisseur().getId();
        return commandeRepository.countByUniteAndNumeroContainingAndFournisseurIdAndAppUserId(unite,numero,fournisseur_id,utilisateur_id);
    }

    //listItems()

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

    @Override
    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    @Override
    public Page<Commande> getAllCommandeNonCloturer(String unite, String nom, int page, int size) {
        return commandeRepository.getAllCommandeNonCloturer(unite.toLowerCase(), nom.toLowerCase(), PageRequest.of(page,size));
    }
}
