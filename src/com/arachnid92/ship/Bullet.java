package com.arachnid92.ship;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

public class Bullet extends SpaceObject {
	
	/*
	 * Objeto que representa una bala.
	 * 
	 * Posee ademas un contador para determinar
	 * la duracion de la bala.
	 */

	protected int	bulletcounter;

	public Bullet(String name, Vector2f position, Vector2f velocity, Image image) {
		super(name, position, velocity, image);
		shape = new Circle(position.x, position.y, 4);
		shape.setCenterX(position.x);
		shape.setCenterY(position.y);
		bulletcounter = 150;

	}

	@Override
	public void updatePosition(int delta) throws SlickException {

		bulletcounter -= 1; //Se disminuye la "vida" temporal de la bala

		position.x += velocity.x * delta;
		position.y -= velocity.y * delta;

		if (position.x > 800) {
			position.x = 0;
		} else if (position.x < 0) {
			position.x = 800;
		}

		if (position.y > 600) {
			position.y = 0;
		} else if (position.y < 0) {
			position.y = 600;
		}

		shape.setCenterX(position.x);
		shape.setCenterY(position.y);

	}

	public int getCounter() {
		return bulletcounter;
	}

}
