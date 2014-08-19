/*
 * Voting System
 * Project By:  * 
 * Almiradz Mling  * 
 * Eduard John Madriaga  * 
 * Rodz Aguilar Piang  * 
 * Mark Kendrick Asena * 
 */


package votingsystem.business.services;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import votingsystem.business.models.Candidate;

/**
 *
 * @author Hadouken
 * This class handles all transaction for the Candidate Entity
 */
public class CandidateService {
    
    @Inject
    private DBService service;

    
    public List<Candidate> all(){
        return service.getManager().createNamedQuery("Candidate.findAll").getResultList();
    }
    
    public List<Candidate> search(String search){        
        List<Candidate> searched = new ArrayList<>();
        Query queryCandidatesByFirstName = service.getManager().createNamedQuery("Candidate.findByFirstName");
        Query queryCandidatesByLastName = service.getManager().createNamedQuery("Candidate.findByLastName");
        queryCandidatesByFirstName.setParameter("firstName","%" + search + "%");
        queryCandidatesByLastName.setParameter("lastName", "%" + search + "%");
        List<Candidate> firstNameList = queryCandidatesByFirstName.getResultList();
        List<Candidate> lastNameList = queryCandidatesByLastName.getResultList();
        firstNameList.stream().filter((candidate) -> (!searched.contains(candidate))).forEach((candidate) -> {
            searched.add(candidate);
        });
        lastNameList.stream().filter((candidate) -> (!searched.contains(candidate))).forEach((candidate) -> {
            searched.add(candidate);
        });        

        return searched;
    }
    
    public List<Candidate> searchByGradeLevel(String gradeLevel){
        Query query = service.getManager().createNamedQuery("Candidate.findByGradeLevel");
        query.setParameter("gradeLevel", gradeLevel);
        return query.getResultList();
    }
    
    
   
    public void save(Candidate candidate){
        service.getTransaction().begin();
        Candidate merged = this.service.getManager().merge(candidate);;
        service.getTransaction().commit();
    }
    
    public void remove(Candidate candidate){
        service.getTransaction().begin();
        service.getManager().remove(candidate);
        service.getTransaction().commit();
    }
    
    public Candidate find(int id){        
        Candidate c = service.getManager().find(Candidate.class, id);
        if(c==null){
            return null;
        }
        return c;
    }
    
    
}
