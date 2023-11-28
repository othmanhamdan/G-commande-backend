package com.snce.lightcom.web;

import com.snce.lightcom.services.ReportService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@AllArgsConstructor
public class ReportController  {
    private ReportService reportService;

    @GetMapping("/report/{id_commande}")
    @PostAuthorize("hasAnyAuthority('USER','ADMIN')")
    public void generatedReport(HttpServletResponse response, @PathVariable Long id_commande) throws IOException, JRException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition" ;
        String headerValue = "attachment; filename = commande"+id_commande+".pdf";
        response.setHeader(headerKey,headerValue);
        reportService.exportReport(response,id_commande);
    }
}
