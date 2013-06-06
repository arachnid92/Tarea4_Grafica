package com.arachnid92.ship;

import java.util.*;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

public class ObjectHandler {

	/*
	 * Administrador de objetos.
	 * Este objeto se encarga de administrar
	 * otros objetos, generando jugadores,
	 * powerups y asteroides, y administrando
	 * las listas respectivas.
	 */
	
	//Listas para objetos:
	protected ArrayList<Asteroid>	  asteroids; 
	protected ArrayList<Ship>	      ships;
	protected ArrayList<FuelBottle>	bottles;
	protected ArrayList<Life>				lives;
	protected ArrayList<MultiShot>	mshots;
	
	//Imagenes:
	protected Image	                ship1;
	protected Image	                ship2;
	protected Image	                asteroid1;
	protected Image	                ibullet1;
	protected Image									ibullet2;
	protected Image	                ifuel;
	protected Image									heart;
	protected Image									mshot;
	
	//Generador aleatorio:
	protected Random	              random;
	
	//Contadores
	protected int	                  acounter;
	protected int										fuelcounter;
	protected int										lifecounter;
	protected int										mshotcounter;

	public ObjectHandler() throws SlickException {

		//Cargamos las imagenes a la memoria:
		ship1 = new Image("resources/player1.png");
		ship2 = new Image("resources/player2.png");
		asteroid1 = new Image("resources/asteroid1.png");
		ibullet1 = new Image("resources/bullet1.png");
		ibullet2 = new Image("resources/bullet2.png");
		ifuel = new Image("resources/fuel.png");
		heart = new Image("resources/life.png");
		mshot = new Image("resources/multishot.png");

		//Inicializamos las listas:
		asteroids	= new ArrayList<Asteroid>();
		ships	    = new ArrayList<Ship>();
		bottles	  = new ArrayList<FuelBottle>();
		lives 		= new ArrayList<Life>();
		mshots 		= new ArrayList<MultiShot>();

		random = new Random();
		acounter = 0;
		fuelcounter = 0;
		lifecounter = 0;
		mshotcounter = 0;

	}

	public void spawnPlayer(int playern, Vector2f position) throws SlickException {

		//Metodo para crear un jugador.
		//Las imagenes asociadas a cada jugador
		//dependen de su numero.
		if (playern == 1) {
			ships.add(new Ship("P" + playern, position, new Vector2f(0, 0), ship1,
					ibullet1));
		} else {
			ships.add(new Ship("P" + playern, position, new Vector2f(0, 0), ship2,
					ibullet2));
		}
	}

	public void spawnAsteroid(int n) throws SlickException {

		//Metodo que genera "n" asteroides en
		//lugares aleatorios en el mapa.
		
		for (int i = 0; i < n; i++) {

			int a = 1;
			int b = 1;

			if (random.nextFloat() >= 0.5f) {
				a = -1;
			}
			if (random.nextFloat() >= 0.5f) {
				b = -1;
			}

			float scale = random.nextFloat() * 3;
			float distance = (asteroid1.getWidth() + asteroid1.getHeight()) * scale
					/ 2;
			Vector2f pos = new Vector2f(random.nextInt(800), random.nextInt(600));

			if (ships.get(0).position.distance(pos) > distance + 20
					&& ships.get(1).position.distance(pos) > distance + 20) {
				//Esta condicion es para que no aparezcan
				//asteroides muy proximos a algun jugador.
				asteroids.add(new Asteroid("A" + acounter, pos, new Vector2f(random
						.nextFloat() * a * 0.01f, random.nextFloat() * b * 0.01f),
						asteroid1, scale));
				acounter++;
			}

		}
	}

	public void spawnAsteroid(int n, Vector2f position, float scale) throws SlickException {

		//Genera n asteroides en un lugar especifico
		//de un tamaño especifico.
		
		for (int i = 0; i < n; i++) {

			int a = 1;
			int b = 1;

			if (random.nextFloat() >= 0.5f) {
				a = -1;
			}
			if (random.nextFloat() >= 0.5f) {
				b = -1;
			}

			asteroids.add(new Asteroid("A" + acounter, position, new Vector2f(random
					.nextFloat() * a * 0.01f, random.nextFloat() * b * 0.01f), asteroid1,
					scale));
			
			acounter++;

		}
	}

	public void spawnFuel() {
		
		//Genera powerups de combustible.
		//Su posicion y movimiento son generados
		//aleatoriamente.
		
		int i = random.nextInt(1000); 
		if (i == 1) {
			
			//En cada instante del juego existe una 
			//posibilidad de 1/1000 que aparezca un
			//powerup de combustible.
			
			int a = 1;
			int b = 1;

			if (random.nextFloat() >= 0.5f) {
				a = -1;
			}
			if (random.nextFloat() >= 0.5f) {
				b = -1;
			}

			Vector2f pos = new Vector2f(random.nextInt(800), random.nextInt(600));

			bottles.add(new FuelBottle("Fuel" + fuelcounter, pos, new Vector2f(random
					.nextFloat() * a * 0.01f, random.nextFloat() * b * 0.01f),
					ifuel));
			fuelcounter++;
		}
	}

	public void spawnLife() {
		
		//Genera powerups de vida.
		//Su posicion y movimiento son generados
		//aleatoriamente.
			
		int i = random.nextInt(1000);
		if (i == 1) {
			
			//En cada instante del juego existe una 
			//posibilidad de 1/1000 que aparezca un
			//powerup de vida.
			
			int a = 1;
			int b = 1;

			if (random.nextFloat() >= 0.5f) {
				a = -1;
			}
			if (random.nextFloat() >= 0.5f) {
				b = -1;
			}

			Vector2f pos = new Vector2f(random.nextInt(800), random.nextInt(600));

			lives.add(new Life("Life" + lifecounter, pos, new Vector2f(random
					.nextFloat() * a * 0.01f, random.nextFloat() * b * 0.01f),
					heart));
			lifecounter++;
		}
	}
	
	public void spawnMultiShot() {
		
		//Genera powerups de MultiShot.
		//Su posicion y movimiento son generados
		//aleatoriamente.
		
		int i = random.nextInt(1500);
		if (i == 1) {
			
			//En cada instante del juego existe una 
			//posibilidad de 1/1500 que aparezca un
			//powerup de MultiShot.
			
			int a = 1;
			int b = 1;

			if (random.nextFloat() >= 0.5f) {
				a = -1;
			}
			if (random.nextFloat() >= 0.5f) {
				b = -1;
			}

			Vector2f pos = new Vector2f(random.nextInt(800), random.nextInt(600));

			mshots.add(new MultiShot("MShot" + mshotcounter, pos, new Vector2f(random
					.nextFloat() * a * 0.01f, random.nextFloat() * b * 0.01f),
					mshot));
			mshotcounter++;
		}
		
	}
	
	public ArrayList<Ship> getPlayerList() {
		return ships;
	}

	public ArrayList<Asteroid> getAsteroidList() {
		return asteroids;
	}
	
	public ArrayList<FuelBottle> getFuelList() {
		return bottles;
	}

	public ArrayList<Life> getLifeList() {
		return lives;
	}
	
	public ArrayList<MultiShot> getMShotList() {
		return mshots;
	}
}
