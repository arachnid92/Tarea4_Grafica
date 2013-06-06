package com.arachnid92.ship;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.arachnid92.ship.ShipGame.GameStates;

public class Pause extends BasicGameState{

	/*
	 * Estado pausado del juego.
	 * 
	 * Presenta una imagen y espera a que el
	 * juego se reanude o termine.
	 */
	
	Image pausa;
	
	@Override
  public void init(GameContainer game, StateBasedGame sbg)
      throws SlickException {
	  
		pausa = new Image("resources/pausa.png");
		
  }

	@Override
  public void render(GameContainer game, StateBasedGame sbg, Graphics graphics)
      throws SlickException {
	  
		pausa.drawCentered(400, 300);
	  
  }

	@Override
  public void update(GameContainer game, StateBasedGame sbg, int delta)
      throws SlickException {
	  
		Input input = game.getInput();
		
		//Espera un input. Si se presiona ENTER
		//vuelve al estado "principal" del juego.
		//Si se presiona ESCAPE, finaliza.
		
		if(input.isKeyDown(Input.KEY_ENTER)){
			sbg.enterState(GameStates.INGAME.ordinal());
		} else if (input.isKeyDown(Input.KEY_ESCAPE)){
			sbg.enterState(GameStates.MENU.ordinal());
		}
				
  }

	@Override
  public int getID() {
	  return GameStates.PAUSE.ordinal();
  }
	
	

}
