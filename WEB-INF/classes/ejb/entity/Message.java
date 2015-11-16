package ejb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@SequenceGenerator(name="message_sequence", sequenceName="message_seq")
@Table(name = "mail_message")
@XmlRootElement(name="message")
@XmlType(propOrder = {"id","objet","corps","emetteur","destinataire","envoi","reception","etat"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable{
	@Transient
	@XmlTransient
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "message_sequence")
	private int id;
	@Column(name="objet")  
	private String objet;
	@Column(name="corps") 
	private String corps;
	@ManyToOne
	@JoinColumn(name="emetteur_login", nullable=false)  
	private Compte emetteur;
	@ManyToOne
	@JoinColumn(name="destinataire_login", nullable=false)  
	private Compte destinataire;
	@Column(name="envoi", nullable=false)
	private String envoi;
	@Column(name="reception") 
	private String reception;
	@Column(name="etat", nullable=false)  
	@Enumerated(EnumType.STRING)
	private EtatMessage etat;
	
	public Message(){}
	
	public Message(String objet, String corps, Compte emetteur, Compte destinataire) {
		super();
		this.objet = objet;
		this.corps = corps;
		this.emetteur = emetteur;
		this.destinataire = destinataire;
		this.etat = EtatMessage.NON_LU;
		this.reception = null;
	}
	//getters
	public String getObjet() {return objet;}
	public String getCorps() {return corps;}
	public Compte getEmetteur() {return emetteur;}
	public Compte getDestinataire() {return destinataire;}
	public String getReception() {return reception;}
	public EtatMessage getEtat() {return etat;}
	public int getId() {return id;}
	public String getEnvoi() {return envoi;}
	//setters
	public void setReception(String reception) {this.reception = reception;}
	public void setEtat(EtatMessage etat) {this.etat = etat;}
	public void setCorps(String corps) {this.corps = corps;}
	public void setEmetteur(Compte emetteur) {this.emetteur = emetteur;}
	public void setDestinataire(Compte destinataire) {this.destinataire = destinataire;}
	public void setId(int id) {this.id = id;}
	public void setObjet(String objet) {this.objet = objet;}
	public void setEnvoi(String envoi) {this.envoi = envoi;}

}