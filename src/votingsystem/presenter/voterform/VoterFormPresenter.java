/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package votingsystem.presenter.voterform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.inject.Inject;
import votingsystem.business.models.Candidate;
import votingsystem.business.models.ImageWrapper;
import votingsystem.business.services.VoterService;
import votingsystem.presenter.voter.VoterPresenter;
import votingsystem.presenter.voter.VoterView;


/**
 * FXML Controller class
 *
 * @author Hadouken
 */
public class VoterFormPresenter implements Initializable {
    @FXML
    private Button saveButton;
    @FXML
    private ImageView candidateImage;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ComboBox<String> gradeLevelCBox;
    @FXML
    private Button choosePhoto;
    @FXML
    private AnchorPane currentPane;
    
    
    Image img;    
    File file;
    VoterPresenter voterPresenter;
    
    @Inject
    VoterService vs;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveVoter(ActionEvent event) {
        Candidate candidate = new Candidate(firstNameField.getText()
                  , lastNameField.getText(), gradeLevelCBox.getSelectionModel().getSelectedItem());
        if(file != null){
            byte[] imageData = new byte[(int) file.length()];
            try {            
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    fileInputStream.read(imageData);
                }
            } catch (IOException e) {
                System.out.println("file error.");
            }
            ImageWrapper image = new ImageWrapper();
            image.setImageName("test.jpg");
            image.setData(imageData); 
        }
        VoterView voterView = new VoterView();
        voterPresenter = (VoterPresenter)voterView.getPresenter();
        
        AnchorPane contentPane = (AnchorPane)currentPane.getParent();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(voterView.getView());
    }

    @FXML
    private void selectPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png)(*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(choosePhoto.getScene().getWindow());
        System.out.println(file);        
        String url = file.getAbsolutePath();
        System.out.println(url);        
        img = new Image("file:" +url);
        candidateImage.setImage(img);
    }
    
}
