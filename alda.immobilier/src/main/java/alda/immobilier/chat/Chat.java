package alda.immobilier.chat;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Le Chat pour l'ensemble de l'application, peu importe la page.
 * A ne pas confondre avec le controleur ChatCtrl.
 */

@ManagedBean(name="chat", eager=true)
@ApplicationScoped
public class Chat implements Serializable {
	private static final long serialVersionUID = 1148132624820376439L;
	
	private ArrayList<Message> messages;
	private ArrayList<ChatCtrl> controleurs;
	
	public Chat(){
		messages = new ArrayList<>();
		controleurs = new ArrayList<>();
	}
	
	public void posterMessage(String expediteur, String contenu){
		messages.add(new Message(expediteur, contenu));
		
		for ( ChatCtrl ctrl : controleurs )
			ctrl.setConversation(chaineConversation());
	}
	
	public String chaineConversation(){
		String res = "";
		
		for ( Message msg : messages )
			res += msg.toString() + "\n\n";
		
		return res;
	}
	
	public void ajouterChatCtrl(ChatCtrl ctrl){
		controleurs.add(ctrl);
		ctrl.setConversation(chaineConversation());
	}
	
	public void retirerChatCtrl(ChatCtrl ctrl){
		controleurs.remove(ctrl);
	}
}
