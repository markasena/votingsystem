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
import javafx.beans.property.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Hadouken
 * This entity is using property access.
 */
@Entity
@Table(name="user_accouunt")
@NamedQueries({
@NamedQuery(name = "UserAccount.findAll", query = "SELECT a FROM UserAccount a"),
    @NamedQuery(name = "UserAccount.findById", query = "SELECT a FROM UserAccount a WHERE a.id = :id"),
    @NamedQuery(name = "UserAccount.findByUsername", query = "SELECT a FROM UserAccount a WHERE a.username LIKE :username"),
    @NamedQuery(name = "UserAccount.findByPassword", query = "SELECT a FROM UserAccount a WHERE a.password LIKE :password")})
public class UserAccount implements Serializable {
    private IntegerProperty id;
    private StringProperty username;
    private StringProperty password;
    
    
    public UserAccount(){
        this.id = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
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
    
    @Column(name="username")
    public String getUsername(){
       return this.username.get();
    }
    
    public void setUsername(String username){
        this.username.set(username);
    }
    
    @Column(name="password")
    public String getPassword(){
       return this.password.get();
    }
    
    public void setPassword(String password){
        this.password.set(password);
    }
    
    @Override
    public String toString(){
        return getUsername();
    }
    
}
