/*
 * Voting System
 * Project By:  * 
 * Almiradz Mling  * 
 * Eduard John Madriaga  * 
 * Rodz Aguilar Piang  * 
 * Mark Kendrick Asena * 
 */


package votingsystem.presenter.voter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javax.inject.Inject;
import votingsystem.business.models.Candidate;
import votingsystem.business.services.VoterService;
import votingsystem.presenter.voterform.VoterFormPresenter;
import votingsystem.presenter.voterform.VoterFormView;

/**
 * FXML Controller class
 *
 * @author Hadouken
 */
public class VoterPresenter implements Initializable {
    
    @FXML
    private Button searchButton,deleteButton,editButton;    
    @FXML
    private TextField searchField;    
    @FXML
    private AnchorPane currentPane;
    @FXML
    private StackPane stackPane;    
    private TableView<Candidate> voterTable;    
    ProgressIndicator progressIndicator;    
    Region veil;    
    Task<ObservableList<Candidate>> task;
    ObservableList<Candidate> voters;
    ObjectProperty<Candidate> selectedVoter;    
    VoterFormPresenter voterFormPresenter;
    VoterFormView voterFormView;
    
    @Inject
    VoterService vs;
    
    
    @FXML
    private Button addVoter;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.voters = FXCollections.observableArrayList();
        this.selectedVoter = new SimpleObjectProperty<>();              
        prepareTable();        
        loadAllVoters();    
        deleteButton.disableProperty().bind(voterTable.getSelectionModel().selectedItemProperty().isNull()); 
        editButton.disableProperty().bind(voterTable.getSelectionModel().selectedItemProperty().isNull()); 
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());  
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.isEmpty()){
                    loadAllVoters();
                } 
            }
        });
    }    

    @FXML
    private void searchVoter(ActionEvent event) {        
        loadAllVoters();
    }
    
    @FXML
    private void deleteVoter(ActionEvent event) {
        if(voterTable.getSelectionModel().getSelectedItem() != null){
            vs.remove(voterTable.getSelectionModel().getSelectedItem());
            stackPane.getChildren().clear();
            loadAllVoters();
        }
    }
    
    @FXML
    private void editVoter(ActionEvent event) {        
        if(voterTable.getSelectionModel().getSelectedItem() != null){
            voterFormView = new VoterFormView();
            voterFormPresenter = (VoterFormPresenter) voterFormView.getPresenter();
            this.selectedVoter.set(voterTable.getSelectionModel().getSelectedItem());
            voterFormPresenter.getSelectedCandidate().bind(selectedVoter);
            AnchorPane contentPane = (AnchorPane)currentPane.getParent();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(voterFormView.getView());
        }
    }
    
    
    //Prepare the table and columns
    private void prepareTable(){
        voterTable = new TableView<>();
        this.voterTable.setEditable(false);        
        ObservableList columns = voterTable.getColumns();
        final TableColumn firstNameColumn = createTextColumn("firstName", "First Name");
        columns.add(firstNameColumn);
        final TableColumn lastNameColumn = createTextColumn("lastName", "Last Name");
        columns.add(lastNameColumn);
        final TableColumn gradeLevelColumn = createTextColumn("gradeLevel", "Grade Level");
        columns.add(gradeLevelColumn);
        voterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        voterTable.setItems(this.voters);
        voterTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);        
    }
    
    //create text column
    private TableColumn createTextColumn(String name, String caption) {
        TableColumn column = new TableColumn(caption);
        column.setCellValueFactory(new PropertyValueFactory<>(name));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        return column;
    }
    
    //load all candidates from DB
    private void loadAllVoters() {
        stackPane.getChildren().clear();
        progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(150, 150);
        veil = new Region();
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
        task = new Task<ObservableList<Candidate>>() {
            @Override
            protected ObservableList<Candidate> call() throws Exception {                
                    List<Candidate> votersList= vs.all();
                if(!searchField.getText().isEmpty()){
                    votersList = vs.search(searchField.textProperty().get());
                }                
                Thread.sleep(5);
                voters = FXCollections.observableArrayList(votersList);                
                return voters;
            }
        };
        progressIndicator.progressProperty().bind(task.progressProperty());
        veil.visibleProperty().bind(task.runningProperty());
        progressIndicator.visibleProperty().bind(task.runningProperty());
        voterTable.itemsProperty().bind(task.valueProperty()); 
        stackPane.getChildren().addAll(voterTable,veil,progressIndicator);
        new Thread(task).start();
    }

    @FXML
    private void addVoter(ActionEvent event) {
        VoterFormView voterFormView = new VoterFormView();
        voterFormPresenter = (VoterFormPresenter) voterFormView.getPresenter();
        AnchorPane contentPane = (AnchorPane)currentPane.getParent();
        contentPane.getChildren().clear();
        contentPane.getChildren().add(voterFormView.getView());
    }
    
    
}
