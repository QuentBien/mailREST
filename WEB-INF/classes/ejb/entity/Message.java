package ejb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@Entity
@XmlRootElement(name="message")
@XmlType(propOrder={})
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable{
	
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -5672797311188576288L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	//@Column(name="qqch")    // pour choisir nom de la colonne dans BDD
	@XmlElement (name="objet")
	private String objet;
	@XmlElement (name="corps")
	private String corps;
	@ManyToOne
	@XmlElement (name="emetteur")
	private Compte emetteur;
	@XmlElement (name="date")
	private Date reception;
	@XmlElement (name="etat")
	private EtatMessage etat;
	//ajout
	//clé étrangère emetteur reference Compte(login)
	@ManyToOne
	@XmlElement (name="destinataire")
	private Compte destinataire;
	
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
	public String getObjet() {
		return objet;
	}
	public String getCorps() {
		return corps;
	}
	public Compte getEmetteur() {
		return emetteur;
	}
	public Compte getDestinataire() {
		return destinataire;
	}
	public Date getReception() {
		return reception;
	}
	public EtatMessage getEtat() {
		return etat;
	}
	public void setReception(Date reception) {
		this.reception = reception;
	}
	public void setEtat(EtatMessage etat) {
		this.etat = etat;
	}

	public void setCorps(String corps) {
		this.corps = corps;
	}

	public void setEmetteur(Compte emetteur) {
		this.emetteur = emetteur;
	}

	public void setDestinataire(Compte destinataire) {
		this.destinataire = destinataire;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}
	
}