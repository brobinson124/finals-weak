package world;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import collision.AABB;
import io.Window;
import render.Camera;
import render.Shader;

public class World {
	private final int view = 32;
	private byte[] tiles; //it is type byte because that is how we render files | Brooke
	private int width;
	private int height;
	private int scale; 
	private int viewX;
	private int viewY;
	
	private AABB[] bounding_boxes;
	
	private Matrix4f world;
	
	public World(String world) {
		//this constructor will take an image and make a 
		//level based off of it
		try {
			BufferedImage tile_sheet = ImageIO.read(new File("./levels/" + world + "_tiles.png"));
			//BufferedImage entity_sheet = ImageIO.read(new File("./levels/" + world + "_entity.png"));
			
			width = tile_sheet.getWidth();
			height = tile_sheet.getHeight();
			scale = 16;
			
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(scale);
			
			int[] colorTileSheet = tile_sheet.getRGB(0,0, width, height, null, 0, width);
			//will return all pixels in an image. 
			
			tiles = new byte[width * height];
			bounding_boxes = new AABB[width * height];
			
			for(int y = 0; y < height; y++){
				for(int x =0; x < 0; x++){
					int red = (colorTileSheet[x + y * width] >> 16) & 0XFF;
					
					Tile t = Tile.tiles[red]; //will cause array out of bounds error
					
					if (t != null){
						setTile(t, x, y);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public World() {
		width = 32;
		height = 32;
		scale = 16; 
		
		tiles = new byte[width * height];
		
		bounding_boxes = new AABB[width*height];
		
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale); //we want the tiles to be 32 X 32 | Brooke
	}
	
	public void calculateView(Window window) {
		viewX = (window.getWidth() / (scale * 2)) + 4;
		viewY = (window.getHeight() / (scale * 2)) + 4;
	}
	
	public void render(TileRenderer render, Shader shader, Camera cam, Window window){
//		for(int i = 0; i < height; i++){
//			for(int j = 0; j < width; j++){
//				render.renderTile(tiles[j+i * width], j, -i, shader, world, cam);
//			}
//		}
		
		//int posX = (int)cam.getPosition().x / (scale);//
		int posX = ((int)cam.getPosition().x + (window.getWidth()/2)) / (scale);//center of screen w/ offset of world
		//int posY = (int)cam.getPosition().y / (scale);//
		int posY = ((int)cam.getPosition().y - (window.getHeight()/2)) / (scale);//center of screen w/ offset of world
		
		//System.out.println("X: " + posX);
		//System.out.println("Y: " + posY);
		
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
		
		System.out.println("posX: " + pos.x);
		System.out.println("pos y: " + pos.y);
		
		int new_w = w + (win.getWidth()/2) + scale;
		
		System.out.println("width: " + new_w);
		
		if(pos.x > -(win.getWidth()/2)+scale){
			pos.x = -(win.getWidth()/2)+scale; //left
			System.out.println("new X: " + pos.x);
		}
		//if(pos.x < w + (win.getWidth()/2) + scale); //the scale prevents the view from stopping at half the tile
			//pos.x = w + (win.getWidth()/2) + scale;			
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
