import java.util.ArrayList;

public class Joueur {
	
	private ArrayList<Carte> hand = new ArrayList<Carte>();
	public String _name;
	
	public Joueur(String name){
		this._name = name;
	}
	
	public String name(){
		return this._name;
	}
	
	public void take_card(Carte c){
		hand.add(c);
	}
	
	public Carte give_card(){
		if(hand.size()>0){
			Carte tmp = hand.get(0);
			hand.remove(0);
			return tmp;
		}
		else{ 
			return null;
		}
	}	
	
	public ArrayList<Carte> show_hand(){
		return hand;
	}
}
