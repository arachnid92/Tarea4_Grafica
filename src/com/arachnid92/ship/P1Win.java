package com.arachnid92.ship;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.arachnid92.ship.ShipGame.GameStates;

public class P1Win extends BasicGameState{
	
	/*
	 * Estado del juego llamado cuando gana
	 * el jugador 1.
	 * 
	 * Presenta una imagen y espera a que el
	 * juego se reinicie o termine.
	 */

	private Image pwin;
	
	@Override
  public void init(GameContainer game, StateBasedGame sbg)
      throws SlickException {
	  
		pwin = new Image("resources/p1win.png");
	  
  }

	@Override
  public void render(GameContainer game, StateBasedGame sbg, Graphics graphics)
      throws SlickException {
		
		pwin.drawCentered(400, 300);
		
  }

	@Override
  public void update(GameContainer game, StateBasedGame sbg, int delta)
      throws SlickException {
	  
		Input input = game.getInput();
		
		//Espera un input. Si se presiona ENTER
		//ingresa al estado "principal" del juego.
		//Si se presiona ESCAPE, finaliza.
		
		if(input.isKeyDown(Input.KEY_ENTER)){
			sbg.enterState(GameStates.INGAME.ordinal());
		} else if (input.isKeyDown(Input.KEY_ESCAPE)){
			game.exit();
		}
		
  }

	@Override
  public int getID() {

	  return GameStates.P1WIN.ordinal();
  }
	
	

}
