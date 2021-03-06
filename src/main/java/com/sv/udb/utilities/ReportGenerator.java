/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.utilities;
import com.sv.udb.resources.ConnectionDB;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
/**
 *
 * @author Estudiante
 */
public class ReportGenerator {
    
    public static void detailReport(String from, String to) {
        HashMap map;
        
        try {
            //Rutas de archivos
            String jrxmlFileName = new File("src/main/java/com/sv/udb/reports/WarePieces.jrxml").getAbsolutePath();
            String jasperFileName = new File("src/main/java/com/sv/udb/reports/WarePieces.jasper").getAbsolutePath();
            String pdfFileName = new File("reports/Bodega.pdf").getAbsolutePath();
            
            
            //Compilando jasperreport
            JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);
            
            //Conexion
            Connection conn = new ConnectionDB().getConn();
            
            //seteando los parametros que recibe el reporte
            map = new HashMap();
            map.put("fromDate",from);
            map.put("toDate", to);
            
            //Para generar al reporte directamente con una conexión y query (debería ser suficiente para reportes basados en tablas)
            JasperPrint print = (JasperPrint)JasperFillManager.fillReport(jasperFileName, map, conn);
            
            //guardando
            JasperExportManager.exportReportToPdfFile(print, pdfFileName);
            
            //mostrando
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(new File(pdfFileName));
                } catch (IOException ex) {
                    System.out.println("No abrió xd " + ex);
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
