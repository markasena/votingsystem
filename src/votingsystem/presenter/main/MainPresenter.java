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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import votingsystem.presenter.admin.AdminView;
import votingsystem.presenter.login.LoginPresenter;
import votingsystem.presenter.vote.VotePresenter;
import votingsystem.presenter.vote.VoteView;
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
//        LoginView loginView = new LoginView();
//        this.loginPresenter = (LoginPresenter) loginView.getPresenter();
////        this.loginPresenter.getUser().bind(this.currentUser);        
//        contentPane.getChildren().clear();
//        contentPane.getChildren().add(loginView.getView());
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

    @FXML
    private void loginAccount(ActionEvent event) {
        this.user.set(auth.findUser(accountCBox.getEditor().textProperty().get()));
        if(this.user.get().getPassword().equals(passwordField.textProperty().get())){
            if(this.getUser().get().getUsername().equals("admin")){
                AdminView adminView = new AdminView();
                this.adminPresenter = (AdminPresenter) adminView.getPresenter();
                this.adminPresenter.getUser().bind(user);
                changeContentPane(adminView.getView());
            }else{
                VoteView voteView = new VoteView();
                this.votePresenter = (VotePresenter) voteView.getPresenter();
                this.user.bind(votePresenter.getCurrentUser());
                changeContentPane(voteView.getView());
            }
        }else{
            
        }
    }

    @FXML
    private void comboAction(Event event) {
       userList = FXCollections.observableList(auth.findByUserName(accountCBox.getEditor().textProperty().get()));
       accountCBox.setItems(userList); 
        
    }
    
    private void changeContentPane(Parent parent){       
        contentPane.getChildren().clear();
        contentPane.getChildren().add(parent);
        
    }
    
}
