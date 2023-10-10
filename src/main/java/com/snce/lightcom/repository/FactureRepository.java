package com.snce.lightcom.repository;

import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.Facture;
import com.snce.lightcom.entities.TypeCommande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    List<Facture> findByCommandeOrderByDateEcheanceDesc(Commande commande);

    @Query("select f from Facture f left join f.commande c left join c.fournisseur cf inner join c.appUser u where f.dateReglement is null and f.dateEcheance <= f.dateFacture and f.numero like %:numero% and cf.libelle LIKE %:libelle% and u.userName LIKE :userName order by f.dateEcheance")
    List<Facture> getAllFactureEchuesRep(@Param("numero") String numero, @Param("libelle") String libelle, @Param("userName") String userName);

    @Query("select count(f) from Facture f left join f.commande c left join c.fournisseur cf inner join c.appUser u where f.dateReglement is null and f.dateEcheance <= :date and upper(f.numero) like :numero and abs(f.montanHT + f.montantTva) >= :montantMin and abs(f.montanHT + f.montantTva) <= :montantMax and u.id = :utilisateur_id and cf.id = :fournisseur_id")
    Long countlistEchue(@Param("date") Date date, @Param("numero") String numero, @Param("utilisateur_id") Long utilisateur_id, @Param("fournisseur_id") Long fournisseur_id, @Param("montantMin") double montantMin,@Param("montantMax") double montantMax);

    @Query("select f from Facture f left join f.commande c left join c.fournisseur cf inner join c.appUser u where f.dateReglement is not null and cf.libelle LIKE %:libelle% and u.userName LIKE :userName order by f.dateReglement desc")
    List<Facture> listReglee(@Param("libelle") String libelle,@Param("userName") String userName);

    @Query("select count(f) from Facture f left join f.commande c left join c.fournisseur cf inner join c.appUser u where f.dateReglement is not null and u.id = :utilisateur_id and cf.id = :fournisseur_id")
    Long countListReglee(@Param("utilisateur_id") Long utilisateur_id, @Param("fournisseur_id") Long fournisseur_id );

    @Query("select f from Facture f left join f.commande c left join c.fournisseur cf where c.typeCommande = :type and f.numero like %:numero% and f.dateFacture >= :dateDebut and f.dateFacture <= :dateFin and f.receptionSaisie = :recuEnStock and f.dateReceptionCompta is not null and cf.libelle LIKE %:libelle%")
    List<Facture> listImportWithRecuEnCptTrue(@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin,@Param("recuEnStock") boolean recuEnStock ,@Param("libelle") String libelle, @Param("numero") String numero , @Param("type") TypeCommande type , Pageable pageable);

    @Query("select f from Facture f left join f.commande c left join c.fournisseur cf where c.typeCommande = :type and f.numero like %:numero% and f.dateFacture >= :dateDebut and f.dateFacture <= :dateFin and f.receptionSaisie = :recuEnStock and f.dateReceptionCompta is null and cf.libelle LIKE %:libelle%")
    List<Facture> listImportWithRecuEnCptFalse(@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin,@Param("recuEnStock") boolean recuEnStock ,@Param("libelle") String libelle, @Param("numero") String numero , @Param("type") TypeCommande type , Pageable pageable);

    @Query("select COUNT(f) from Facture f left join f.commande c left join c.fournisseur cf where c.typeCommande = :type and upper(f.numero) like :numero and f.dateFacture >= :dateDebut and f.dateFacture <= :dateFin and f.receptionSaisie = :recuEnStock and f.dateReceptionCompta is not null and cf.id = :fournisseur_id")
    Long countListImportWithRecuEnCptTrue(@Param("fournisseur_id") Long fournisseur_id, @Param("numero") String numero, @Param("recuEnStock") boolean recuEnStock , @Param("type") TypeCommande type, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    @Query("select COUNT(f) from Facture f left join f.commande c left join c.fournisseur cf where c.typeCommande = :type and upper(f.numero) like :numero and f.dateFacture >= :dateDebut and f.dateFacture <= :dateFin and f.receptionSaisie = :recuEnStock and f.dateReceptionCompta is null and cf.id = :fournisseur_id")
    Long countListImportWithRecuEnCptFalse(@Param("fournisseur_id") Long fournisseur_id, @Param("numero") String numero, @Param("recuEnStock") boolean recuEnStock , @Param("type") TypeCommande type, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    @Query("select f from Facture f WHERE f.commande.id = :id")
    List<Facture> getFactureByCommandeId(@Param("id") Long id);


    //@Query("select f from Facture f left join f.commande c left join c.fournisseur fr where f.numero like %:numero% and fr.libelle LIKE %:libelle%")
    //Page<Facture> getAllFactureEchuesRep(@Param("numero") String numero, @Param("libelle") String libelle, Pageable pageable);


}
