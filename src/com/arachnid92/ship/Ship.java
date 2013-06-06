package com.arachnid92.ship;

import org.newdawn.slick.*;

import java.util.*;

import org.newdawn.slick.geom.*;

public class Ship extends SpaceObject {
	
	/*
	 * Objeto que representa una nave espacial,
	 * con todas sus propiedades.
	 * 
	 * Esta clase se encarga de dibujar la nave,
	 * actualizar su posicion en cada iteracion,
	 * su velocidad, y ademas se encarga de agrupar
	 * sus balas cuando dispara.
	 */

	private Image	            fire;
	private int	              firecounter;
	private int	              nbullet;
	private ArrayList<Bullet>	bulletlist;
	private Image	            ibullet;
	private Image	            ishield;
	private Circle	          shield;
	private int	              shieldtime;
	private int	              integrity;
	private int								fuel;
	private boolean						invunerable;
	private boolean						multishot;
	private int								invcounter;
	private int								mshotcounter;
	private int								blinkcounter;
	private Sound							laser;
	private Sound							shieldhit;
	private Sound							hit;
	private Sound							powerup;

	public Ship(String name, Vector2f position, Vector2f velocity, Image image,
			Image ibullet) throws SlickException {
		/*
		 * Metodo de inicializacion.
		 */
		super(name, position, velocity, image);
		fire = new Image("resources/player1_fire.png"); //Imagen para cuando la nave acelere.
		ishield = new Image("resources/shield.png"); //Imagen para representar el "escudo".
		
		//A continuacion se crea el poligono que engloba la parte trasera de la nave. 
		float[] points = { position.x + image.getWidth() / 2, position.y + 25,
				position.x - 6, position.y + image.getHeight() - 12,
				position.x + 6 + image.getWidth(), position.y + image.getHeight() - 12 }; 
		shape = new Polygon(points);
		
		this.ibullet = ibullet; //Imagen para las balas.
		
		//Inicializamos el circulo que engloba la parte delantera de la nave.
		this.shield = new Circle(position.x + image.getWidth() / 2, position.y + 6,
				30);
		
		this.integrity = 10; //Vidas de la nave.
		this.fuel = 1000; //Combustible.
		this.shieldtime = 0; //Contador para mostrar el escudo.
		this.nbullet = 0; //Contador de balas.
		this.invcounter = 0; //Contador para la "invulnerabilidad".
		this.blinkcounter = 0; //Contador para crear un efecto de parpadeo.
		this.mshotcounter = 0; //Contador para el powerup "multishot".
		this.bulletlist	= new ArrayList<Bullet>(); //Lista de balas.
		this.invunerable = false; //Boolean para establecer invunerabilidad.
		this.multishot = false; //Boolean para establecer multishot.
		
		//Sonidos:
		this.laser = new Sound("resources/laser.wav");
		this.shieldhit = new Sound("resources/shieldhit.wav");
		this.hit = new Sound("resources/hit.wav");
		this.powerup = new Sound("resources/powerup.wav");
	}

	public void rotate(int degree) {
		/*
		 * Este metodo se encarga de rotar la nave en el angulo
		 * argumento. Rota todas las imagenes y figuras asociadas.
		 */
		image.rotate(degree);
		ishield.rotate(degree);
		angle = image.getRotation();
		rotation.x = (float) Math.sin(Math.toRadians(angle));
		rotation.y = (float) Math.cos(Math.toRadians(angle));
		new Transform();
		shape = shape.transform(Transform.createRotateTransform((float) Math
				.toRadians(degree)));
		speed = 0;
		float[] coords = shape.getPoints();
		shield.setCenterX(coords[0]);
		shield.setCenterY(coords[1]);
		fire.setRotation(angle);
	}

	@Override
	public void accelerate(float rate) {

		/*
		 * Acelera la nave a de acuerdo a la aceleracion argumento.
		 * Si alcanza una velocidad maxima (0.002), no acelera mas.
		 * Tampoco acelera si la nave no tiene combustible.
		 */
		
		if(fuel > 0){
			if(speed <= 0.002 || rate * speed < 0){
				speed += rate;
			}

			velocity.x += rotation.x * speed;
			velocity.y += rotation.y * speed;

			fuel--;

		}
	}

	public void renderFire() {

		/*
		 * Dibuja la nave cuando se encuentra acelerando.
		 * Ademas, si la nave fue "reseteada" recientemente,
		 * parpadea.
		 */
		
		if(invunerable){
			if(blinkcounter <= 10){
				image.drawCentered(position.x, position.y);
				fire.drawCentered(position.x, position.y);
				if (shieldtime > 0) {
					ishield.drawCentered(position.x, position.y);
					shieldtime -= 1;
				}
				blinkcounter++;
			} else if (blinkcounter < 20){
				blinkcounter++;
				if(blinkcounter == 20){
					blinkcounter = 0;
				}
			}
		} else {
			image.drawCentered(position.x, position.y);
			fire.drawCentered(position.x, position.y);
			if (shieldtime > 0) {
				ishield.drawCentered(position.x, position.y);
				shieldtime -= 1;
			}
		}
	}

