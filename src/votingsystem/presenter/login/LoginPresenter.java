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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;
import votingsystem.business.models.UserAccount;
import votingsystem.business.services.Authenticator;
import votingsystem.presenter.admin.AdminPresenter;
import votingsystem.presenter.admin.AdminView;
import votingsystem.presenter.vote.VotePresenter;
import votingsystem.presenter.vote.VoteView;


/**
 * FXML Controller class
 *
 * @author Hadouken
 */
public class LoginPresenter implements Initializable {

    
    @FXML
    private ComboBox<UserAccount> accountCBox;
    @FXML
    private Button loginButton;
    @FXML
    private TextField passwordField;
    @FXML
    private AnchorPane currentPane;
    
    @Inject
    Authenticator auth;
    
    VotePresenter votePresenter;
    AdminPresenter adminPresenter;
    ObservableList<UserAccount> userList;
    private ObjectProperty<UserAccount> user;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = new SimpleObjectProperty<>();

    }    


    @FXML
    private void loginAccount(ActionEvent event) {          
        this.user.set(auth.findUser(accountCBox.getEditor().textProperty().get()));
        if(this.user.get().getPassword().equals(passwordField.textProperty().get())){
            if(this.getUser().get().getUsername().equals("admin")){
                AdminView adminView = new AdminView();
                this.adminPresenter = (AdminPresenter) adminView.getPresenter();
                this.user.bind(this.adminPresenter.getUser());
                changeContentPane(adminView.getView());
            }else{
                VoteView voteView = new VoteView();
                this.votePresenter = (VotePresenter) voteView.getPresenter();
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
    
    @FXML
    private void mooose(ActionEvent event){
        accountCBox.setEditable(true);
    }

    /**
     * @return the user
     */
    public ObjectProperty<UserAccount> getUser() {
        return user;
    }
    
    public void changeContentPane(Parent parent){
        AnchorPane contentPane = (AnchorPane)currentPane.getParent();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(parent);
        
    }
    
}
