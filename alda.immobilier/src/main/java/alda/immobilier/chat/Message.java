package alda.immobilier.chat;

import java.text.DateFormat;
import java.util.Date;

public class Message {
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
