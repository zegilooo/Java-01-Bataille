import java.util.ArrayList;

public class Bataille {
	public static void main(String[] args) {
		
		// initialisations
		int values[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		String colors[] = {"coeur","carreau","trefle","pique"};
		ArrayList<Carte> cards = new ArrayList<Carte>();
		ArrayList<Carte> cards_on_table = new ArrayList<Carte>();
		
		// fabrication des cartes
		for (int i = 0; i<colors.length; i++){
			for (int j = 0; j<values.length; j++){
				Carte x = new Carte (values[j],colors[i]);
				cards.add(x);
			}
		}
		
		// création d'une partie
		Partie game = new Partie();
		
		// on commence par mélanger les cartes
		game.shuffling(cards);
		
		// arrivée des deux joueurs
		Joueur John = new Joueur("John");
		Joueur Tony = new Joueur("Tony");
		
		// distribution des cartes
		game.dealing(cards, John, Tony);

		// on commence le jeu
		game.playing(cards_on_table, John, Tony);
		
		};
}
