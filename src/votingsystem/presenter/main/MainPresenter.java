/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package votingsystem.presenter.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import votingsystem.presenter.voter.VoterPresenter;
import votingsystem.presenter.voter.VoterView;
import votingsystem.presenter.voterform.VoterFormPresenter;
import votingsystem.presenter.voterform.VoterFormView;



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
    private AnchorPane contentPane;
    
    VoterPresenter voterPresenter;
    VoterFormPresenter voterFormPresenter;

    
    @FXML
    private MenuItem addVoter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    @FXML
    private void searchVoter(ActionEvent event) {
        VoterView voterView = new VoterView();
        voterPresenter = (VoterPresenter) voterView.getPresenter();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(voterView.getView());
    }
    @FXML
    private void addVoter(ActionEvent event) {
        VoterFormView voterFormView = new VoterFormView();
        voterFormPresenter = (VoterFormPresenter) voterFormView.getPresenter();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(voterFormView.getView());
    }

    @FXML
    private void searchCandidate(ActionEvent event) {
        
        
    }

    @FXML
    private void searchPartylist(ActionEvent event) {
        
        
    }



    
    
    
}
