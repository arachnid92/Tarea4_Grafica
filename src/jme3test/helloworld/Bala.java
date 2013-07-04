/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Agustin Antoine
 */
public class Bala {
    
    private Geometry bala;
    private Node node;
    private Sphere sphere;
    private int Jugador;
    private float radio;
    private RigidBodyControl  ball_phy;
    private BulletAppState bulletAppState;
    
    public Bala(BulletAppState B,Vector3f pos, Material mat, Node root, int Jugador){     
        this.Jugador = Jugador;
        radio=0.1f;
        this.sphere = new Sphere(30,30,radio);
        bala = new Geometry("cannon ball", sphere);
        bala.setMaterial(mat);
        root.attachChild(bala);
        bala.setLocalTranslation(pos);
        
        bulletAppState=B;
        
        ball_phy = new RigidBodyControl(1f);
    
        bala.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        
    }
    public void setVelocity(int v){
        ball_phy.setLinearVelocity(Vector3f.UNIT_Z.mult(v));
    }
    public int getJugador(){
        return Jugador;
    }
    
}
