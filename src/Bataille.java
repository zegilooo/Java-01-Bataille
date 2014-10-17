
import java.util.ArrayList;

public class Bataille {
	public static void main(String[] args) {
		
		// initialisations
		int valeurs[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		String couleurs[] = {"coeur","carreau","trefle","pique"};
		ArrayList<Carte> listeCartes = new ArrayList<Carte>();
		ArrayList<Carte> laTable = new ArrayList<Carte>();
		
		// fabrication des cartes
		for (int i = 0; i<couleurs.length; i++){
			for (int j = 0; j<valeurs.length; j++){
				Carte x = new Carte (valeurs[j],couleurs[i]);
				listeCartes.add(x);
			}
		}
		
		melanger(listeCartes);
		
		/*
		for (int i = 0; i < listeCartes.size(); i++) {
			System.out.println(listeCartes.get(i).lireCarte());
		}	
		*/
		
		// arrivée des deux joueurs
		Joueur John = new Joueur();
		Joueur Tony = new Joueur();
		
		// distribution des cartes
		for (int i = 0; i < listeCartes.size(); i++) {
			if (i % 2 == 0) John.prendCarte(listeCartes.get(i));
			else Tony.prendCarte(listeCartes.get(i));
		}	
		
		// on demmarre le jeu
		String winner = "";
		boolean partieGagnee = false;
		boolean partieTropLongue = false;
		boolean bataille = false;
		int nbTours = 0;// nombre de tours
		int maxPronf = 0; // profondeur max
		int memoTour = 0; // mémoire du meilleur tour
		while(partieGagnee == false && partieTropLongue == false)
		{
			String commentaire = "";
			
			nbTours++;
			commentaire +="Tour n°"+Integer.toString(nbTours)+"\n";
			
			// les joueurs tirent chacun une carte
			laTable.add(John.donneCarte());
			commentaire +="John joue "+laTable.get(laTable.size()-1).lireCarte()+"\n";
			laTable.add(Tony.donneCarte());
			commentaire +="Tony joue "+laTable.get(laTable.size()-1).lireCarte()+"\n";
			
			// on compare les cartes
			if(laTable.get(laTable.size()-1).estSuperieure(laTable.get(laTable.size()-2))){
				winner = "Tony"; // Tony est le dernier à  jouer et sa carte est superieure
			} else if (laTable.get(laTable.size()-1).estInferieure(laTable.get(laTable.size()-2))){
				winner = "John"; // Tony est le dernier à  jouer et sa carte est inferieure
			} else {
				// Bataille !
				bataille=true;
				int p = 0;
				while(bataille){
					p++;
					if(p>maxPronf) { // on garde au moins le premier des meilleurs tours en mémoire (meilleur tour = plus grande profondeur)
						maxPronf=p;
						memoTour=nbTours;
					}
					String profondeur ="";
					for (int i = 0;i<p;i++) {
						profondeur+="\t";
						}
					commentaire +=profondeur+"Bataille !\n";
					if(Tony.montrerLaMain().size()>1 && John.montrerLaMain().size()>1){
						laTable.add(John.donneCarte());
						commentaire +=profondeur+"John cache une carte "+laTable.get(laTable.size()-1).lireCarte()+"\n";
						laTable.add(Tony.donneCarte());
						commentaire +=profondeur+"Tony cache une carte "+laTable.get(laTable.size()-1).lireCarte()+"\n";
						laTable.add(John.donneCarte());
						commentaire +=profondeur+"John joue "+laTable.get(laTable.size()-1).lireCarte()+"\n";
						laTable.add(Tony.donneCarte());
						commentaire +=profondeur+"Tony joue "+laTable.get(laTable.size()-1).lireCarte()+"\n";
						// on compare les cartes
						if(laTable.get(laTable.size()-1).estSuperieure(laTable.get(laTable.size()-2))){
							winner = "Tony"; // Tony est le dernier à  jouer et sa carte est superieure
							bataille=false;
						} else if (laTable.get(laTable.size()-1).estInferieure(laTable.get(laTable.size()-2))){
							winner = "John"; // Tony est le dernier à  jouer et sa carte est inferieure
							bataille=false;
						} else {
							bataille=true;						
						}						
					} else { // un des deux joueurs n'a plus assez de cartes pour jouer
						// alors il donne gentillement la carte qu'il lui reste
						if(Tony.montrerLaMain().size()==1){
							laTable.add(Tony.donneCarte());
							commentaire +=profondeur+"Tony n'a plus qu'une seule carte, un "+laTable.get(laTable.size()-1).lireCarte();
							commentaire += " qu'il donne gentillement à  John.\n";
							winner = "John";
						} 
						else if(John.montrerLaMain().size()==1){
							laTable.add(John.donneCarte());
							commentaire +=profondeur+"John n'a plus qu'une seule carte, un "+laTable.get(laTable.size()-1).lireCarte();
							commentaire += " qu'il donne gentillement à  Tony.\n";
							winner = "Tony";
						} else if(Tony.montrerLaMain().size()==0){
							laTable.add(Tony.donneCarte());
							commentaire +=profondeur+"Mais Tony n'a plus de carte ! Pas de carte, pas de bataille possible ! \n";
							winner = "John";
						} 
						else if(John.montrerLaMain().size()==0){
							laTable.add(John.donneCarte());
							commentaire +=profondeur+"Mais John n'a plus de carte ! Pas de carte, pas de bataille possible ! \n";
							winner = "Tony";
						}  
						bataille = false;
					}
				}
			}
			
			commentaire += winner + " emporte ce pli.\n";
			
			if (winner.equals("John")){
				while (laTable.size()!=0){
					John.prendCarte(laTable.get(0));
					//commentaire +=laTable.get(0).lireCarte()+" "+John.montrerLaMain().size()+" - ";//debug
					laTable.remove(0);
				}
			} else if (winner.equals("Tony")){
				while (laTable.size()!=0){
					Tony.prendCarte(laTable.get(0));
					//commentaire +=laTable.get(0).lireCarte()+" "+Tony.montrerLaMain().size()+" - ";//debug
					laTable.remove(0);
				}
			}
			
			if (Tony.montrerLaMain().size() == 0) {
				commentaire +="\nHolala ! Tony n'a plus de carte !\n";
				commentaire +="John est le gagnant !\n";
				partieGagnee = true;	
			} else if (John.montrerLaMain().size() == 0){
				commentaire +="\nHolala ! John n'a plus de carte !\n";
				commentaire +="Tony est le gagnant !\n";
				partieGagnee = true;				
			} else{
				commentaire +="(John "+John.montrerLaMain().size()+" cartes, ";
				commentaire +="Tony "+Tony.montrerLaMain().size()+" cartes)\n";
			}
					
			if(nbTours >=50000 && partieGagnee == false){
				partieTropLongue = true;
				commentaire +="La partie est trop longue, et il n'y a plus rien à  boire !\n";
				if (John.montrerLaMain().size()>Tony.montrerLaMain().size()){
					commentaire +="Tony concède la victoire à  John "+John.montrerLaMain().size()+" à  "+Tony.montrerLaMain().size()+" !\n";
				}else if (Tony.montrerLaMain().size()>John.montrerLaMain().size()){
					commentaire +="John concède la victoire à  Tony "+Tony.montrerLaMain().size()+" à  "+John.montrerLaMain().size()+" !\n";
				}else{	
					commentaire +="Les deux compétiteurs se réjouissent de terminer sur un match nul "+Tony.montrerLaMain().size()+" à  "+John.montrerLaMain().size()+" !\n";
				}
				
			}
			System.out.println(commentaire);
			
		} 	
		if(memoTour>0 && maxPronf>1) System.out.println("Le plus beau tour a été le n°"+memoTour+" avec "+maxPronf+" batailles imbriquées.");
		//if(memoTour>0 && maxPronf==1) System.out.println("Le plus beau tour a été le n°"+memoTour+" avec une seule bataille, pas très palpitant...");
	};
	
	public static void melanger(ArrayList<Carte> l)
	{
	    int n = l.size();
	    for (int i = 0; i < n; i++)
	    {
	        // entre i and n-1
	        int r = i + (int) (Math.random() * (n-i));
	        Carte tmp = l.get(i);    // tampon
	        l.set(i,l.get(r)) ;
	        l.set(r,tmp);
	    }
	};
}
