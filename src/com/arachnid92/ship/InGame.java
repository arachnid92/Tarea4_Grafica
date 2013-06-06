package com.arachnid92.ship;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.arachnid92.ship.ShipGame.GameStates;

public class InGame extends BasicGameState{
	
	/*
	 * Estado principal del juego.
	 * 
	 * Inicializa todos los objetos (naves, asteroides,
	 * etc), actualiza el estado del juego en cada 
	 * instante y dibuja todo en pantalla.
	 */

	private ObjectHandler	   handler;
	private Vector2f startingpos1;
	private Vector2f startingpos2;
	private Vector2f resetposition;
	private Image life;
	private Image	fuel;
	private boolean pause = false;
	private Music loop;

	@Override
	public void render(GameContainer game, StateBasedGame sbg, Graphics graphics)
			throws SlickException {
		
		/*
		 * Metodo encargado de las operaciones de dibujo en
		 * pantalla del juego. Aqui van todas las operaciones
		 * de representacion grafica.
		 */

		//Listas de objetos:
		ArrayList<Ship> players = handler.getPlayerList();
		ArrayList<Asteroid> asteroids = handler.getAsteroidList();
		ArrayList<FuelBottle> fuels = handler.getFuelList();
		ArrayList<Life> lives = handler.getLifeList();
		ArrayList<MultiShot> mshots = handler.getMShotList();
		ArrayList<Bullet> bulletlist1 = players.get(0).getBulletList();
		ArrayList<Bullet> bulletlist2 = players.get(1).getBulletList();

		for (FuelBottle fuel: fuels){ //Dibuja los contenedores de combustible.
			fuel.render();
		}

		for (Life life: lives){ //Dibuja las vidas.
			life.render();
		}

		for (MultiShot mshot: mshots){ //Dibuja los MultiShot.
			mshot.render();
		}

		for (Asteroid asteroid : asteroids) { //Dibuja los asteroides.
			asteroid.render();
		}

		for (Bullet bullet : bulletlist1) { //Dibuja las balas del jugador 1.
			bullet.render();
		}

		for (Bullet bullet : bulletlist2) { //Dibuja las balas del jugador 2.
			bullet.render();
		}

		Input input = game.getInput();
		// Dibuja el jugador 1.
		// Si esta acelerando, dibuja llamas detras de la nave.
		if (input.isKeyDown(Input.KEY_UP) && players.get(0).getFuel() > 0) {
			players.get(0).renderFire();
		} else {
			players.get(0).render();
		}

		// Dibuja el jugador 2.
		// Si esta acelerando, dibuja llamas detras de la nave.
		if (input.isKeyDown(Input.KEY_W) && players.get(1).getFuel() > 0) {
			players.get(1).renderFire();
		} else {
			players.get(1).render();
		}

		//A continuacion se dibujan los contadores de vidas y de combustible.
		int p1vidas = players.get(0).getIntegrity();
		int p2vidas = players.get(1).getIntegrity();
		int p1fuel = players.get(0).getFuel();
		int p2fuel = players.get(1).getFuel();
		life.drawCentered(10, 10);
		fuel.drawCentered(10, 35);
		graphics.drawString("X " + p2vidas, 30, 0);
		graphics.drawString("X " + p2fuel, 30, 30);
		life.drawCentered(785, 10);
		fuel.drawCentered(785, 35);
		graphics.drawString(p1vidas + " X", 730, 0);
		graphics.drawString(p1fuel + " X", 720, 30);
	}

	public void enter(GameContainer game, StateBasedGame sbg) throws SlickException{

		/*
		 * Este metodo es invocado cuando el juego entra a este estado
		 * desde otro estado.
		 */
		
		if(!pause){ //Si el juego esta pausado, debe seguir corriendo como antes.
								//Si no, reasigna todas las variables.
			startingpos1 = new Vector2f(600, 500);
			startingpos2 = new Vector2f(200, 500);
			resetposition = new Vector2f(400, 300);

			life = new Image("resources/lifecounter.png");
			fuel = new Image("resources/fuel.png");

			life = life.getScaledCopy(0.5f);
			fuel = fuel.getScaledCopy(0.7f);

			handler = new ObjectHandler();
			handler.spawnPlayer(1, startingpos1.copy());
			handler.spawnPlayer(2, startingpos2.copy());
			handler.spawnAsteroid(10);
			
			ArrayList<Ship> players = handler.getPlayerList();
			players.get(0).setInvunerable(200);
			players.get(1).setInvunerable(200);
			
			loop = new Music("resources/ingame_loop.wav");
			loop.loop(1, 0.5f);
			
		}
		
	}

