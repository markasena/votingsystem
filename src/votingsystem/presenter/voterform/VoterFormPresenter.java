/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package votingsystem.presenter.voterform;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javax.imageio.ImageIO;
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
    
    private ObjectProperty<Candidate> selectedCandidate;
    
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
                this.selectedCandidate = new SimpleObjectProperty<>();
        prepareGradeBox();
        this.selectedCandidate.addListener(new ChangeListener<Candidate>() {
            @Override
            public void changed(ObservableValue<? extends Candidate> observable, Candidate oldValue, Candidate newValue) {
                if (newValue != null) {
                    firstNameField.setText(newValue.getFirstName());
                    lastNameField.setText(newValue.getLastName());
                    gradeLevelCBox.getSelectionModel().select(newValue.getGradeLevel()); 
                    if(newValue.getImage().getData() != null){
//                        File dirName = new File("C:\\temp\\");
//                        String base64String=Base64.encode(newValue.getImage().getData().toByteArray());
//                        byte[] bytes = Base64.decode(base64String);
//                        BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytes));
//                        ImageIO.write(imag, "jpg", new File(dirName,"snap.jpg"));
                        img = new Image("file:" +file.getAbsolutePath());
                        candidateImage.setImage(img);     
                    }
                    
                } else {
                    
                }
            }
        });
        if(this.selectedCandidate.get() != null){
            fillForm();
            System.out.println(this.selectedCandidate.get().getFirstName());
        }
        
    }    
    
    public void fillForm(){

    }
    
    public void prepareGradeBox(){
        List<String> gradeList = Arrays.asList("Seven", 
                "Eight", "Nine","Ten","Eleven","Twelve");
        ObservableList<String> oGradeList = FXCollections.observableArrayList(gradeList);
        gradeLevelCBox.setItems(oGradeList);
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
            image.setImageName("test.png");
            image.setData(imageData); 
            candidate.setImage(image);            
        }
        vs.save(candidate);
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
        img = new Image("file:" +file.getAbsolutePath());
        candidateImage.setImage(img);
    }

    private void retrieveImage(){
        
    }
    
  
    /**
     * @return the selectedCandidate
     */
    public ObjectProperty<Candidate> getSelectedCandidate() {
        return selectedCandidate;
    }

    /**
     * @param selectedCandidate the selectedCandidate to set
     */
    public void setSelectedCandidate(ObjectProperty<Candidate> selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }

  
}
