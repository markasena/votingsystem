/*
 * Voting System
 * Project By:  * 
 * Almiradz Mling  * 
 * Eduard John Madriaga  * 
 * Rodz Aguilar Piang  * 
 * Mark Kendrick Asena * 
 */

package votingsystem.presenter.vote;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import votingsystem.business.models.UserAccount;

/**
 * FXML Controller class
 *
 * @author Hadouken
 */
public class VotePresenter implements Initializable {
    
    
    private ObjectProperty<UserAccount> currentUser;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentUser = new SimpleObjectProperty<>();
        
        
    }    

    /**
     * @return the currentUser
     */
    public ObjectProperty<UserAccount> getCurrentUser() {
        return currentUser;
    }
    
}
