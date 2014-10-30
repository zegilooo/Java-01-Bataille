
public class Carte {

	private int value;
	private String color;
		
	// Constructeurs
	public Carte (int value, String color){
		this.value = value;
		this.color = color;
	}
	
	public Carte (Carte c){
		this.value = c.value;
		this.color = c.color;
	}
	
	// Modifier
	public void modify(int value, String color) {
		this.value = value;
		this.color = color;	
	}
	
	// Accesseurs
	public String read(){
		String rangCarte;
		if (this.value==1) rangCarte="As";
		else if (this.value==11) rangCarte="Valet";
		else if (this.value==12) rangCarte="Dame";
		else if (this.value==13) rangCarte="Roi";
		else rangCarte=Integer.toString(this.value);
		return rangCarte+" de "+this.color;
	}
	
	public int read_value(){
		return this.value;
	}
	
		// comparateurs (mode basique)
	public boolean greater_than (Carte c){
		if (this.value == 1 && c.value!=1) return true;
		if (c.value == 1 && this.value!=1) return false;
		else return this.value>c.value;
	}
	
	public boolean lower_than (Carte c){
		if (this.value == 1 && c.value!=1) return false;
		if (c.value == 1 && this.value!=1) return true;
		return this.value<c.value;
	}	
	
	public boolean equals (Carte c){
		return this.value==c.value;
	}
}
