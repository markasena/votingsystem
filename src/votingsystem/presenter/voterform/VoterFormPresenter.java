/*
 * Voting System
 * Project By:  * 
 * Almiradz Mling  * 
 * Eduard John Madriaga  * 
 * Rodz Aguilar Piang  * 
 * Mark Kendrick Asena * 
 */



package votingsystem.presenter.voterform;



import java.io.*;
import java.net.URL;
import java.util.*;

import java.util.regex.Pattern;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.inject.Inject;
import votingsystem.business.models.Candidate;
import votingsystem.business.models.ImageWrapper;
import votingsystem.business.models.UserAccount;
import votingsystem.business.services.CandidateService;
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
    private ObjectProperty<Candidate> currentCandidate;
    
    Image img;    
    File file;
    VoterPresenter voterPresenter;
    
    @Inject
    CandidateService vs;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        prepareGradeBox();
        Candidate c = new Candidate();
        
        this.selectedCandidate = new SimpleObjectProperty<>(c);
        this.currentCandidate = new SimpleObjectProperty<>();        
        this.selectedCandidate.addListener(selectedCandidateListener());        
        this.firstNameField.textProperty().addListener(textFieldListeners(this.firstNameField));        
        this.lastNameField.textProperty().addListener(textFieldListeners(this.lastNameField));     
    }    
    
    
    
    public void prepareGradeBox(){
        List<String> gradeList = Arrays.asList("Seven", 
                "Eight", "Nine","Ten","Eleven","Twelve");
        ObservableList<String> oGradeList = FXCollections.observableArrayList(gradeList);
        gradeLevelCBox.setItems(oGradeList);
    }

    @FXML
    private void saveVoter(ActionEvent event) {
        Candidate candidate = selectedCandidate.get();
        candidate.setFirstName(firstNameField.getText().toLowerCase());
        candidate.setLastName(lastNameField.getText().toLowerCase());
        candidate.setGradeLevel(gradeLevelCBox.getSelectionModel().getSelectedItem());
        UserAccount ua;
        if(candidate.getAccount() == null){
            ua = new UserAccount();
            ua.setUsername(candidate.getFirstName()+","+candidate.getLastName());
            ua.setPassword("" + (Math.random() * 1001));
            candidate.setAccount(ua);
        }
        if(file != null){
            byte[] imageData = new byte[(int) file.length()];
            try {            
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    fileInputStream.read(imageData);
                }
            } catch (IOException e) {
                System.out.println("file error.");
        }
        if(imageData != null){
          ImageWrapper image = new ImageWrapper();            
          image.setImageName(candidate.getFirstName()+candidate.getLastName() + ".png");
          image.setData(imageData); 
          candidate.setImage(image);  
        }
        
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
        if(file != null){
            img = new Image("file:" +file.getAbsolutePath());
            candidateImage.setImage(img);
        }
    }


    public ObjectProperty<Candidate> getSelectedCandidate() {
        return selectedCandidate;
    }

    public void setSelectedCandidate(ObjectProperty<Candidate> selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }
    
    
    private ChangeListener<Candidate> selectedCandidateListener(){
       ChangeListener<Candidate> selectedCandidateListener = new ChangeListener<Candidate>() {
            @Override
            public void changed(ObservableValue<? extends Candidate> observable, Candidate oldValue, Candidate newValue) {
                if (newValue != null) {                    
                    firstNameField.setText(firstCharToUpperCase(newValue.getFirstName()));
                    lastNameField.setText(firstCharToUpperCase(newValue.getLastName()));
                    gradeLevelCBox.getSelectionModel().select(newValue.getGradeLevel());    
//                    if(newValue.getImage().getData() != null){
//                        if(!newValue.getImage().getImageName().equals("")){
//                            File dirName = new File("/temp/img");
//                            byte[] bytes = newValue.getImage().getData();
//                            BufferedImage imag;
//                            try {
//                                imag = ImageIO.read(new ByteArrayInputStream(bytes));
//                                ImageIO.write(imag, "jpg", new File(dirName, newValue.getImage().getImageName()));
//                            } catch (IOException ex) {
//                                Logger.getLogger(VoterFormPresenter.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            img = new Image("file:" +dirName.getAbsolutePath() + "\\" + newValue.getImage().getImageName());
//                            System.out.println("file:" +dirName.getAbsolutePath() + "\\" + newValue.getImage().getImageName());
//                            candidateImage.setImage(img);     
//                        }
//                    }                    
                } else {
                    
                }
            }
        };
       return selectedCandidateListener;
    }
    
    
    private String firstCharToUpperCase(String string){
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
    
    private ChangeListener<String> textFieldListeners(TextField tf){
        final Pattern wholeNumberPattern = Pattern.compile("[A-Za-zñÑ]*");
        return new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observableValue, final String oldValue,
                                final String newValue) {
                if (!wholeNumberPattern.matcher(newValue).matches())
                    tf.setText(oldValue);
            }
        };
    }
    


 
  
}
