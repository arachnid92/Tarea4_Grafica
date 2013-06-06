package com.arachnid92.ship;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class FuelBottle extends SpaceObject{
	
	/*
	 * Representa un contenedor de combustible.
	 * 
	 * Es simple, pero el hecho que este contenido
	 * en una clase propia permite extenderlo 
	 * facilmente.
	 */

	public FuelBottle(String name, Vector2f position, Vector2f velocity,
      Image image) {
	  super(name, position, velocity, image);
	  this.shape = new Circle(position.x, position.y, image.getHeight()/2);
		shape.setCenterX(position.x);
		shape.setCenterY(position.y);
  }
	
	

}
