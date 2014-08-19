/*
 * Voting System
 * Project By:  * 
 * Almiradz Mling  * 
 * Eduard John Madriaga  * 
 * Rodz Aguilar Piang  * 
 * Mark Kendrick Asena * 
 */


package votingsystem.presenter.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;
import votingsystem.business.models.UserAccount;
import votingsystem.business.services.Authenticator;
import votingsystem.business.services.VoterService;
import votingsystem.presenter.admin.AdminPresenter;
import votingsystem.presenter.login.LoginPresenter;
import votingsystem.presenter.login.LoginView;
import votingsystem.presenter.vote.VotePresenter;
import votingsystem.presenter.voter.VoterPresenter;
import votingsystem.presenter.voter.VoterView;
import votingsystem.presenter.voterform.VoterFormPresenter;




/**
 * FXML Controller class
 * 
 * @author Hadouken
 */
public class MainPresenter implements Initializable {
    @FXML
    private MenuItem voterMenu;
    @FXML
    private MenuItem candidateMenu;
    @FXML
    private MenuItem partylistMenu;
    
    @FXML
    private MenuBar mainMenu;
    
    @FXML
    private AnchorPane contentPane;
    
    
    private ObjectProperty<UserAccount> user;
    private ObservableList<UserAccount> userList;
    LoginPresenter loginPresenter;
    AdminPresenter adminPresenter;
    VoterPresenter voterPresenter;
    VotePresenter votePresenter;
    VoterFormPresenter voterFormPresenter;
    @Inject
    VoterService vs;
    @Inject
    Authenticator auth;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<UserAccount> accountCBox;

    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.user = new SimpleObjectProperty<>();
        this.user.addListener((ObservableValue<? extends UserAccount> observable, UserAccount oldValue, UserAccount newValue) -> {
            if(newValue.getUsername().equals("admin")){
                 mainMenu.setVisible(true);
            }
        });
        LoginView loginView = new LoginView();
        this.loginPresenter = (LoginPresenter) loginView.getPresenter();
        this.user.bindBidirectional(this.loginPresenter.getUser());
        contentPane.getChildren().clear();
        contentPane.getChildren().add(loginView.getView());
    }    

    @FXML
    private void searchVoter(ActionEvent event) {
        VoterView voterView = new VoterView();
        voterPresenter = (VoterPresenter) voterView.getPresenter();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(voterView.getView());
    }
    
    
    /**
     * @return the currentUser
     */
    public ObjectProperty<UserAccount> getUser() {
        return user;
    }

    @FXML
    private void searchCandidate(ActionEvent event) {
    }

    @FXML
    private void searchPartylist(ActionEvent event) {
    }

}
