package application;

public class State {
	static public final State instance = new State();
	//Hier wird der Aktuelle Status eines Users gespeichert
	//Somit kann man auf den Benutzernamen des Users zugreifen, nachdem die Anmeldung erfolgt ist
	private String user;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	private State() {
		
	}
	public static State getInstance() {
		return instance;
	}
}
