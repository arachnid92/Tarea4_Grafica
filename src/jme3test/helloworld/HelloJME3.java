package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

/**
 * A 3rd-person camera node follows a target (teapot).  Follow the teapot with
 * WASD keys, rotate by dragging the mouse.
 */
public class HelloJME3 extends SimpleApplication{

  private String mensaje;
  private Cola entrada;
  private Cola salida;
  private Servidor Server;   
  private float hor =0, ver=0;
  private RigidBodyControl  ball_phy;  
  private Nave nave;    
  private Geometry floor_geo;  
  private BulletAppState bulletAppState;
  private RigidBodyControl    floor_phy;
  private static Box floor;
  private Material floor_mat;
  private Material matBala;

  public static void main(String[] args) {
    HelloJME3 app = new HelloJME3();
    
    AppSettings s = new AppSettings(true);
    s.setFrameRate(100);
    app.setSettings(s);
    app.start();
    

  }
  static{
    floor = new Box(Vector3f.ZERO, 10f, 0.1f, 5f);
    floor.scaleTextureCoordinates(new Vector2f(3, 6));
  }
  
  public void simpleInitApp() {
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    
    Geometry geom = (Geometry) assetManager.loadModel("Models/Teapot/Teapot.obj");
    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    
    nave = new Nave(geom, material);
    nave.Translate(2.0f, 0, 0.0f);
    rootNode.attachChild(nave.getNode());
    
    cam.setLocation(new Vector3f(0f, 10f, -10f));
    cam.lookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);

    //disable the default 1st-person flyCam (don't forget this!!)
    flyCam.setEnabled(false);
    viewPort.setBackgroundColor(new ColorRGBA(0.05f, 0.0f, 1f, 1f));

    initMaterials();
    initFloor();
    
    entrada = new Cola();
    salida = new Cola();
    Server = new Servidor(entrada, salida);
    Thread t = new Thread(Server);
    t.start();
    
    matBala= new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    
  }
  
   public void initMaterials() {
 
    floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
    key3.setGenerateMips(true);
    Texture tex3 = assetManager.loadTexture(key3);
    tex3.setWrap(WrapMode.Repeat);
    floor_mat.setTexture("ColorMap", tex3);
  }
   public void initFloor() {
    floor = new Box(Vector3f.ZERO, 10f, 0.1f, 150f);
   // floor.scaleTextureCoordinates(new Vector2f(3, 6));
    floor_geo = new Geometry("Floor", floor);
    floor_geo.setMaterial(floor_mat);
    floor_geo.setLocalTranslation(0, -0.1f, 0);
    this.rootNode.attachChild(floor_geo);
    /* Make the floor physical with mass 0.0f! */
    floor_phy = new RigidBodyControl(0.0f);
    floor_geo.addControl(floor_phy);
    bulletAppState.getPhysicsSpace().add(floor_phy);

  }
   

   @Override
    public void simpleUpdate(float tpf) {
       
        floor_geo.move(Vector3f.UNIT_Z.mult(-0.02f));

	//direction.set(cam.getDirection()).normalizeLocal();
	   
        nave.Translate(hor, 0f, ver);
       
        
        
        // make the player rotate
        while ((mensaje=entrada.dequeue())!=null){
            if(Jugador(1,mensaje)){
		if(isStatus(mensaje)){
                    String[] split = mensaje.split("/");
                    if (split[2].equals("1")){
			System.out.println("Jugador 1 conectado");
                    }
		    else if(split[2].equals("0")){
			System.out.println("Jugador 1 desconectado");
			Server.descontarJugador();
                    }
                    else if(split[2].equals("2")){
			//System.out.println("bum");
                        //makeBullet(nave.getPos());
                        Bala bala = new Bala(bulletAppState, nave.getPos(), matBala, rootNode, 1);
                        bala.setVelocity(10);
                    }
                }
		else{
                    double Y = getY(mensaje);
                    double X = getX(mensaje);
                    //nave.Translate((float) (-Y), 0f, (float) (-X));
                    if (X>2 && X>Y){
                        ver-=0.3;
                    }
                    else if(X<-2 && X<Y){
                        ver+=0.3;
                    }
                    else if(Y<-2 && X>Y){
                        hor+=0.3;
                    }
                    else if(Y>2 && X<Y){
                        hor-=0.3;
                    }                    
                    
                }
            }
            else if(Jugador(2,mensaje)){
		double Y = getY(mensaje);
		double X = getX(mensaje);
            }
        }
    }
    
    public boolean Jugador(int jugador, String mensaje){
	String[] split = mensaje.split("/");
	if(split[0].equals("J"+jugador)){
		return true;
	}
	return false;
    }
    
    public boolean isStatus(String mensaje){
	String[] split = mensaje.split("/");
	if(split[1].equals("E")){
		return true;
	}
	return false;
    }
    public double getY(String mensaje){
	String[] split = mensaje.split("/");
	return Double.parseDouble(split[2]);		
    }
    public double getX(String mensaje){
	String[] split = mensaje.split("/");
	return Double.parseDouble(split[1]);		
    }
    
   
}
