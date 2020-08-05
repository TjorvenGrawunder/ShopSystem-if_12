package application;

public class WarenkorbElement {
	int id;
	String größe;
	
	public WarenkorbElement(int i, String g) {
		id = i;
		größe = g;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGröße() {
		return größe;
	}

	public void setGröße(String größe) {
		this.größe = größe;
	}
}
