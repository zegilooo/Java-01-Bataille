import java.util.ArrayList;


public class Joueur {

	private ArrayList<Carte> laMain = new ArrayList<Carte>();

	public void prendCarte(Carte c){
		laMain.add(c);
	}
	public Carte donneCarte(){
		if(laMain.size()>0){
			Carte tmp = laMain.get(0);
			laMain.remove(0);
			return tmp;
		}
		else{ 
			return null;
		}
	}	
	public ArrayList<Carte> montrerLaMain(){
		return laMain;
	}

}
