package clientEJB.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@XmlRootElement(name="compte")
@XmlType(propOrder={})
@XmlAccessorType(XmlAccessType.FIELD)
public class Compte implements Serializable{
	
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -5676848848189719727L;
	@Id
	@XmlElement (name="login")
	private String login;
	@XmlElement (name="password")
	private String password;
	@XmlElement (name="name")
	private String name;
	@XmlElement (name="birthday")
	private Date birthday;
	@XmlElement (name="signup")
	private Date signup;
	//ajout
	//login sera la clé étrangère dans la table message
	//un compte possède sa propre liste de messages reçus (et envoyés)
	@OneToMany (mappedBy="destinataire")
	@XmlElementWrapper (name="messagesRecus")
	private List<Message> messagesRecus;
	@OneToMany (mappedBy="emetteur")
	@XmlElementWrapper (name="messagesEnvoyes")
	private List<Message> messagesEnvoyes;
	
	public Compte(){}
	
	public Compte(String login, String password, String name, Date birthday) {
		super();
		this.login = login;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		//date au moment de l'inscription
		this.signup = new Date();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getSignup() {
		return signup;
	}

	public List<Message> getMessagesRecus() {
		return messagesRecus;
	}

	public void setMessagesRecus(List<Message> messagesRecus) {
		this.messagesRecus = messagesRecus;
	}

	public List<Message> getMessagesEnvoyes() {
		return messagesEnvoyes;
	}

	public void setMessagesEnvoyes(List<Message> messagesEnvoyes) {
		this.messagesEnvoyes = messagesEnvoyes;
	}

	public void setSignup(Date signup) {
		this.signup = signup;
	}

}