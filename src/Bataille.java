import java.util.ArrayList;

public class Bataille {
	public static void main(String[] args) {
		// cards est un ArrayList qui va contenir toutes les cartes du jeu
		ArrayList<Carte> cards = new ArrayList<Carte>();
		// cards_on_table est un autre ArrayList qui correspond aux cartes posées sur la table
		// la bataille est un jeu dans lequel on peut poser et jouer un nombre indéfinie de carte
		// sur la table.
		ArrayList<Carte> cards_on_table = new ArrayList<Carte>();

		// création d'une partie
		Partie game = new Partie();
		
		// on génère le jeu de carte
		game.cards_factory(cards);
		
		// on mélange les cartes
		game.shuffling(cards);
		
		// arrivée de deux joueurs
		Joueur John = new Joueur("John");
		Joueur Tony = new Joueur("Tony");
		
		// distribution des cartes
		game.dealing(cards, John, Tony);

		// on commence le jeu
		game.playing(cards_on_table, John, Tony);
		
		};
}
