/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shine.db.record.common.tools;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lvhuihui
 */
public class EmProvider {

    private static final String DB_PU = "accRecordPU";
    private static final EmProvider singleton = new EmProvider();
    private EntityManagerFactory emf;

    public EmProvider() {
    }

    public static EmProvider getInstance() {
        return singleton;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(DB_PU);
        }
        return emf;
    }

    public void closeEmf() {
        if(emf.isOpen() || emf != null) {
            emf.close();
        }
        emf = null;    
    }

}
