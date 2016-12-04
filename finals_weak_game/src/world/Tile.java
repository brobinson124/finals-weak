package world;

public class Tile {
	public static Tile tiles[] = new Tile[255];
	public static byte not = 0;
	
	public static final Tile test_tile = new Tile( "carpet");
	public static final Tile test2 = new Tile("stoneTex").setSolid();
	public static final Tile circleTable = new Tile("circleTable").setSolid();
	public static final Tile squareTable = new Tile("squareTable").setSolid();
	public static final Tile coffee = new Tile("coffee");
	public static final Tile duck = new Tile("duck").setSolid();
	public static final Tile duck2 = new Tile("duck2").setSolid();
	public static final Tile jesus = new Tile("jesus").setSolid();
	public static final Tile brooke = new Tile("brooke").setSolid();
	public static final Tile dylan = new Tile("dylan").setSolid();
	public static final Tile michael = new Tile("michael").setSolid();



	
	
	private byte id;
	private boolean solid;
	private String texture;
	
	public Tile(String texture){
		this.id = not;
		not++;
		this.texture = texture;
		this.solid = false;
		if(tiles[id] != null){
			throw new IllegalStateException("Tiles at ["+id+"] is already being used.");
		}
		tiles[id] = this;
	}
	
	public Tile setSolid() { this.solid=true; return this;}
	public boolean isSolid() { return solid;}
	
	public byte getId() {
		return id;
	}
	
	public String getTexture(){
		return texture;
	}
	
}