	public void fire() {

		/*
		 * Crea balas con la misma direccion y sentido
		 * que la nave, y las agrega a la lista de balas
		 * para que sean dibujadas y consideradas.
		 * Ademas reproduce un sonido.
		 */
		
		if (firecounter == 0) {
			
			if(mshotcounter > 0){
				mshotcounter--;
				if(mshotcounter == 0){
					multishot = false;
				}
			}

			laser.play();
			firecounter += 1;

			Vector2f position = this.getPosition().copy();
			Vector2f velocity = this.getVelocity().copy();
			Vector2f rotation = this.getRotation().copy();

			velocity = velocity.add(rotation);
			velocity = velocity.normalise();
			velocity = velocity.scale(0.5f);

			Bullet bullet = new Bullet("bullet" + nbullet, position, velocity,
					ibullet);
			nbullet += 1;

			bulletlist.add(bullet);

		} else {
			firecounter += 1;
			if (firecounter == 15) {
				firecounter = 0;
			}
		}
	}

	public void multiFire() {
		
		/*
		 * Se encarga de disparar multiples balas
		 * en abanico. Para esto, gira la nave 
		 * multiples veces y dispara.
		 */
		
		this.resetFireCounter();
		this.rotate(10);
		this.fire();
		this.resetFireCounter();
		this.rotate(-10);
		this.rotate(-10);
		this.fire();
		this.resetFireCounter();
		this.rotate(10);
		this.fire();
	}
	
	@Override
	public void updatePosition(int delta) throws SlickException {

		/*
		 * Actualiza la posicion de la nave en cada
		 * instante del juego, a partir de su posicion
		 * actual y su velocidad.
		 * Ademas, si la nueva posicion de la nave se 
		 * encuentra mas alla de los limites del tablero,
		 * mueve la nave al otro extremo, creando un efecto
		 * de que el tablero es "curvo".
		 * Finalmente, actualiza los contadores asociados.
		 */
		
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

		float[] coords = shape.getPoints();
		shield.setCenterX(coords[0]);
		shield.setCenterY(coords[1]);

		if(invcounter > 0){
			invcounter--;
		} else if (invcounter == 0 && invunerable){
			invunerable = false;
		}

	}

	@Override
	public void render() {
		
		/*
		 * Dibuja la nave en la ventana.
		 * Si la nave recientemente fue dañada,
		 * la dibuja pestañeando.
		 */

		if(invunerable){
			if(blinkcounter <= 10){
				image.drawCentered(position.x, position.y);
				if (shieldtime > 0) {
					ishield.drawCentered(position.x, position.y);
					shieldtime -= 1;
				}
				blinkcounter++;
			} else if (blinkcounter < 20){
				blinkcounter++;
				if(blinkcounter == 20){
					blinkcounter = 0;
				}
			}
		} else {
			image.drawCentered(position.x, position.y);
			if (shieldtime > 0) {
				ishield.drawCentered(position.x, position.y);
				shieldtime -= 1;
			}
		}
	}

	public void shieldImpact() {
		
		//Registra un impacto en el "escudo".
		
		shieldhit.play();
		shieldtime = 30;
	}

	public void hullHit() {
		
		//Registra un impacto en la nave.
		
		hit.play();
		integrity--;
	}

	public int getIntegrity() {
		return integrity;
	}

	public void addIntegrity() {
		
		//Suma vidas a la nave.
		
		powerup.play();
		integrity += 5;
	}

	public void resetFuel() {
		
		//Reestablece el combustible.
		
		powerup.play();
		fuel = 1000;
	}

	public int getFuel() {
		return fuel;
	}

	public int getBulletNumber() {
		return nbullet;
	}

	public ArrayList<Bullet> getBulletList() {
		return bulletlist;
	}

	public Shape getShield() {
		return shield;
	}

	public void setInvunerable(int counter){
		
		//Activa el modo invulnerabilidad, por un
		//periodo de tiempo definido por "counter".
		
		this.invunerable = true;
		this.invcounter = counter;
	}

	public boolean isInvunerable(){
		return invunerable;
	}

	public void resetPowerUps(){
		
		//Devuelve la nave a su estado normal.
		
		mshotcounter = 0;
		multishot = false;
	}

	public void setMultiShot() {
		
		//Activa el modo multishot.
		
		powerup.play();
		multishot = true;
		mshotcounter = 500;
	}

	public boolean isMultiShot() {
		return multishot;
	}

	public void resetFireCounter() {
		firecounter = 0;
	}
	
}