	@Override
	public void init(GameContainer game, StateBasedGame sbg) throws SlickException {

		/*
		 * Metodo invocado al entrar por primera vez a este estado.
		 * Aqui se asignan todas las variables.
		 */
		
		//Posiciones iniciales:
		startingpos1 = new Vector2f(600, 500);
		startingpos2 = new Vector2f(200, 500);
		resetposition = new Vector2f(400, 300);

		//Imagenes para los contadores de vidas y combustible:
		life = new Image("resources/lifecounter.png");
		fuel = new Image("resources/fuel.png");
		life = life.getScaledCopy(0.5f);
		fuel = fuel.getScaledCopy(0.7f);

		//Inicializamos el administrador de objetos.
		handler = new ObjectHandler();
		handler.spawnPlayer(1, startingpos1.copy());
		handler.spawnPlayer(2, startingpos2.copy());
		handler.spawnAsteroid(10);

		ArrayList<Ship> players = handler.getPlayerList();
		players.get(0).setInvunerable(200);
		players.get(1).setInvunerable(200);
	}

	@Override
	public void update(GameContainer game, StateBasedGame sbg, int delta) throws SlickException {

		/*
		 * Este metodo es el encargado de calcular el nuevo estado
		 * del juego en cada instante de su ejecucion. Aqui esta toda
		 * la logica del juego.
		 */
		
		//Llamamos al administrador de objetos para que cree
		//PowerUps.
		handler.spawnFuel();
		handler.spawnLife();
		handler.spawnMultiShot();

		//Listas de objetos:
		ArrayList<Ship> players = handler.getPlayerList();
		ArrayList<Asteroid> asteroids = handler.getAsteroidList();
		ArrayList<FuelBottle> fuels = handler.getFuelList();
		ArrayList<Life> lives = handler.getLifeList();
		ArrayList<MultiShot> mshots = handler.getMShotList();
		ArrayList<Bullet> bulletlist1 = players.get(0).getBulletList();
		ArrayList<Bullet> bulletlist2 = players.get(1).getBulletList();

		//Verificamos las condiciones de victoria:
		if(players.get(1).getIntegrity() <= 0 && players.get(0).getIntegrity() <= 0){
			sbg.enterState(GameStates.TIE.ordinal()); //En caso de empate.
		} else	if(players.get(1).getIntegrity() <= 0){
			sbg.enterState(GameStates.P1WIN.ordinal()); //Gana el jugador 1.
		} else if (players.get(0).getIntegrity() <= 0){
			sbg.enterState(GameStates.P2WIN.ordinal()); //Gana el jugador 2.
		}

		//Si hay muy pocos asteroides, creamos mas:
		if(asteroids.size() < 3){
			handler.spawnAsteroid(5);
		} 
		
		Input input = game.getInput();

		//Definimos una manera de pausar el juego:
		if (input.isKeyDown(Input.KEY_P)){
			pause = true;
			sbg.enterState(GameStates.PAUSE.ordinal());
		} else {
			pause = false;
		} 

		/* 
		 * A continuacion se definen las condiciones para
		 * mover las naves.
		 */
		
		//Jugador 1:
		if (input.isKeyDown(Input.KEY_UP)) {
			players.get(0).accelerate(.0001f);
		}

		if (input.isKeyDown(Input.KEY_DOWN)) {
			players.get(0).accelerate(-.0001f);
		}

		if (input.isKeyDown(Input.KEY_LEFT)) {
			players.get(0).rotate(-5);
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			players.get(0).rotate(5);
		}

		if (input.isKeyDown(Input.KEY_RCONTROL)) {
			if(players.get(0).isMultiShot()){
				players.get(0).multiFire();
			} else {
				players.get(0).fire();
			}
		}

		if (!input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN)) {
			players.get(0).setSpeed(0);
		}

		// Jugador 2
		if (input.isKeyDown(Input.KEY_W)) {
			players.get(1).accelerate(.0001f);
		}

		if (input.isKeyDown(Input.KEY_S)) {
			players.get(1).accelerate(-.0001f);
		}

		if (input.isKeyDown(Input.KEY_A)) {
			players.get(1).rotate(-5);
		}

		if (input.isKeyDown(Input.KEY_D)) {
			players.get(1).rotate(5);
		}

		if (input.isKeyDown(Input.KEY_LCONTROL)) {
			if(players.get(1).isMultiShot()){
				players.get(1).multiFire();
			} else {
				players.get(1).fire();
			}
		}

