package com.arachnid92.ship;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.arachnid92.ship.ShipGame.GameStates;

public class Menu extends BasicGameState{
	
	/*
	 * Estado "Menu" del juego. 
	 * Este es el estado inicial del juego,
	 * presenta una imagen y espera que el
	 * usuario presione ENTER para comenzar
	 * el juego.
	 */
	
	private Image	title; //Imagen inicial
	private Music loop; //Musica de fondo
	
	@Override
  public void enter(GameContainer game, StateBasedGame sbg)
      throws SlickException {
	  
		loop = new Music("resources/menu_loop.wav");
		loop.loop(1, 0.5f);
	  
  }

	@Override
  public void init(GameContainer game, StateBasedGame sbg)
      throws SlickException {
	  title = new Image("resources/titlescreen.png");
	  
  }

	@Override
  public void render(GameContainer game, StateBasedGame sbg, Graphics graphics)
      throws SlickException {
		title.drawCentered(400, 300);
  }

	@Override
  public void update(GameContainer game, StateBasedGame sbg, int delta)
      throws SlickException {
	  
		//Espera un input. Si se presiona ENTER
		//ingresa al estado "principal" del juego.
		//Si se presiona ESCAPE, finaliza.
		Input input = game.getInput();
		if(input.isKeyDown(Input.KEY_ENTER)){
			sbg.enterState(GameStates.INGAME.ordinal());
		} else if (input.isKeyDown(Input.KEY_ESCAPE)){
			game.exit();
		}
	  
  }

	@Override
  public int getID() {

	  return GameStates.MENU.ordinal();
  }
	
	

}
