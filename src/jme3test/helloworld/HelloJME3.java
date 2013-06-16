package jme3test.helloworld;
 
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.controls.*;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
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
    
    
  private BulletAppState bulletAppState;
  private Geometry teaGeom;
  private Geometry teaGeom2;
  private Node teaNode;
  private Node teaNode2;
  private RigidBodyControl    teaGeom_phy;
  private RigidBodyControl    teaGeom2_phy;
  CollisionShape teteraShape;
  private RigidBodyControl    floor_phy;
  private static final Box    floor;
   Material floor_mat;
  Vector3f direction = new Vector3f();

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

    // load a teapot model 
    teaGeom = (Geometry) assetManager.loadModel("Models/Teapot/Teapot.obj");
    Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    teaGeom.setMaterial(mat);
    teaGeom.getLocalRotation().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y); 
    teaGeom.setLocalTranslation(2.0f, 0, 0.0f);
    teaNode = new Node("teaNode");
    teaNode.attachChild(teaGeom);
    rootNode.attachChild(teaNode);
    teteraShape = CollisionShapeFactory.createDynamicMeshShape(teaGeom);
    teaGeom_phy = new RigidBodyControl(teteraShape, 0);
    teaGeom.addControl(teaGeom_phy);
    bulletAppState.getPhysicsSpace().add(teaGeom_phy);
    
    
    teaGeom2 = (Geometry) assetManager.loadModel("Models/Teapot/Teapot.obj");
    teaGeom2.setMaterial(mat);
    teaGeom2.getLocalRotation().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y); 
    teaNode2 = new Node("teaNode2");
    teaNode2.attachChild(teaGeom2);
    rootNode.attachChild(teaNode2);
    teaGeom2_phy = new RigidBodyControl(teteraShape, 0);
    teaGeom2.addControl(teaGeom2_phy);
    bulletAppState.getPhysicsSpace().add(teaGeom2_phy);
    
    
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
    Geometry floor_geo = new Geometry("Floor", floor);
    floor_geo.setMaterial(floor_mat);
    floor_geo.setLocalTranslation(0f, 0f, 0f);
    //floor_geo.getLocalRotation().fromAngleAxis(-FastMath.HALF_PI/2, Vector3f.UNIT_X); 
    this.rootNode.attachChild(floor_geo);
    /* Make the floor physical with mass 0.0f! */
    floor_phy = new RigidBodyControl(0.0f);
    floor_geo.addControl(floor_phy);
    bulletAppState.getPhysicsSpace().add(floor_phy);
  }
   @Override
    public void simpleUpdate(float tpf) {

	direction.set(cam.getDirection()).normalizeLocal();
	   

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
                }
		else{
                    double Y = getY(mensaje);
                    double X = getX(mensaje);
                    teaGeom.setLocalTranslation((float) (-Y), 0f, (float) (-X));
                    
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
	return Double.parseDouble(split[2].substring(2));		
    }
    public double getX(String mensaje){
	String[] split = mensaje.split("/");
	return Double.parseDouble(split[1].substring(2));		
    }

 
}
