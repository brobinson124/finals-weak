package world;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import collision.AABB;
import io.Window;
import render.Camera;
import render.Shader;

public class World {
	private final int view = 24;
	private byte[] tiles; //it is type byte because that is how we render files | Brooke
	private int width;
	private int height;
	private int scale; 
	
	private AABB[] bounding_boxes;
	
	private Matrix4f world;
	
	public World() {
		width = 64;
		height = 64;
		scale = 32; 
		
		tiles = new byte[width * height];
		
		bounding_boxes = new AABB[width*height];
		
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale); //we want the tiles to be 32 X 32 | Brooke
	}
	
	public void render(TileRenderer render, Shader shader, Camera cam, Window window){
//		for(int i = 0; i < height; i++){
//			for(int j = 0; j < width; j++){
//				render.renderTile(tiles[j+i * width], j, -i, shader, world, cam);
//			}
//		}
		
		int posX = ((int)cam.getPosition().x + (window.getWidth()/2)) / (scale * 2);//center of screen w/ offset of world
		int posY = ((int)cam.getPosition().y - (window.getHeight()/2)) / (scale * 2);//center of screen w/ offset of world

		for(int i = 0; i < view; i++){
			for(int j = 0; j <view; j++){
				Tile t = getTile(i-posX, j+posY);
				if(t != null)
					render.renderTile(t, i - posX, -j - posY, shader, world, cam);
			}
		}
	}
	
	public void correctCamera(Camera camera, Window win){
		Vector3f pos = camera.getPosition();
		
		int w = -width * scale * 2; //we take the width of the level and multiple to get us actual scale of box
		int h = height * scale * 2; //And this gives us the exact scale of our world | Brooke
	
		//dependant on camera projection matrix
		//we have the origin of camera set to middle of screen
		//if we were to change that, we would have a different algo
		
		if(pos.x > -(win.getWidth()/2)+scale)
			pos.x = -(win.getWidth()/2)+scale; //left
		if(pos.x < w + (win.getWidth()/2) + scale); //the scale prevents the view from stopping at half the tile
			pos.x = w + (win.getWidth()/2) + scale;
		if(pos.y < (win.getHeight()/2)-scale)
			pos.y = (win.getHeight()/2)-scale;
		if(pos.y > h - (win.getHeight()/2) - scale)
			pos.y = h- (win.getHeight()/2) -scale;
		
	}
	
	public void setTile(Tile tile, int x, int y) {
		tiles[x + y * width] = tile.getID();
		if(tile.isSolid()) {
			bounding_boxes[x + y * width] = new AABB(new Vector2f(x*2, -y*2), new Vector2f(1,1));
		}else{
			bounding_boxes[x + y * width] = null;
		}
		
	}

	public Tile getTile(int x, int y){
		try { //we are likely to get array error
			return Tile.tiles[tiles[x+y * width]]; 
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		
	}
	
	public AABB getTileBoundingBox(int x, int y){
		try { //we are likely to get array error
			return bounding_boxes[x + y * width]; 
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		
	}
	
	public int getScale() {
		return scale; 
	}
	

}
