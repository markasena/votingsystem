/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package votingsystem.business.services;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import votingsystem.business.models.Candidate;

/**
 *
 * @author Hadouken
 */
public class VoterService {
    
    EntityManager em;
    EntityTransaction et;
    
    
    @PostConstruct
    public void init(){
        this.em = (EntityManager) Persistence.createEntityManagerFactory("VotingSystemPU").createEntityManager();
        this.et = em.getTransaction();
    }
    
    public List<Candidate> all(){
        return em.createNamedQuery("Candidate.findAll").getResultList();
    }
    
    public void save(){
        et.begin();
        em.flush();
        et.commit();
    }
    
    public void save(Candidate candidate){
        et.begin();
        Candidate merged = this.em.merge(candidate);;
        et.commit();
    }
    
    public void remove(Candidate candidate){
        et.begin();
        em.remove(candidate);
        et.commit();
    }
    
    public Candidate find(int id){        
        Candidate c = em.find(Candidate.class, id);
        if(c==null){
            return null;
        }
        return c;
    }
    
    
}
