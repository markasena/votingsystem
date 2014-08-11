/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package votingsystem.presenter.voter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javax.inject.Inject;
import votingsystem.business.models.Candidate;
import votingsystem.business.services.VoterService;

/**
 * FXML Controller class
 *
 * @author Hadouken
 */
public class VoterPresenter implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Candidate> voterTable;
    
    ObservableList<Candidate> voters;
    ObjectProperty<Candidate> selectedVoter;
    
    @Inject
    VoterService vs;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.voters = FXCollections.observableArrayList();
        this.selectedVoter = new SimpleObjectProperty<>();        
        prepareTable();        
        loadAllVoters();
    }    

    @FXML
    private void searchVoter(ActionEvent event) {
    }

    private void prepareTable() {
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

    private void loadAllVoters() {
       Candidate c = new Candidate("M", "V", "2");
       vs.save(c);
       List<Candidate> voters = vs.all();
        for (Candidate candidate : voters) {
            this.voters.add(candidate);
        }
    }

    private TableColumn createTextColumn(String name, String caption) {
        TableColumn column = new TableColumn(caption);
        column.setCellValueFactory(new PropertyValueFactory<>(name));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        return column;
    }

    
}
