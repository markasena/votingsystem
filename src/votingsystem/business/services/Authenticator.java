/*
 * Voting System
 * Project By:  * 
 * Almiradz Mling  * 
 * Eduard John Madriaga  * 
 * Rodz Aguilar Piang  * 
 * Mark Kendrick Asena * 
 */


package votingsystem.business.services;


import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;
import votingsystem.business.models.UserAccount;

/**
 *
 * @author Hadouken
 */
public class Authenticator {
    @Inject
    DBService es;
    
    public List<UserAccount> findByUserName(String search){
         List<UserAccount> searched;
         Query query = es.getManager().createNamedQuery("UserAccount.findByUsername");
         query.setParameter("username", "%" + search + "%");
         searched = query.getResultList();
         return searched;
    }
    
    public UserAccount findById(Integer id){        
        return es.getManager().find(UserAccount.class, id);
    }
    
    public UserAccount findUser(String username){
        UserAccount ua;
        Query query = es.getManager().createNamedQuery("UserAccount.findByUsername");
        query.setParameter("username", username);
        ua = (UserAccount)query.getSingleResult();
        return ua;
    }
}
