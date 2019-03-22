/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.service;

import shine.db.record.IOCfile2DB.FolderFileScanner;

/**
 *
 * @author lvhuihui
 */
public class scanIOCFile {

    public static void main(String[] args) {
        String filePath = "E:\\NetBeansProjects\\shine-db-record_service\\data\\IOCdata";
        FolderFileScanner f = new FolderFileScanner();
        f.scanFromDir(filePath);
    }
}
