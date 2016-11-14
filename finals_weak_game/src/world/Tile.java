package world;

public class Tile {
	public static Tile tiles[] = new Tile[16];
	
	public static byte num_tiles = 0;
	
	public static final Tile test_tile = new Tile("grass");
	public static final Tile test3 = new Tile("playertest");
	public static final Tile test2 = new Tile("player2");
	
	private byte id;
	private String texture;
	
	public Tile(String texture) {
		this.id = num_tiles;
		num_tiles++;
		this.texture = texture;
		if (tiles[id] != null)
			throw new IllegalStateException("Tiles at: ["+id+"] are already being used!");
		tiles[id] =  this;
	}

	public byte getID() {
		return id;
	}
	
	public String getTexture() {
		return texture;
	}

}
