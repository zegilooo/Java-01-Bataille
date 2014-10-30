import java.util.ArrayList;

public class Partie {

	public void shuffling(ArrayList<Carte> l){
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
	
	public void dealing(ArrayList<Carte> l, Joueur player1, Joueur player2){
		// distribution des cartes
		for (int i = 0; i < l.size(); i++) {
			if (i % 2 == 0) player1.take_card(l.get(i));
			else player2.take_card(l.get(i));
		}
	};
	
	public void playing(ArrayList<Carte> l, Joueur player1, Joueur player2){
		// on demmarre le jeu
		String winner = "";
		boolean game_won = false;
		boolean game_too_long = false;
		boolean bataille = false;
		int round_number = 0;// nombre de tours
		int max_depth = 0; // depth max
		int memo_best_round = 0; // mémoire du meilleur tour
		while(game_won == false && game_too_long == false)
		{
			String comments = "";
			
			round_number++;
			comments +="Tour n°"+Integer.toString(round_number)+"\n";
			
			// les joueurs tirent chacun une carte
			l.add(player1.give_card());
			comments +=player1.name()+" joue "+l.get(l.size()-1).read()+"\n";
			l.add(player2.give_card());
			comments +=player2.name()+" joue "+l.get(l.size()-1).read()+"\n";
			
			// on compare les cartes
			if(l.get(l.size()-1).greater_than(l.get(l.size()-2))){
				winner = player2.name(); // Tony est le dernier à jouer et sa carte est superieure
			} else if (l.get(l.size()-1).lower_than(l.get(l.size()-2))){
				winner = player1.name(); // Tony est le dernier à jouer et sa carte est inferieure
			} else {
				// Bataille !
				bataille=true;
				int p = 0;
				while(bataille){
					p++;
					if(p>max_depth) { // on garde au moins le premier des meilleurs tours en mémoire (meilleur tour = plus grande depth)
						max_depth=p;
						memo_best_round=round_number;
					}
					String depth ="";
					for (int i = 0;i<p;i++) {
						depth+="\t";
						}
					comments +=depth+"Bataille !\n";
					if(player2.show_hand().size()>1 && player1.show_hand().size()>1){
						l.add(player1.give_card());
						comments +=depth+player1.name()+" cache une carte "+l.get(l.size()-1).read()+"\n";
						l.add(player2.give_card());
						comments +=depth+player2.name()+" cache une carte "+l.get(l.size()-1).read()+"\n";
						l.add(player1.give_card());
						comments +=depth+player1.name()+" joue "+l.get(l.size()-1).read()+"\n";
						l.add(player2.give_card());
						comments +=depth+player2.name()+" joue "+l.get(l.size()-1).read()+"\n";
						// on compare les cartes
						if(l.get(l.size()-1).greater_than(l.get(l.size()-2))){
							winner = player2.name(); // Tony est le dernier à jouer et sa carte est superieure
							bataille=false;
						} else if (l.get(l.size()-1).lower_than(l.get(l.size()-2))){
							winner = player1.name(); // Tony est le dernier à jouer et sa carte est inferieure
							bataille=false;
						} else {
							bataille=true;						
						}						
					} else { // un des deux joueurs n'a plus assez de cartes pour jouer
						// alors il donne gentillement la carte qu'il lui reste
						if(player2.show_hand().size()==1){
							l.add(player2.give_card());
							comments +=depth+player2.name()+" n'a plus qu'une seule carte, un "+l.get(l.size()-1).read();
							comments += " qu'il donne gentillement à player1.\n";
							winner = player1.name();
						} 
						else if(player1.show_hand().size()==1){
							l.add(player1.give_card());
							comments +=depth+player1.name()+" n'a plus qu'une seule carte, un "+l.get(l.size()-1).read();
							comments += " qu'il donne gentillement à player2.\n";
							winner = player2.name();
						} else if(player2.show_hand().size()==0){
							l.add(player2.give_card());
							comments +=depth+"Mais Tony n'a plus de carte ! Pas de carte, pas de bataille possible ! \n";
							winner =player1.name();
						} 
						else if(player1.show_hand().size()==0){
							l.add(player1.give_card());
							comments +=depth+"Mais John n'a plus de carte ! Pas de carte, pas de bataille possible ! \n";
							winner = player2.name();
						}  
						bataille = false;
					}
				}
			}
			
			comments += winner + " emporte ce pli.\n";
			
			if (winner.equals(player1.name())){
				while (l.size()!=0){
					player1.take_card(l.get(0));
					l.remove(0);
				}
			} else if (winner.equals(player2.name())){
				while (l.size()!=0){
					player2.take_card(l.get(0));
					l.remove(0);
				}
			}
			
			if (player2.show_hand().size() == 0) {
				comments +="\nHolala ! "+player2.name()+" n'a plus de carte !\n";
				comments +=player1.name()+" est le gagnant !\n";
				game_won = true;	
			} else if (player1.show_hand().size() == 0){
				comments +="\nHolala ! "+player1.name()+" n'a plus de carte !\n";
				comments +=player2.name()+" est le gagnant !\n";
				game_won = true;				
			} else{
				comments +="("+player2.name()+" "+player1.show_hand().size()+" cartes, ";
				comments +=player2.name()+" "+player2.show_hand().size()+" cartes)\n";
			}
					
			if(round_number >=50000 && game_won == false){
				game_too_long = true;
				comments +="La partie est trop longue, et il n'y a plus rien à boire !\n";
				if (player1.show_hand().size()>player2.show_hand().size()){
					comments +=player2.name()+" concède la victoire à "+player1.name()+" "+player1.show_hand().size()+" à "+player2.show_hand().size()+" !\n";
				}else if (player2.show_hand().size()>player1.show_hand().size()){
					comments +=player1.name()+" concède la victoire à "+player2.name()+" "+player2.show_hand().size()+" à "+player1.show_hand().size()+" !\n";
				}else{	
					comments +="Les deux compétiteurs se réjouissent de terminer sur un match nul "+player2.show_hand().size()+" à "+player1.show_hand().size()+" !\n";
				}
				
			}
			System.out.println(comments);
			
		} 	
		if(memo_best_round>0 && max_depth>1) System.out.println("Le plus beau tour a été le n°"+memo_best_round+" avec "+max_depth+" batailles imbriquées.");
		};
		
	};


