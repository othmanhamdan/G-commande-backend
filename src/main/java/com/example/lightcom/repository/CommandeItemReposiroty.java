package com.example.lightcom.repository;


import com.example.lightcom.entities.CommandeItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommandeItemReposiroty extends JpaRepository<CommandeItem,Long> {
    @Query("SELECT ci FROM CommandeItem ci inner JOIN ci.commande c inner join c.fournisseur cf WHERE c.supprime = false and c.numero is not null and c.unite like %:unite% and c.numero like %:numero% and ci.article like %:article% and ci.uniteMesure like %:uniteMesure% and cf.libelle like %:fournisseur% order by c.date desc ,c.id")
    Page<CommandeItem> listItemsCommande(@Param("unite")String unite, @Param("numero")String numero,@Param("fournisseur") String fournisseur, @Param("article")String article, @Param("uniteMesure")String uniteMesure, Pageable pageable);

    @Query("SELECT count(ci) FROM CommandeItem ci INNER JOIN ci.commande c inner join c.fournisseur cf WHERE c.supprime = false and c.numero is not null and c.unite like %:unite% and c.numero like %:numero% and ci.article like %:article% and ci.uniteMesure like %:uniteMesure% and cf.libelle like %:fournisseur%")
    Long countlistItemsCommande(@Param("unite")String unite, @Param("numero")String numero,@Param("fournisseur") String fournisseur, @Param("article")String article, @Param("uniteMesure")String uniteMesure);

}