		if (!input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_S)) {
			players.get(1).setSpeed(0);
		}

		/*
		 * A continuacion se actualiza la posicion de los
		 * objetos del juego a partir de sus respectivas
		 * velocidades y los cambios hechos desde la ultima
		 * iteracion de este metodo.
		 */
		
		//Jugadores
		for (Ship player : players) {
			player.updatePosition(delta);
		}
		
		//Balas
		//Si el contador de la bala en cuestion es cero,
		//se marca para ser eliminada despues
		for (Bullet bullet: bulletlist1) {
			bullet.updatePosition(delta);
			if (bullet.getCounter() == 0) {
				bullet.mark4Removal();
			}
		}

		for (Bullet bullet: bulletlist2) {
			bullet.updatePosition(delta);
			if (bullet.getCounter() == 0) {
				bullet.mark4Removal();
			}
		}

		//Contenedores de combustible
		for (FuelBottle fuel: fuels){
			fuel.updatePosition(delta);
		}

		//Vidas
		for (Life life: lives){
			life.updatePosition(delta);
		}

		//Powerups
		for (MultiShot mshot: mshots){
			mshot.updatePosition(delta);
		}

		//Asteroides
		for (Asteroid asteroid: asteroids) {
			asteroid.updatePosition(delta);
			if (asteroid.getScale() < 0.5) {
				asteroid.mark4Removal();
			}
		}

		/*
		 * A continuacion se hacen llamados a metodos
		 * definidos mas abajo en esta misma clase,
		 * los cuales son los encargados de detectar
		 * colisiones entre objetos.
		 */
		
		//Colisiones entre vidas y naves
		this.checkLifeShipCollisions(players.get(0), lives);
		this.checkLifeShipCollisions(players.get(1), lives);
		
		//Colisiones entre balas y naves
		this.checkBulletShipCollisions(players.get(0), bulletlist2);
		this.checkBulletShipCollisions(players.get(1), bulletlist1);
		
		//Colisiones entre asteroides y balas
		this.checkBulletAsteroidCollisions(bulletlist1, asteroids);
		this.checkBulletAsteroidCollisions(bulletlist2, asteroids);
		
		//Colisiones entre contenedores de combustible y naves
		this.checkFuelShipCollisions(players.get(0), fuels);
		this.checkFuelShipCollisions(players.get(1), fuels);
		
		//Colisiones entre naves
		this.checkShipCollisions(players.get(0), players.get(1));
		
		//Colisiones entre asteroides y naves
		this.checkShipAsteroidCollisions(players.get(0), asteroids);
		this.checkShipAsteroidCollisions(players.get(1), asteroids);
		
		//Colisiones entre naves y powerups
		this.checkMShotShipCollisions(players.get(0), mshots);
		this.checkMShotShipCollisions(players.get(1), mshots);

		/*
		 * A continuacion se eliminan los objetos de cada lista
		 * que han sido marcados para ser eliminados.
		 */
		
		//Balas
		for(int i = 0; i < bulletlist1.size(); i++){
			if(bulletlist1.get(i).marked4Removal()){
				bulletlist1.remove(i);
			}
		}

		for(int i = 0; i < bulletlist2.size(); i++){
			if(bulletlist2.get(i).marked4Removal()){
				bulletlist2.remove(i);
			}
		}

		
		//Asteroides
		for(int i = 0; i < asteroids.size(); i++){
			if(asteroids.get(i).marked4Split()){
				handler.spawnAsteroid(2, asteroids.get(i).getPosition().copy(),
						asteroids.get(i).getScale() / 2);
				asteroids.remove(i);
			} else if (asteroids.get(i).marked4Removal()){
				asteroids.remove(i);
			}
		}

		//Combustible
		for(int i = 0; i < fuels.size(); i++){
			if(fuels.get(i).marked4Removal()){
				fuels.remove(i);
			}
		}

		//Vidas
		for(int i = 0; i < lives.size(); i++){
			if(lives.get(i).marked4Removal()){
				lives.remove(i);
			}
		}

		//PowerUps
		for(int i = 0; i < mshots.size(); i++){
			if(mshots.get(i).marked4Removal()){
				mshots.remove(i);
			}
		}
	}

	private void checkBulletAsteroidCollisions(ArrayList<Bullet> bulletlist,
			ArrayList<Asteroid> asteroidlist) {

		/*
		 * Verifica colisiones entre las balas de una lista,
		 * con los asteroides de otra lista.
		 * Si encuentra colisiones, marca las balas (y si
		 * corresponde, los asteroides) para su eliminacion.
		 */
		
		for (Bullet bullet: bulletlist) {
			Shape bulletshape = bullet.getShape();
			for (Asteroid asteroid: asteroidlist) {
				if (asteroid.isCollidingWith(bulletshape)) {
					bullet.mark4Removal();
					asteroid.decIntegrity();
					int integrity = asteroid.getIntegrity();
					if (integrity == 0) {
						asteroid.mark4Split();
					}
				}
			}
		}
	}

	private void checkBulletShipCollisions(Ship player, ArrayList<Bullet> bullets) {

		/*
		 * Verifica colisiones entre las balas de una lista,
		 * con una nave. Estas pueden ser de dos tipos:
		 * con la nave misma, o con su escudo.
		 * 
		 * Si la colision es con la nave, la nave pierde una vida.
		 * Si es con el escudo, no pasa nada con la nave, y 
		 * se dibuja el escudo en pantalla por unos instantes.
		 * 
		 * En ambos casos la bala es marcada para ser eliminada.
		 */
		
		if(!player.isInvunerable()){
			Shape shield = player.getShield();
			for (Bullet bullet: bullets) {
				Shape bulletshape = bullet.getShape();
				if (bulletshape.intersects(shield) || shield.contains(bulletshape)) {
					player.shieldImpact();
					bullet.mark4Removal();
				} else if(player.isCollidingWith(bulletshape)){
					player.hullHit();
					bullet.mark4Removal();
				}
			}
		}
	}

	private void checkFuelShipCollisions(Ship player, ArrayList<FuelBottle> fuels) {
		
		/*
		 * Verifica colisiones entre contenedores de combustible.
		 * De haberlos, se marca el contenedor para ser eliminado,
		 * y la nave recupera su combustible.
		 */
		
		for (FuelBottle fuel: fuels){
			if(player.isCollidingWith(fuel.getShape())){
				player.resetFuel();
				fuel.mark4Removal();
			}
		}
	}

	private void checkShipCollisions(Ship player1, Ship player2){
		
		/*
		 * Verifica colisiones entre dos naves.
		 * Si las naves coliden, vuelven a sus posiciones
		 * originales y son invulnerables por un periodo de
		 * tiempo. Ademas, pierden dos vidas cada uno.
		 */
		
		if(player1.isCollidingWith(player2.getShape())){
			//			player1.setVelocity(player1.getVelocity().scale(-0.5f));
			//			player2.setVelocity(player2.getVelocity().scale(-0.5f));
			if(!player1.isInvunerable() && !player2.isInvunerable()){
				player1.setPosition(startingpos1.copy());
				player2.setPosition(startingpos2.copy());	
				player1.hullHit();
				player1.hullHit();
				player2.hullHit();
				player2.hullHit();
				player1.setInvunerable(200);
				player2.setInvunerable(200);
			}
		}
	}

	private void checkShipAsteroidCollisions(Ship player, ArrayList<Asteroid> asteroids){
		
		/*
		 * Verifica colisiones entre una nave y asteroides.
		 * 
		 * Si la nave colisiona con un asteroide, es trasladada a 
		 * una posicion en el centro del tablero y se vuelve 
		 * invulnerable por un periodo de tiempo. Ademas, 
		 * tanto la nave como el asteroide pierden 2 vidas.
		 */
		
		if(!player.isInvunerable()){
			for(Asteroid asteroid: asteroids){
				if(player.isCollidingWith(asteroid.getShape())){
					player.hullHit();
					player.hullHit();
					player.setPosition(resetposition.copy());
					asteroid.decIntegrity();
					asteroid.decIntegrity();
					player.setInvunerable(200);
				}
			}
		}
	}

	private void checkLifeShipCollisions(Ship player, ArrayList<Life> lives){
		
		/*
		 * Verifica colisiones entre vidas y una nave.
		 * 
		 * Si las hay, la nave gana 5 vidas y la vida es marcada
		 * para su eliminacion.
		 */
		
		for(Life life: lives){
			if(player.isCollidingWith(life.getShape())){
				player.addIntegrity();
				life.mark4Removal();
			}
		}
	}

	private void checkMShotShipCollisions(Ship player, ArrayList<MultiShot> mshots){
		
		/*
		 * Verifica colisiones entre MultiShots y una nave.
		 * 
		 * Si las hay, la nave gana el powerup y este es marcado
		 * para su eliminacion.
		 */
		
		
		for(MultiShot mshot: mshots){
			if(player.isCollidingWith(mshot.getShape())){
				player.setMultiShot();
				mshot.mark4Removal();
			}
		}
	}

	@Override
	public int getID() {
		return GameStates.INGAME.ordinal();
	}

}
