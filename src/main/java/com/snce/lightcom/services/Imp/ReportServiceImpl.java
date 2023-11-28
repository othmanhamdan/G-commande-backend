package com.snce.lightcom.services.Imp;


import com.snce.lightcom.DTO.CommandeDTO;
import com.snce.lightcom.entities.Commande;
import com.snce.lightcom.mappers.MapperService;
import com.snce.lightcom.proj.CommandeItemProjection;
import com.snce.lightcom.repository.CommandeItemReposiroty;
import com.snce.lightcom.repository.CommandeRepository;
import com.snce.lightcom.services.ReportService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private CommandeItemReposiroty commandeItemReposiroty;
    private CommandeRepository commandeRepository;
    private MapperService mapperService;
    @Override
    public void exportReport(HttpServletResponse response , Long id_commande) throws IOException, JRException {
        List<CommandeItemProjection> commandes = commandeItemReposiroty.exportReport(id_commande);
        CommandeDTO commande = mapperService.fromCommande(commandeRepository.findCommandeById(id_commande));
        File file = ResourceUtils.getFile("classpath:commandeItem.jrxml");
        JasperReport jasperReport= JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(commandes);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("numero", commande.getNumero());
        parameters.put("date", commande.getDate());
        parameters.put("unite", commande.getUnite());
        parameters.put("libelle", commande.getFournisseur().getLibelle());
        parameters.put("modePaiment", commande.getModePaiment());
        parameters.put("delaiLivraison", commande.getDelaiLivraison());
        parameters.put("delaiPaiment", commande.getDelaiPaiment());
        parameters.put("lieuLivraison", commande.getLieuLivraison());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
