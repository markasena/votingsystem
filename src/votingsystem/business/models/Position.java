/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package votingsystem.business.models;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.*;

/**
 *
 * @author Hadouken
 */
@Entity
@Table(name= "position")
@NamedQueries({
@NamedQuery(name = "Position.findAll", query = "SELECT p FROM Position p"),
    @NamedQuery(name = "Position.findById", query = "SELECT p FROM Position p WHERE p.id = :id"),
    @NamedQuery(name = "Position.findByName", query = "SELECT p FROM Position p WHERE p.name = :name")})
public class Position implements Serializable {
    private IntegerProperty id;
    private StringProperty name;
    private ListProperty<Candidate> candidates;
    
    

    public Position(){
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.candidates = new SimpleListProperty<>();
    }
    
    public Position(String positionName){
        this();
        this.name.set(positionName);
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "position")
    public List<Candidate> getCandidates(){
        return candidates.get();
    }
    
    public void setCandidates(List<Candidate> candidates){
        ObservableList list = FXCollections.observableArrayList(candidates);
        this.candidates.set(list);
    }
    
}
