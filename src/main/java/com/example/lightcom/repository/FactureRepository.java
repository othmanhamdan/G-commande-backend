package com.example.lightcom.repository;

import com.example.lightcom.entities.Commande;
import com.example.lightcom.entities.Facture;
import com.example.lightcom.entities.TypeCommande;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    List<Facture> findByCommandeOrderByDateEcheanceDesc(Commande commande);

    @Query("select f from Facture f left join f.commande c left join c.fournisseur cf inner join c.utilisateur u where f.dateReglement is null and f.dateEcheance <= :date and upper(f.numero) like :numero and abs(f.montanHT + f.montantTva) >= :montantMin and abs(f.montanHT + f.montantTva) <= :montantMax and u.id = :utilisateur_id and cf.id = :fournisseur_id order by f.dateEcheance")
    List<Facture> listEchue(@Param("date") Date date, @Param("numero") String numero, @Param("utilisateur_id") Long utilisateur_id, @Param("fournisseur_id") Long fournisseur_id, @Param("montantMin") double montantMin,@Param("montantMax") double montantMax, Pageable pageable);

    @Query("select count(f) from Facture f left join f.commande c left join c.fournisseur cf inner join c.utilisateur u where f.dateReglement is null and f.dateEcheance <= :date and upper(f.numero) like :numero and abs(f.montanHT + f.montantTva) >= :montantMin and abs(f.montanHT + f.montantTva) <= :montantMax and u.id = :utilisateur_id and cf.id = :fournisseur_id")
    Long countlistEchue(@Param("date") Date date, @Param("numero") String numero, @Param("utilisateur_id") Long utilisateur_id, @Param("fournisseur_id") Long fournisseur_id, @Param("montantMin") double montantMin,@Param("montantMax") double montantMax);

    @Query("select f from Facture f left join f.commande c left join c.fournisseur cf inner join c.utilisateur u where f.dateReglement is not null and u.id = :utilisateur_id and cf.id = :fournisseur_id order by f.dateReglement desc")
    List<Facture> listReglee(@Param("utilisateur_id") Long utilisateur_id, @Param("fournisseur_id") Long fournisseur_id,Pageable pageable);

    @Query("select count(f) from Facture f left join f.commande c left join c.fournisseur cf inner join c.utilisateur u where f.dateReglement is not null and u.id = :utilisateur_id and cf.id = :fournisseur_id")
    Long countListReglee(@Param("utilisateur_id") Long utilisateur_id, @Param("fournisseur_id") Long fournisseur_id );

    @Query("select f from Facture f left join f.commande c left join c.fournisseur cf where c.typeCommande = :type and upper(f.numero) like :numero and f.dateFacture >= :dateDebut and f.dateFacture <= :dateFin and f.receptionSaisie = :recuEnStock and f.dateReceptionCompta is not null and cf.id = :fournisseur_id")
    List<Facture> listImportWithRecuEnCptTrue(@Param("fournisseur_id") Long fournisseur_id, @Param("numero") String numero, @Param("recuEnStock") boolean recuEnStock , @Param("type") TypeCommande type, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin, Pageable pageable);

    @Query("select f from Facture f left join f.commande c left join c.fournisseur cf where c.typeCommande = :type and upper(f.numero) like :numero and f.dateFacture >= :dateDebut and f.dateFacture <= :dateFin and f.receptionSaisie = :recuEnStock and f.dateReceptionCompta is null and cf.id = :fournisseur_id")
    List<Facture> listImportWithRecuEnCptFalse(@Param("fournisseur_id") Long fournisseur_id, @Param("numero") String numero, @Param("recuEnStock") boolean recuEnStock , @Param("type") TypeCommande type, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin, Pageable pageable);


    @Query("select COUNT(f) from Facture f left join f.commande c left join c.fournisseur cf where c.typeCommande = :type and upper(f.numero) like :numero and f.dateFacture >= :dateDebut and f.dateFacture <= :dateFin and f.receptionSaisie = :recuEnStock and f.dateReceptionCompta is not null and cf.id = :fournisseur_id")
    Long countListImportWithRecuEnCptTrue(@Param("fournisseur_id") Long fournisseur_id, @Param("numero") String numero, @Param("recuEnStock") boolean recuEnStock , @Param("type") TypeCommande type, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    @Query("select COUNT(f) from Facture f left join f.commande c left join c.fournisseur cf where c.typeCommande = :type and upper(f.numero) like :numero and f.dateFacture >= :dateDebut and f.dateFacture <= :dateFin and f.receptionSaisie = :recuEnStock and f.dateReceptionCompta is null and cf.id = :fournisseur_id")
    Long countListImportWithRecuEnCptFalse(@Param("fournisseur_id") Long fournisseur_id, @Param("numero") String numero, @Param("recuEnStock") boolean recuEnStock , @Param("type") TypeCommande type, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

}
