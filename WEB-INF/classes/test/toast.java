package test;

import java.util.Date;
import java.util.List;
//import java.util.Date;
import java.util.Properties;

import javax.naming.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import ejb.entity.Compte;
import ejb.entity.Message;
import ejb.service.IMessagerie;
import rest.mailRessource;

public class toast {

	public static void main(String[] args) throws NamingException {
		try {
			mailRessource m = new mailRessource();
			//String s = m.consulterCompte("luffy");
			//System.out.println(s);
			Compte c = (Compte) m.consulterCompte("toto2").getEntity();
			/*
			m.creerCompte("toto2", "TOTO2", "bien", new Date());
			m.creerCompte("toto1", "TOTO1", "cool", new Date());
			m.creerCompte("toto3", "TOTO3", "super", new Date());
			m.creerCompte("toto4", "TOTO4", "parfait", new Date());
			*/
			System.out.println("Login : " + c.getLogin() + "\n" +
					"Naissance : " + c.getBirthday() + "\n" +
					"Nom : " + c.getName() + "\n" +
					"Mot de passe : " + c.getPassword() + "\n" +
					"Date inscription : " + c.getSignup() + "\n");
			/*
			JAXBContext jc = JAXBContext.newInstance(Compte.class);
			Marshaller mar = jc.createMarshaller();
			mar.marshal(c, System.out);
			*/
			List<Message> l = (List<Message>) m.releverCourrier("toto2");
			String s = "\n";
			s += "Emetteur : " + l.get(0).getEmetteur().getLogin() + "\n";
			s += "Destinataire : " + l.get(0).getDestinataire().getLogin() + "\n";
			s += "Objet : " + l.get(0).getObjet() + "\n";
			s += l.get(0).getCorps() + "\n";
			s += "Date de récéption : " + l.get(0).getReception() + "\n";
			s += "Date d'envoi : " + l.get(0).getEnvoi();
			System.out.println(s);
			

			//toto.supprimerMessagesLus("toto2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}