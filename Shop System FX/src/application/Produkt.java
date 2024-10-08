package application;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.StringProperty;

public class Produkt extends RecursiveTreeObject<Produkt>{
	//Komponenten eines Produkts
	private StringProperty id;
	private StringProperty productName;
	private StringProperty price;
	private StringProperty groe�e;
	
	//Getter und Setter der einzelnen Komponenten
	public StringProperty getId() {
		return id;
	}
	public void setId(StringProperty id) {
		this.id = id;
	}
	public StringProperty getProductName() {
		return productName;
	}
	public void setProductName(StringProperty productName) {
		this.productName = productName;
	}
	public StringProperty getPrice() {
		return price;
	}
	public void setPrice(StringProperty price) {
		this.price = price;
	}
	public StringProperty getGroe�e() {
		return groe�e;
	}
	public void setGroe�e(StringProperty groe�e) {
		this.groe�e = groe�e;
	}
}
