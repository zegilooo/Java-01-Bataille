
public class Carte {

	private int valeur;
	private String couleur;
		
	// Constructeur
	public Carte (int valeur, String couleur){
		this.valeur = valeur;
		this.couleur = couleur;
	}
	public Carte (Carte c){
		this.valeur = c.valeur;
		this.couleur = c.couleur;
	}
	// Modifier
	public void modifierCarte(int valeur, String couleur) {
		this.valeur = valeur;
		this.couleur = couleur;	
	}
	
	// Accesseurs
	public String lireCarte(){
		String rangCarte;
		if (this.valeur==1) rangCarte="As";
		else if (this.valeur==11) rangCarte="Valet";
		else if (this.valeur==12) rangCarte="Dame";
		else if (this.valeur==13) rangCarte="Roi";
		else rangCarte=Integer.toString(this.valeur);
		return rangCarte+" de "+this.couleur;
	}
	public int lireValeur(){
		return this.valeur;
	}
	
		// comparateurs (mode basique)
	public boolean estSuperieure (Carte c){
		if (this.valeur == 1 && c.valeur!=1) return true;
		if (c.valeur == 1 && this.valeur!=1) return false;
		else return this.valeur>c.valeur;
	}
	public boolean estInferieure (Carte c){
		if (this.valeur == 1 && c.valeur!=1) return false;
		if (c.valeur == 1 && this.valeur!=1) return true;
		return this.valeur<c.valeur;
	}	
	public boolean estEgale (Carte c){
		return this.valeur==c.valeur;
	}
}
