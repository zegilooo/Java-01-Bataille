import java.util.ArrayList;

public class Partie {
	
	private ArrayList<Carte> cards = new ArrayList<Carte>();
	private ArrayList<Carte> cards_on_table = new ArrayList<Carte>();
	private Joueur player1; 
	private Joueur player2;
	
	public Partie (Joueur player1, Joueur player2){
		this.player1 = player1;
		this.player2 = player2;
		cards_factory();
		shuffling();
		dealing();
		
	}
	
	public void comment (String c){
		System.out.println(c);
	}
	
	public void shuffling(){
	    int n = this.cards.size();
	    for (int i = 0; i < n; i++)
	    {
	        int r = i + (int) (Math.random() * (n-i));
	        Carte tmp = this.cards.get(i);    // tampon
	        this.cards.set(i,this.cards.get(r)) ;
	        this.cards.set(r,tmp);
	    }
	};
	
	public void cards_factory(){
		int values[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		String colors[] = {"coeur","carreau","trefle","pique"};		
		for (int i = 0; i<colors.length; i++){
			for (int j = 0; j<values.length; j++){
				Carte x = new Carte (values[j],colors[i]);
				this.cards.add(x);
			}
		}
	};
	
	public void dealing(){
		for (int i = 0; i < this.cards.size(); i++) {
			if (i % 2 == 0) this.player1.take_card(this.cards.get(i));
			else this.player2.take_card(this.cards.get(i));
		}
	};
	
	public void playing(){
		// on demmarre le jeu
		String winner = "";
		boolean game_won = false;
		boolean game_too_long = false;
		boolean bataille = false;
		int round_number = 0;// numéro de tours
		int max_depth = 0; // depth max
		int memo_best_round = 0; // mémoire du meilleur tour
		while(game_won == false && game_too_long == false){
			
			round_number++;
			
			comment("Tour n°"+Integer.toString(round_number)+"");
			
			// les joueurs tirent chacun une carte
			this.cards_on_table.add(this.player1.give_card());
			comment(this.player1.name()+" joue "+this.cards_on_table.get(this.cards_on_table.size()-1).read()+"");
			this.cards_on_table.add(this.player2.give_card());
			comment(this.player2.name()+" joue "+this.cards_on_table.get(this.cards_on_table.size()-1).read()+"");
			
			// on compare les cartes
			if(this.cards_on_table.get(this.cards_on_table.size()-1).greater_than(this.cards_on_table.get(this.cards_on_table.size()-2))){
				winner = this.player2.name(); // this.player2 est le dernier à jouer et sa carte est superieure
			} else if (this.cards_on_table.get(this.cards_on_table.size()-1).lower_than(this.cards_on_table.get(this.cards_on_table.size()-2))){
				winner = this.player1.name(); // this.player2 est le dernier à jouer et sa carte est inferieure
			} else {
				// Bataille !
				bataille=true;
				int p = 0;
				while(bataille){
					p++;
					if(p>max_depth) { // on garde au moins le premier des meilleurs tours en mémoire (meilleur tour = plus grande profondeur)
						max_depth=p;
						memo_best_round=round_number;
					}
					String depth ="";
					for (int i = 0;i<p;i++) {
						depth+="\t";
						}
					comment(depth+"Bataille !");
					if(this.player2.show_hand().size()>1 && this.player1.show_hand().size()>1){
						this.cards_on_table.add(this.player1.give_card());
						comment(depth+this.player1.name()+" cache une carte "+this.cards_on_table.get(this.cards_on_table.size()-1).read()+"");
						this.cards_on_table.add(this.player2.give_card());
						comment(depth+this.player2.name()+" cache une carte "+this.cards_on_table.get(this.cards_on_table.size()-1).read()+"");
						this.cards_on_table.add(this.player1.give_card());
						comment(depth+this.player1.name()+" joue "+this.cards_on_table.get(this.cards_on_table.size()-1).read()+"");
						this.cards_on_table.add(this.player2.give_card());
						comment(depth+this.player2.name()+" joue "+this.cards_on_table.get(this.cards_on_table.size()-1).read()+"");
						// on compare les cartes
						if(this.cards_on_table.get(this.cards_on_table.size()-1).greater_than(this.cards_on_table.get(this.cards_on_table.size()-2))){
							winner = this.player2.name(); // this.player2 est le dernier à jouer et sa carte est superieure
							bataille=false;
						} else if (this.cards_on_table.get(this.cards_on_table.size()-1).lower_than(this.cards_on_table.get(this.cards_on_table.size()-2))){
							winner = this.player1.name(); // this.player2 est le dernier à jouer et sa carte est inferieure
							bataille=false;
						} else {
							bataille=true;						
						}						
					} else { 	
						// un des deux joueurs n'a plus assez de cartes pour jouer
						// alors il donne gentillement la carte qu'il lui reste
						if(this.player2.show_hand().size()==1){
							this.cards_on_table.add(this.player2.give_card());
							comment(depth+this.player2.name()+" n'a plus qu'une seule carte, un "+this.cards_on_table.get(this.cards_on_table.size()-1).read());
							comment( " qu'il donne gentillement à "+this.player1.name()+".");
							winner = this.player1.name();
						} 
						else if(this.player1.show_hand().size()==1){
							this.cards_on_table.add(this.player1.give_card());
							comment(depth+this.player1.name()+" n'a plus qu'une seule carte, un "+this.cards_on_table.get(this.cards_on_table.size()-1).read());
							comment( " qu'il donne gentillement à"+this.player2.name()+".");
							winner = this.player2.name();
						} else if(this.player2.show_hand().size()==0){
							this.cards_on_table.add(this.player2.give_card());
							comment(depth+"Mais "+this.player2.name()+" n'a plus de carte ! Pas de carte, pas de bataille possible ! ");
							winner =this.player1.name();
						} 
						else if(this.player1.show_hand().size()==0){
							this.cards_on_table.add(this.player1.give_card());
							comment(depth+"Mais "+this.player1.name()+" n'a plus de carte ! Pas de carte, pas de bataille possible ! ");
							winner = this.player2.name();
						}  
						bataille = false;
					}
				}
			}
			comment( winner + " emporte ce pli.");
			if (winner.equals(this.player1.name())){
				while (this.cards_on_table.size()!=0){
					this.player1.take_card(this.cards_on_table.get(0));
					this.cards_on_table.remove(0);
				}
			} else if (winner.equals(this.player2.name())){
				while (this.cards_on_table.size()!=0){
					this.player2.take_card(this.cards_on_table.get(0));
					this.cards_on_table.remove(0);
				}
			}
			if (this.player2.show_hand().size() == 0) {
				comment("\nHolala ! "+this.player2.name()+" n'a plus de carte !");
				comment(this.player1.name()+" est le gagnant !");
				game_won = true;	
			} else if (this.player1.show_hand().size() == 0){
				comment("Holala ! "+this.player1.name()+" n'a plus de carte !");
				comment(this.player2.name()+" est le gagnant !");
				game_won = true;				
			} else{
				comment("("+this.player1.name()+" "+this.player1.show_hand().size()+" cartes, "+this.player2.name()+" "+this.player2.show_hand().size()+" cartes)\n");
			}
					
			if(round_number >=50000 && game_won == false){
				game_too_long = true;
				comment("La partie est trop longue, et il n'y a plus rien à boire !");
				if (this.player1.show_hand().size()>this.player2.show_hand().size()){
					comment(this.player2.name()+" concède la victoire à "+this.player1.name()+" "+this.player1.show_hand().size()+" à "+this.player2.show_hand().size()+" !");
				}else if (this.player2.show_hand().size()>this.player1.show_hand().size()){
					comment(this.player1.name()+" concède la victoire à "+this.player2.name()+" "+this.player2.show_hand().size()+" à "+this.player1.show_hand().size()+" !");
				}else{	
					comment("Les deux compétiteurs se réjouissent de terminer sur un match nul "+this.player2.show_hand().size()+" à "+this.player1.show_hand().size()+" !");
				}
				
			}
			
		} 	
		if(memo_best_round>0 && max_depth>1) comment("Le plus beau tour a été le n°"+memo_best_round+" avec "+max_depth+" batailles imbriquées.");
		};
		
	};


