package com.arachnid92.ship;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.*;

public class Asteroid extends SpaceObject {
	
	/*
	 * Representa un asteroide en el juego.
	 * 
	 * Se encarga de crear y dibujar el asteroide, 
	 * de acuerdo al tamaño especificado.
	 * 
	 * Ademas, mantiene la cuenta de la "integridad"
	 * del asteroide.
	 */

	protected float		scale; /*Escala (no todos los asteroides
														 son del mismo tamaño*/
	protected int	  	integrity; //"Vida"
	protected boolean split; /* Boolean para determinar si el 
														* asteroide debe partirse en dos
														*/
	protected Sound		impact;

	public Asteroid(String name, Vector2f position, Vector2f velocity,
	    Image image, float scale) throws SlickException {
		super(name, position, velocity, image);
		float radius = (image.getWidth() + image.getHeight()) / 4;
		this.scale = scale;
		shape = new Circle(position.x, position.y, radius * scale);
		shape.setCenterX(position.x);
		shape.setCenterY(position.y);
		this.image = image.getScaledCopy(scale);
		this.split = false;
		this.impact = new Sound("resources/impact.wav");

		
		/* La cantidad de vida del asteroide
		   dependera de su tamaño:*/
		if (scale > 2 && scale <= 3) {
			integrity = 15;
		} else if (scale > 1 && scale <= 2) {
			integrity = 10;
		} else {
			integrity = 5;
		}

	}

	public void decIntegrity() {
		
		//Daña el asteroide.

		impact.play();
		integrity--;

	}

	public int getIntegrity() {

		return integrity;

	}

	public float getScale() {

		return scale;

	}
	
	public void mark4Split(){
		split = true;
	}
	
	public boolean marked4Split(){
		return split;
	}

}
