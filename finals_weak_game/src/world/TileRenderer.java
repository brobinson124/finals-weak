package world;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;


public class TileRenderer {
	private HashMap<String, Texture> tileTextures;
	
	private Model model;
	
	public TileRenderer() {
		tileTextures = new HashMap<String, Texture>();
		float[] vertices = new float[]{
				-1, 1, 0, //TL		0 
					1, 1, 0, //TR		1
					1, -1, 0, //BR 	2
					-1, -1, 0, //BL 	3
		};
		
		
		float[] texture = new float[]{
				0,0,
				1,0,
				1,1,
				0,1,
		};
		
		int[] indices = new int[] {
				0,1,2, //draw first triangle
				2,3,0
		};
		
		model = new Model(vertices, texture, indices);
		
		for (int i = 0; i < Tile.tiles.length; i++){
			if (Tile.tiles[i] != null){
				if (!tileTextures.containsKey(Tile.tiles[i].getTexture())){
					String tex = Tile.tiles[i].getTexture();
					tileTextures.put(tex, new Texture(tex +".png"));
				}
			}
		}
	}
	
	public void renderTile(byte id, int x, int y, Shader shader, Matrix4f world, Camera cam) {
		shader.bind();
		if (tileTextures.containsKey(Tile.tiles[id].getTexture())) 
			tileTextures.get(Tile.tiles[id].getTexture()).bind(0);
			
		Matrix4f tilePosition = new Matrix4f().translate(new Vector3f(x*2, y*2, 0));
		Matrix4f target = new Matrix4f();
		
		cam.getProjection().mul(world, target);
		target.mul(tilePosition);
		
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", target);
		model.render();
		
//		model.render

	}

}
