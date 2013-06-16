package jme3test.helloworld;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

class Cliente implements Runnable {
	private Socket server;
	private String line,input;
	private ServerSocket listener;
	private int numero;
	private Cola entrada; //Todo lo que va al Server (Juego)
	private Cola salida;  //Todo lo que va al Cliente (Android)
	private boolean conectado;


	Cliente(Socket server, ServerSocket listener, int i, Cola entrada, Cola salida) {
		this.listener=listener;
		this.server=server;
		this.numero=i;
		this.entrada=entrada;
		this.salida=salida;
	}

	public void run () {
		entrada.enqueue("J"+numero+"/E/1");
		

		try {
			// Get input from the client
			DataInputStream in = new DataInputStream (server.getInputStream());
			PrintStream out = new PrintStream(server.getOutputStream());

			out.println("connect");
			conectado=true;
			while(conectado){
				String a;
				while ((a=salida.dequeue())!=null){	
					out.println(a);
					if(a.equals("log out")){
						conectado=false;
						System.out.println("log out");
					}
					
				}	
				while((line = in.readLine()) != null) {
					entrada.enqueue("J"+numero+"/"+line);	
					if(line.equals("E/0")){
						conectado=false;
					}
				}				
			}			
			
			// Avisar al Server de la desconeccion (Juego)
			//entrada.enqueue("J"+numero+"/E/0");

			// Desconectar al cliente (Android)
			//out.println("log out");
			//System.out.println("Se acabo la conversaci�n :( ");
			server.close();


		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
	}
}