package com.snce.lightcom.services;

import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface ReportService {
    //String exportReport(Long id_commande) throws IOException, JRException;

    void exportReport(HttpServletResponse response ,Long id_commande) throws IOException, JRException;
}
