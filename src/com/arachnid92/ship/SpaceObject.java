package com.arachnid92.ship;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class SpaceObject {
	
	/*
	 * "Superclase" para los objetos que 
	 * tienen movimiento en pantalla.
	 * Implementa metodos para dibujarlo
	 * en pantalla, para detectar colisiones
	 * y para su movimiento incercial.
	 */

	protected Vector2f	position; //Posicion
	protected Vector2f	velocity; //Velocidad
	protected Vector2f	rotation; //Direccion
	protected Image	   image; //Imagen asociada al objeto
	protected String	 name;	//Nombre (para su identificacion)
	protected float	   speed; //Rapidez
	protected float	   angle; 
	protected Shape	   shape; //Figura asociada, para detectar colisiones
	protected boolean	 dead; //Variable para marcar si el objeto debe destruirse.

	public SpaceObject(String name, Vector2f position, Vector2f velocity,
	    Image image) {

		//Inicializador, crea el objeto en el lugar
		//especificado con la velocidad especificada.
		
		this.speed = 0;
		this.rotation	= new Vector2f(0, 0);
		this.position = position;
		this.image = image;
		this.name = name;
		this.velocity = velocity;
		angle = image.getRotation();
		rotation.x = (float) Math.sin(Math.toRadians(angle));
		rotation.y = (float) Math.cos(Math.toRadians(angle));
		this.dead = false;

	}

	public Shape getShape() {
		return shape;
	}

	public String getName() {
		return name;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public Vector2f getRotation() {
		return rotation;
	}

	public Image getImage() {
		return image;
	}

	public void rotateRight() {
		
		//Rota el objeto a la derecha.
		
		image.rotate(5);
		angle = image.getRotation();
		rotation.x = (float) Math.sin(Math.toRadians(angle));
		rotation.y = (float) Math.cos(Math.toRadians(angle));
		speed = 0;
	}

	public void rotateLeft() {
		
		//Rota el objeto a la izquierda.
		
		image.rotate(-5);
		angle = image.getRotation();
		rotation.x = (float) Math.sin(Math.toRadians(angle));
		rotation.y = (float) Math.cos(Math.toRadians(angle));
		speed = 0;
	}

	public void accelerate(float rate) {
		
		//Acelera

		speed += rate;
		velocity.x += rotation.x * speed;
		velocity.y += rotation.y * speed;

	}

	public void setPosition(Vector2f position) {
		
		//Mueve el objeto a una posicion
		//especificada.
		
		this.position = position;
		this.setSpeed(0);
		this.setVelocity(new Vector2f(0,0));
	}

	public float getRotationAngle() {
		return image.getRotation();
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setRotation(float angle) {
		
		//Rota el objeto a un angulo especifico.
		
		float oldangle = image.getRotation();
		image.setRotation(angle);
		rotation.x = (float) Math.sin(Math.toRadians(angle));
		rotation.y = (float) Math.cos(Math.toRadians(angle));
		new Transform();
		shape = shape.transform(Transform.createRotateTransform((float) Math
				.toRadians(angle - oldangle)));
		speed = 0;
	}

	public void updatePosition(int delta) throws SlickException {
		
		//Metodo que actualiza la posicion
		//del objeto en cada instante del
		//juego.

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

	public void render() {

		image.drawCentered(position.x, position.y);
		//ShapeRenderer.draw(shape);

	}

	public Vector2f getShapeCenter() {
		return new Vector2f(shape.getCenterX(), shape.getCenterY());
	}

	public boolean isCollidingWith(Shape shape) {
		
		//Detecta colisiones entre objetos,
		//a partir de sus figuras asociadas.
		//Si se intersectan, retorna true.

		if (this.shape.intersects(shape) || this.shape.contains(shape)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void mark4Removal(){
		dead = true;
	}
	
	public boolean marked4Removal(){
		return dead;
	}
	
	public void setVelocity(Vector2f velocity){
		this.velocity = velocity.copy();
	}
}