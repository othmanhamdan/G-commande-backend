package com.example.lightcom.services.Imp;

import com.example.lightcom.entities.*;
import com.example.lightcom.repository.CommandeRepository;
import com.example.lightcom.repository.FactureRepository;
import com.example.lightcom.services.FactureService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class FactureServiceImpl implements FactureService {
    private FactureRepository factureRepository;
    private CommandeRepository commandeRepository;

    @Override
    public Facture save(Facture facture) {
        if (!Objects.isNull(facture)){
            facture.setDateSaisie(new Date());
            factureRepository.save(facture);
        }
       return facture;
    }
    @Override
    public void recueParCompta(Facture facture){
        Optional<Facture> id = factureRepository.findById(facture.getId());
        if (id.isPresent()){
            facture.setDateReceptionCompta(new Date());
            factureRepository.save(facture);
        }else {
            System.out.println("Identifiant du client invalide");
        }
    }

    @Override
    public void reglee(Facture facture) {
        Optional<Facture> id = factureRepository.findById(facture.getId());
        if (id.isPresent()){
            factureRepository.save(facture);
        }else {
            System.out.println("Identifiant du client invalide");
        }
    }

    //list()
    @Override
    public List<Facture> getByCommande(Commande commande) {
        return factureRepository.findByCommandeOrderByDateEcheanceDesc(commande);
    }

    @Override
    public Facture saveFactureDeCommande(Facture facture, List<Commande> commandeGroupe) {
        Commande commande;
        if(commandeGroupe.size() > 1){
            commande = new Commande();
            double reliquat=0;
            double remise=0;
            commande.setFournisseur(commandeGroupe.get(0).getFournisseur());
            commande.setDelaiPaiment(commandeGroupe.get(0).getDelaiPaiment());//TODO new line
            commande.setUtilisateur(facture.getCommande().getUtilisateur());
            commande.setValide(true);
            commandeRepository.save(commande);
            for (Commande cmd : commandeGroupe) {
                Optional<Commande> id = commandeRepository.findById(commande.getId());
                if (id.isPresent()){
                    if(commande.getDateRelanceFacture()==null)
                        commande.setDateRelanceFacture(cmd.getDateRelanceFacture());
                    if(commande.getDateRelanceFacture().after(cmd.getDateRelanceFacture()))
                        commande.setDateRelanceFacture(cmd.getDateRelanceFacture());
                    //cmd.setCommande(commande);
                    reliquat+=cmd.getReliquat();
                    reliquat+=cmd.getRemise();
                }else {
                    System.out.println("Identifiant du client invalide");
                }

            }
            commande.setReliquat(reliquat);
            commande.setRemise(remise);
        }else {
            commande=commandeGroupe.get(0);
            commandeRepository.save(commande);
            facture.setCommande(commande);
            commande.facturer(facture);
           // em.merge(facture);
            factureRepository.save(facture);
        }
        return facture;
    }

    @Override
    public List<Facture> listEchue(Facture facture, double montantMin, double montantMax, int page, int size) {
        Date date = facture.getDateEcheance();
        String numero = facture.getNumero();
        Long utilisateur_id = facture.getCommande().getUtilisateur().getId();
        Long fournisseur_id = facture.getCommande().getFournisseur().getId();
        return factureRepository.listEchue(date,numero,utilisateur_id,fournisseur_id,montantMin,montantMax, PageRequest.of(page,size));
    }
    @Override
    public Long countByListEchue(Facture facture, double montantMin, double montantMax) {
        Date date = facture.getDateEcheance();
        String numero = facture.getNumero();
        Long utilisateur_id = facture.getCommande().getUtilisateur().getId();
        Long fournisseur_id = facture.getCommande().getFournisseur().getId();
        return factureRepository.countlistEchue(date,numero,utilisateur_id,fournisseur_id,montantMin,montantMax);
    }

    @Override
    public List<Facture> listReglee(Facture facture, int page, int size) {
        Long utilisateur_id = facture.getCommande().getUtilisateur().getId();
        Long fournisseur_id = facture.getCommande().getFournisseur().getId();
        return factureRepository.listReglee(utilisateur_id,fournisseur_id, PageRequest.of(page,size));
    }

    @Override
    public Long countListReglee(Facture facture) {
        Long utilisateur_id = facture.getCommande().getUtilisateur().getId();
        Long fournisseur_id = facture.getCommande().getFournisseur().getId();
        return factureRepository.countListReglee(utilisateur_id,fournisseur_id);
    }

    @Override
    public List<Facture> listImport(Facture facture, boolean recuEnCpt, Date date_debut, Date date_fin, int page, int size) {
        Long fournisseur_id = facture.getCommande().getFournisseur().getId();
        String numero = facture.getNumero();
        boolean recuEnStock = facture.isReceptionSaisie();
        TypeCommande type = TypeCommande.IMPORTATION;
        List<Facture> listImportWithRecuEnCpt;
        if (recuEnCpt){
            listImportWithRecuEnCpt= factureRepository.listImportWithRecuEnCptTrue(fournisseur_id,numero,recuEnStock, type,date_debut,date_fin,PageRequest.of(page,size));
        }else {
            listImportWithRecuEnCpt= factureRepository.listImportWithRecuEnCptFalse(fournisseur_id,numero,recuEnStock, type,date_debut,date_fin,PageRequest.of(page,size));

        }
        return listImportWithRecuEnCpt;
    }
    @Override
    public Long countListImport(Facture facture, boolean recuEnCpt, Date date_debut, Date date_fin) {
        Long fournisseur_id = facture.getCommande().getFournisseur().getId();
        String numero = facture.getNumero();
        boolean recuEnStock = facture.isReceptionSaisie();
        TypeCommande type = TypeCommande.IMPORTATION;
        Long countListImportWithRecuEnCpt;
        if (recuEnCpt){
            countListImportWithRecuEnCpt= factureRepository.countListImportWithRecuEnCptTrue(fournisseur_id,numero,recuEnStock, type,date_debut,date_fin);
        }else {
            countListImportWithRecuEnCpt= factureRepository.countListImportWithRecuEnCptFalse(fournisseur_id,numero,recuEnStock, type,date_debut,date_fin);

        }
        return countListImportWithRecuEnCpt;
    }
}
