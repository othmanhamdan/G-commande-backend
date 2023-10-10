package com.snce.lightcom.services.Imp;

import com.snce.lightcom.DTO.FactureDTO;
import com.snce.lightcom.Utils.UtilsDate;
import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.Facture;
import com.snce.lightcom.entities.TypeCommande;
import com.snce.lightcom.mappers.MapperService;
import com.snce.lightcom.repository.CommandeRepository;
import com.snce.lightcom.repository.FactureRepository;
import com.snce.lightcom.services.FactureService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FactureServiceImpl implements FactureService {
    private FactureRepository factureRepository;
    private CommandeRepository commandeRepository;
    private MapperService mapperService;

    @Override
    public Facture save(Facture facture) {
        if (!Objects.isNull(facture)){
            facture.setDateSaisie(new Date());
            facture.getCommande().setId(facture.getCommande().getId());
            facture.setDateEcheance(
                    UtilsDate.incDateByDays(facture.getDateLivraison(),facture.getCommande().getDelaiPaiment())
            );
            commandeRepository.save(facture.getCommande());
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
            facture.setCommande(facture.getCommande());
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
    public Long countByListEchue(Facture facture, double montantMin, double montantMax) {
        Date date = facture.getDateEcheance();
        String numero = facture.getNumero();
        Long utilisateur_id = facture.getCommande().getAppUser().getId();
        Long fournisseur_id = facture.getCommande().getFournisseur().getId();
        return factureRepository.countlistEchue(date,numero,utilisateur_id,fournisseur_id,montantMin,montantMax);
    }

    @Override
    public Page<FactureDTO> listReglee(String libelle, String userName, int page, int size) {
        List<Facture> factures = factureRepository.listReglee(libelle,userName);

        List<FactureDTO> factureDTOStream = factures.stream().map(factureReglle -> mapperService.fromFacture(factureReglle)).collect(Collectors.toList());
        Page<FactureDTO> factureDTOS = convertListToPage(factureDTOStream, PageRequest.of(page, size));
        return factureDTOS;


    }

    @Override
    public Long countListReglee(Facture facture) {
        Long utilisateur_id = facture.getCommande().getAppUser().getId();
        Long fournisseur_id = facture.getCommande().getFournisseur().getId();
        return factureRepository.countListReglee(utilisateur_id,fournisseur_id);
    }

    @Override
    public Page<FactureDTO> listImport(Date date_debut,Date date_fin, boolean recupEnStock,boolean recupEncpt,String libelle,String numero, int page,int size) {
        TypeCommande type = TypeCommande.IMPORTATION;
        List<Facture> listImportWithRecuEnCpt;
        if (recupEncpt){
            listImportWithRecuEnCpt= factureRepository.listImportWithRecuEnCptTrue(date_debut,date_fin,recupEnStock,libelle, numero,type,PageRequest.of(page,size));
        }else {
            listImportWithRecuEnCpt= factureRepository.listImportWithRecuEnCptFalse(date_debut,date_fin,recupEnStock,libelle, numero,type,PageRequest.of(page,size));

        }
        List<FactureDTO> factureDTOS = listImportWithRecuEnCpt.stream().map(facture ->
                mapperService.fromFacture(facture)
        ).collect(Collectors.toList());
        return convertListToPage(factureDTOS, PageRequest.of(page, size));


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

    @Override
    public List<Facture> getAllFactureByCommande(Long id) {
        return factureRepository.getFactureByCommandeId(id);
    }

    @Override
    public Page<FactureDTO> getAllCommandeServise(String numero, String libelle, String userName, int page, int size) {
        List<Facture> allFactureEchuesRep = factureRepository.getAllFactureEchuesRep(numero.toLowerCase(), libelle.toLowerCase(),userName);
        List<FactureDTO> factureDTOS = allFactureEchuesRep.stream().map(facture ->
                mapperService.fromFacture(facture)
        ).collect(Collectors.toList());
        Page<FactureDTO> factures = convertListToPage(factureDTOS, PageRequest.of(page, size));
        return factures;
    }
    public Page<FactureDTO> convertListToPage(List<FactureDTO> list, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<FactureDTO> pageList;

        if (list.size() < startItem) {
            pageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            pageList = list.subList(startItem, toIndex);
        }

        return new PageImpl<>(pageList, pageable, list.size());

    }
    @Override
    public Facture confirmerReception(Facture facture){
        Optional<Facture> id = factureRepository.findById(facture.getId());
        if (id.isPresent()){
            facture.setReceptionSaisie(true);
            factureRepository.save(facture);
        }
        return facture;

    }

}
