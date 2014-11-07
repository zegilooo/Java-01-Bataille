
public class Bataille {
	public static void main(String[] args) {

		// arrivée de deux joueurs
		Joueur John = new Joueur("John");
		Joueur Tony = new Joueur("Tony");

		// création d'une partie
		Partie game = new Partie(John, Tony);

		// on commence le jeu
		game.playing();
		
		};
}
