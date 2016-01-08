package alda.immobilier.chat;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import alda.immobilier.utilisateur.UserLoginCtrl;
import alda.immobilier.utilisateur.Utilisateur;

@ManagedBean(name="chatSession")
@SessionScoped
public class ChatSession implements Serializable{
	private static final long serialVersionUID = 1148132624820376439L;
	
	@ManagedProperty(value="#{chat}")
	private Chat chat;
	@ManagedProperty(value="#{UserLoginCtrl}")
	private UserLoginCtrl usc;
	private String conversation;
	private int numSession;

	public ChatSession(){
		conversation = new String();
		numSession = -1;
	}
	
	@PostConstruct
	public void init(){
		chat.ajouterChatSession(this);
	}
	
	@PreDestroy
	public void destroy(){
		chat.retirerChatSession(this);
		numSession = -1;
	}

	public String getExpediteurMsg() {
		if ( usc.suisJePasCo() ){
			numSession = (numSession == -1) ? chat.getNbSessions() : numSession;
			return "Visiteur" + numSession;
		} else {
			Utilisateur u = usc.getConnexions().getUtilisateur(usc.getUserLoginId());
			return u.getPrenom() + " " + u.getNom();
		}
	}
	
	public void posterMessage(String contenu){
		chat.posterMessage(getExpediteurMsg(), contenu);
	}

	public UserLoginCtrl getUsc() {
		return usc;
	}
	public void setUsc(UserLoginCtrl usc) {
		this.usc = usc;
	}
	public Chat getChat(){
		return chat;
	}
	public void setChat(Chat chat){
		this.chat = chat;
	}
	public String getConversation() {
		return conversation;
	}
	public void setConversation(String conversation) {
		this.conversation = conversation;
		
		System.out.println(" ... "  + getExpediteurMsg() + " a bien reçu!");
	}
}
