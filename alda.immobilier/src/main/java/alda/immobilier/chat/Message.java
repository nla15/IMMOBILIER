package alda.immobilier.chat;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Message implements Serializable{
	private static final long serialVersionUID = 8156682447165775652L;
	private static final DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
	
	private final String expediteur, contenu;
	private final Date d;
	
	Message(String expediteur, String contenu){
		this.expediteur = expediteur;
		this.contenu = contenu;
		this.d = new Date();
	}
	
	public String toString(){
		return expediteur + ", à " + df.format(d) + " :\n" + contenu;
	}
}
