package world;

import java.awt.Window;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import render.Camera;
import render.Shader;

public class World {
	private byte[] tiles; //it is type byte because that is how we render files | Brooke
	private int width;
	private int height;
	private int scale; 
	
	private Matrix4f world;
	
	public World() {
		width = 64;
		height = 64;
		scale = 16; 
		
		tiles = new byte[width * height];
		
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale); //we want the tiles to be 32 X 32 | Brooke
	}
	
	public void render(TileRenderer render, Shader shader, Camera cam){
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				render.renderTile(tiles[j+i * width], j, -i, shader, world, cam);
			}
		}
	}
	
	public void correctCamera(Camera camera, Window window){
		Vector3f pos = camera.getPosition();
		
		int w = -width * scale * 2; //we take the width of the level and multiple to get us actual scale of box
		int h = height * scale * 2; //And this gives us the exact scale of our world | Brooke
	
		if(pos.x > -(window.getWidth()/2)+scale)
			pos.x = -(window.getWidth()/2)+scale;
		
	}
	
	public void setTile(Tile tile, int x, int y) {
		tiles[x+y * width] = tile.getID();
		
	}
	

}
