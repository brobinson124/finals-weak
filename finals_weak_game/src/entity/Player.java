package entity;

import org.joml.Vector3f;

import io.Window;
import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;
import world.World;

public class Player {
	private Model model;
	private Texture texture;
	private Transform transform;
	
	public Player() {
		
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
		this.texture = new Texture("playertest.png");
		
		transform = new Transform();
		transform.scale = new Vector3f(16,16,1); //third arguemnt for 3d, 0 doesn't work | Brooke
	}
	
	public void update(float delta, Window window, Camera camera, World world){
		
	}
	
	public void render(Shader shader, Camera camera) {
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(camera.getProjection()));
		texture.bind(0);
		model.render();
	}

}
