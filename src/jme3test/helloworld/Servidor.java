package jme3test.helloworld;
import java.io.*;
import java.net.*;




public class Servidor implements Runnable{

	private static int port=12345, maxConnections=3;
	public static int actualConnections = 0;
	private Cola entrada;
	private Cola salida;


	// Listen for incoming connections and handle them

	public Servidor(Cola entrada, Cola salida){
		this.entrada = entrada;
		this.salida = salida;
	}


	public void descontarJugador(){
		actualConnections--;
	}



	@Override
	public void run() {
		try{
			ServerSocket listener = new ServerSocket(port);
			//System.out.println(getLocalIpAddress());
			Socket server;
			System.out.println("Server inicializado");

			while((actualConnections++ < maxConnections) || (maxConnections == 0)){
				server = listener.accept();
				Cliente conn_c= new Cliente(server, listener, actualConnections, entrada, salida);
				Thread t = new Thread(conn_c);
				t.start();
			}
		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}

	}

}

