package application;

public class State {
	static public final State instance = new State();
	
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
