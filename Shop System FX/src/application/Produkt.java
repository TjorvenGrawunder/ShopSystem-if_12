package application;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.StringProperty;

public class Produkt extends RecursiveTreeObject<Produkt>{
	private StringProperty id;
	private StringProperty productName;
	private StringProperty price;
	private StringProperty groeﬂe;
	
	
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
	public StringProperty getGroeﬂe() {
		return groeﬂe;
	}
}
