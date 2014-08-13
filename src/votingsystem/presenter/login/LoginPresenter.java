/*
 * Voting System
 * Project By:  * 
 * Almiradz Mling  * 
 * Eduard John Madriaga  * 
 * Rodz Aguilar Piang  * 
 * Mark Kendrick Asena * 
 */

package votingsystem.presenter.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Hadouken
 */
public class LoginPresenter implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private Button findButton;
    @FXML
    private ComboBox<?> accountCBox;
    @FXML
    private Button loginButton;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void findAccount(ActionEvent event) {
    }

    @FXML
    private void loginAccount(ActionEvent event) {
    }
    
}
