/*
 * Voting System
 * Project By:  * 
 * Almiradz Mling  * 
 * Eduard John Madriaga  * 
 * Rodz Aguilar Piang  * 
 * Mark Kendrick Asena * 
 */

package votingsystem.business.services;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Hadouken
 */
public class DBService {
    EntityManager em;
    EntityTransaction et;

    @PostConstruct
    public void init(){
        this.em = (EntityManager) Persistence.createEntityManagerFactory("VotingSystemPU").createEntityManager();
        this.et = em.getTransaction();
    }
    
    public EntityManager getManager(){
        return this.em;
    }
    
    public EntityTransaction getTransaction(){
        return this.et;
    }
}
