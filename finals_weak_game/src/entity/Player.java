package entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import collision.AABB;
import collision.Collision;
import io.Window;
import render.Animation;
import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;
import world.World;

public class Player {
	private Model model;
	private AABB bounding_box;
	//private Texture texture;
	private Animation texture;
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
		this.texture = new Animation(5, 5, "deer");
		
		//this.texture = new Texture("playertest.png");
		
		transform = new Transform();
		transform.scale = new Vector3f(16,16,1); //third arguemnt for 3d, 0 doesn't work | Brooke
	
		bounding_box = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(1,1));
	}
	
	public void update(float delta, Window window, Camera camera, World world){
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_A)) {
			transform.pos.add(new Vector3f(-10*delta, 0, 0));
		}
		
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_D)) {
			transform.pos.add(new Vector3f(10*delta, 0, 0));
		}
		
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_W)) {
			transform.pos.add(new Vector3f(0, 10*delta, 0));
		}
		
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_S)) {
			transform.pos.add(new Vector3f(0, -10*delta, 0));
		}
		
		bounding_box.getCenter().set(transform.pos.x, transform.pos.y);
		
		AABB[] boxes = new AABB[25];
		for(int i = 0; i < 5; i++){
			for(int j = 0; j< 5; j++){
				boxes[i + j * 5] = world.getTileBoundingBox(
							(int)(((transform.pos.x / 2) + 0.5f) - (5/2)) + i,
							(int)(((-transform.pos.y / 2) + 0.5f) - (5/2)) + j
						); //everything around player within a dist of 5
			}
		}
		
		AABB box = null;
		for(int i = 0; i < boxes.length; i++){
			if (boxes[i] != null) {
				if(box == null) box = boxes[i];
				
				Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				
				if(length1.lengthSquared() > length2.lengthSquared()) {
					box = boxes[i];
				}
			
			}
		}
		
		if(box != null){
			Collision data = bounding_box.getCollision(box);
			if(data.isIntersecting){
				bounding_box.correctPosition(box, data);
				transform.pos.set(bounding_box.getCenter(), 0);
			}
		}
		
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.2f); // changes how fast the camera keeps up with the player
		//makes the camera more smooth
		//camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));
	}
	
	public void render(Shader shader, Camera camera) {
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(camera.getProjection()));
		texture.bind(0);
		model.render();
	}

}
