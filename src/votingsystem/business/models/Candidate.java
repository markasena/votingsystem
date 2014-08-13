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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.*;

/**
 *
 * @author Hadouken
 */
@Entity
@Table(name = "candidate")
@NamedQueries({
@NamedQuery(name = "Candidate.findAll", query = "SELECT c FROM Candidate c"),
    @NamedQuery(name = "Candidate.findById", query = "SELECT c FROM Candidate c WHERE c.id = :id"),
    @NamedQuery(name = "Candidate.findByFirstName", query = "SELECT c FROM Candidate c WHERE lower('c.firstName') like :firstName"),
    @NamedQuery(name = "Candidate.findByLastName", query = "SELECT c FROM Candidate c WHERE lower('c.lastName') like :lastName"),
    @NamedQuery(name = "Candidate.findByFirstNameAndLastName", 
            query = "SELECT c FROM Candidate c WHERE lower('c.lastName') like :lastName AND lower('c.firstName') like :firstName" ),
    @NamedQuery(name = "Candidate.findByGradeLevel", query = "SELECT c FROM Candidate c WHERE c.gradeLevel = :gradeLevel")})
public class Candidate implements Serializable {
    private IntegerProperty id;
    private StringProperty firstName;    
    private StringProperty lastName;    
    private StringProperty gradeLevel;
    private ListProperty<Candidate> voter;
    private ObjectProperty<UserAccount> account;
    private ObjectProperty<Position> position;
    private ObjectProperty<Party> partylist;
    private ImageWrapper image;
    
    
    public Candidate() {
        this.id = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.gradeLevel = new SimpleStringProperty();  
        this.voter = new SimpleListProperty<>();
        this.position = new SimpleObjectProperty<>();
        this.partylist = new SimpleObjectProperty<>();
        this.account = new SimpleObjectProperty<>();
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

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @Column(name = "grade_level")
    public String getGradeLevel() {
        return gradeLevel.get();
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel.set(gradeLevel);
    }
    
    
    @ManyToOne(optional = false, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "party", referencedColumnName = "id")
    public Party getParty(){
        return partylist.get();
    }
    
    public void setParty(Party partyList){
        this.partylist.set(partyList);
    }
    

    @ManyToOne(optional = false, cascade=CascadeType.PERSIST,targetEntity=Position.class)
    @JoinColumn(name = "position", referencedColumnName = "id")
    public Position getPosition(){
        return position.get();
    }
    
    public void setPosition(Position pos){
        position.set(pos);
    }
    
    
    @JoinTable(name = "votes", joinColumns = {
    @JoinColumn(name = "voter", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
    @JoinColumn(name = "candidate", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    public List<Candidate> getVoters(){
        return voter.get();
    }
    
    public void setVoters(List<Candidate> voters){   
        ObservableList<Candidate> v = FXCollections.observableArrayList(voters);
        this.voter.set(v);
    }
  

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image")
    public ImageWrapper getImage(){
        return this.image;
    }
    
    
    public void setImage(ImageWrapper image){
        this.image = image;
    }
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account")
    public UserAccount getAccount(){
        return this.account.get();
    }
    
    public void setAccount(UserAccount account){
        this.account.set(account);
    }
        
    
}
