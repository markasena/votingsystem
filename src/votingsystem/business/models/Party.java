/*
 * Voting System
 * Project By:  * 
 * Almiradz Mling  * 
 * Eduard John Madriaga  * 
 * Rodz Aguilar Piang  * 
 * Mark Kendrick Asena * 
 */

package votingsystem.business.models;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.*;
import javafx.collections.*;
import javax.persistence.*;
/**
 *
 * @author Hadouken
 */
@Entity
@Table(name= "party")
@NamedQueries({
@NamedQuery(name = "Party.findAll", query = "SELECT p FROM Party p"),
    @NamedQuery(name = "Party.findById", query = "SELECT p FROM Party p WHERE p.id = :id"),
    @NamedQuery(name = "Party.findByName", query = "SELECT p FROM Party p WHERE p.name = :name")})
public class Party implements Serializable {
    private IntegerProperty id;
    private StringProperty name;
    private ListProperty<Candidate> candidates;

    public Party(){
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.candidates = new SimpleListProperty<>();
    }
    
    public Party(String partyName){
        this();
        this.name.set(partyName);
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }
    @Column(name="name")
    public String getName(){
        return name.get();
    }
    
    public void setName(String name){
        this.name.set(name);
    }    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "party")
    public List<Candidate> getCandidates(){
        return candidates.get();
    }
    
    public void setCandidates(List<Candidate> candidates){
        ObservableList list = FXCollections.observableArrayList(candidates);
        this.candidates.set(list);
    }
    
    
}
