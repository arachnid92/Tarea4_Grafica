package com.arachnid92.ship;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class ShipGame extends StateBasedGame {
	
	/* Clase principal del juego.
	 * Se encarga de inicializar la ventana,
	 * y los "estados" del juego (Menu, Juego,
	 * Victoria, Empate, etc)".
	 */

	public ShipGame() {
	  super("BattleSpace");
  }
	
	public enum GameStates {
		
		//Estados del juego.
		
	  MENU,
	  INGAME,
	  P1WIN,
	  P2WIN,
	  TIE,
	  PAUSE
	}

	public static void main(String[] args) throws SlickException {

		AppGameContainer app = new AppGameContainer(new ShipGame());

		app.setDisplayMode(800, 600, false);
		app.setShowFPS(false);
		app.setVSync(true);
		app.start();

	}

	@Override
  public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new Menu());
	  addState(new InGame());
	  addState(new P1Win());
	  addState(new P2Win());
	  addState(new Tie());
	  addState(new Pause());
  }

}
