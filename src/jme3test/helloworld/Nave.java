/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
/**
 *
 * @author Agustin Antoine
 */
public class Nave {
    private Geometry nave;
    private Node node;
    //private RigidBodyControl teaGeom_phy;
    
    public Nave(Geometry nave, Material mat){
        this.nave = nave;
        nave.setMaterial(mat);
        node = new Node("teaNode");
        node.attachChild(this.nave);
    }
    
    public void Translate(float X, float Y, float Z){
        this.nave.setLocalTranslation(X, Y, Z);
    }
    
    public Node getNode(){
        return this.node;
    }
    
}
