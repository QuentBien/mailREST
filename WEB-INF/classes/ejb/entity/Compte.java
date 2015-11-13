package ejb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "mail_compte")
@XmlRootElement(name="compte")
@XmlType(propOrder = {"login","password","name","birthday","signup"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Compte implements Serializable{

	@XmlTransient
	private static final long serialVersionUID = 1L;
	@XmlElement (name="login")
	@Id
	@Column(name="login", nullable=false) 
	private String login;
	@XmlElement (name="password")
	@Column(name="password", nullable=false)  
	private String password;
	@XmlElement (name="name")
	@Column(name="name", nullable=false)
	private String name;
	@XmlElement (name="birthday")
	@Column(name="birthday", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;
	@XmlElement (name="signup")
	@Column(name="signup", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date signup;
	//@XmlElementWrapper (name="messagesRecus")
	@OneToMany (mappedBy="destinataire")
	@XmlTransient
	private List<Message> messagesRecus;
	//@XmlElementWrapper (name="messagesEnvoyes")
	@OneToMany (mappedBy="emetteur")
	@XmlTransient
	private List<Message> messagesEnvoyes;
	
	public Compte(){}
	
	public Compte(String login, String password, String name, Date birthday) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		//date au moment de l'inscription
		this.signup = new Date();
	}
	
	public Compte(String login, String password, String name, Date birthday, Date signup) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		//date au moment de l'inscription
		this.signup = signup;
	}

	public String getLogin() {return login;}
	public void setLogin(String login) {this.login = login;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Date getBirthday() {return birthday;}
	public void setBirthday(Date birthday) {this.birthday = birthday;}
	public Date getSignup() {return signup;}
	public List<Message> getMessagesRecus() {return messagesRecus;}
	public void setMessagesRecus(List<Message> messagesRecus) {this.messagesRecus = messagesRecus;}
	public List<Message> getMessagesEnvoyes() {return messagesEnvoyes;}
	public void setMessagesEnvoyes(List<Message> messagesEnvoyes) {this.messagesEnvoyes = messagesEnvoyes;}
	public void setSignup(Date signup) {this.signup = signup;}

}