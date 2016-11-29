package world;

public class Tile {
	public static Tile tiles[] = new Tile[255];
	
	public static byte num_tiles = 0;
	
	public static final Tile test1 = new Tile("grass");
	public static final Tile test3 = new Tile("playertest");
	public static final Tile test2 = new Tile("player2").setSolid(); //collision
	
	private byte id;
	private String texture;
	
	private boolean solid; //for collision 
	
	public Tile(String texture) {
		this.id = num_tiles;
		num_tiles++;
		this.texture = texture;
		this.solid = false; //for collision
		if (tiles[id] != null)
			throw new IllegalStateException("Tiles at: ["+id+"] are already being used!");
		tiles[id] =  this;
	}

	public Tile setSolid(){
		this.solid = true;
		return this;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public byte getID() {
		return id;
	}
	
	public String getTexture() {
		return texture;
	}

}
