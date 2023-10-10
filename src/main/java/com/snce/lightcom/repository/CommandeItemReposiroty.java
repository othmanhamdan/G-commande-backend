package com.snce.lightcom.repository;


import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.entities.CommandeItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandeItemReposiroty extends JpaRepository<CommandeItem,Long> {
    @Query("SELECT ci FROM CommandeItem ci inner JOIN ci.commande c inner join c.fournisseur cf WHERE c.supprime = false and c.numero is not null and c.unite like %:unite% and c.numero like %:numero% and ci.article like %:article% and ci.uniteMesure like %:uniteMesure% and cf.libelle like %:libelle% order by c.date desc ,c.id")
    List<CommandeItem> listItemsCommande( @Param("numero")String numero,@Param("unite")String unite,@Param("libelle") String libelle, @Param("article")String article, @Param("uniteMesure")String uniteMesure, Pageable pageable);

    @Query("SELECT count(ci) FROM CommandeItem ci INNER JOIN ci.commande c inner join c.fournisseur cf WHERE c.supprime = false and c.numero is not null and c.unite like %:unite% and c.numero like %:numero% and ci.article like %:article% and ci.uniteMesure like %:uniteMesure% and cf.libelle like %:fournisseur%")
    Long countlistItemsCommande(@Param("unite")String unite, @Param("numero")String numero,@Param("fournisseur") String fournisseur, @Param("article")String article, @Param("uniteMesure")String uniteMesure);

    @Query("select ci from CommandeItem ci WHERE ci.commande.id = :id")
    List<CommandeItem> getArticleById(@Param("id") Long id);


}
