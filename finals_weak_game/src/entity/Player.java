package entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import collision.AABB;
import collision.Collision;
import render.Animation;

import io.Window;
import render.Camera;
import render.Model;
import render.Shader;
import world.World;
import render.Texture;

public class Player extends Entity {
	public static final int ANIM_IDLE = 0;
	public static final int ANIM_WALK = 1;
	public static final int ANIM_SIZE = 2;
	
	public Player(Transform transform) {
		super(ANIM_SIZE, transform);
		setAnimation(ANIM_IDLE, new Animation(2, 3, "id"));
		setAnimation(ANIM_WALK, new Animation(2, 5, "an"));
	}
	
	@Override
	public void update(float delta, Window window, Camera camera, World world ){
		Vector2f movement = new Vector2f();
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_A)) {
			movement.add(-10*delta, 0);
		}
		
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_D)) {
			movement.add(10*delta, 0);
		}
		
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_W)) {
			movement.add(0, 10*delta);
		}
		
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_S)) {
			movement.add(0, -10*delta);
		}
		
		move(movement);
		
		if(movement.x != 0 | movement.y != 0){
			useAnimation(ANIM_WALK);
		}else{
			useAnimation(ANIM_IDLE);
		}
			
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.1f);

	}
//	private Model model;
//	private AABB bounding_box;
//	//private Texture texture;
//	private Animation texture;
//	private Transform transform;
//	
//	
//	public Player(){
//		float[] vertices = new float[] {
//				-1f, 1f, 0, 	//top left		0
//				1f, 1f, 0, 		//top right		1
//				1f, -1f, 0, 	//bottom right	2
//				-1f, -1f, 0, 	//bottom left	3
//		};
//		
//		float[] texture = new float[] {
//				0,0,
//				1,0,
//				1,1,
//				0,1,
//		};
//		
//		int[] indices = new int[] {
//				0,1,2,
//				2,3,0
//		};
//		
//		model = new Model(vertices, texture, indices);
//		this.texture = new Animation(2, 5, "an");
//		
//		transform = new Transform();
//		transform.scale = new Vector3f(32,32,1);
//		
//		bounding_box = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(1,1));
//	}
//	
//	public void update(float delta, Window window, Camera camera, World world){
//		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_A)) {
//			transform.pos.add(new Vector3f(-10*delta, 0, 0));
//		}
//		
//		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_D)) {
//			transform.pos.add(new Vector3f(10*delta, 0, 0));
//		}
//		
//		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_W)) {
//			transform.pos.add(new Vector3f(0, 10*delta, 0));
//		}
//		
//		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_S)) {
//			transform.pos.add(new Vector3f(0, -10*delta, 0));
//		}
//		
//		bounding_box.getCenter().set(transform.pos.x, transform.pos.y);
//		
//		AABB[] boxes = new AABB[25];
//		for(int i = 0; i < 5; i++){
//			for(int j = 0; j < 5; j++){
//				boxes[i + j * 5] = world.getTileBoundingBox(
//							(int)(((transform.pos.x/2) + 0.5f) - (5/2)) + i,
//							(int)(((-transform.pos.y/2) + 0.5f) - (5/2)) + j
//						);
//			}
//		}
//		
//		AABB box = null;
//		for(int i = 0; i < boxes.length; i++){
//			if(boxes[i] != null){
//				if(box == null) box = boxes[i];
//				
//				Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
//				Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
//				
//				if(length1.lengthSquared() > length2.lengthSquared()){
//					box = boxes[i];
//				}
//			
//			}
//		}
//		
//		if(box != null){
//			Collision data = bounding_box.getCollision(box);
//			if(data.isIntersecting){
//				bounding_box.correctPosition(box,  data);
//				transform.pos.set(bounding_box.getCenter(), 0);
//			}
//	
//		
//			for(int i = 0; i < boxes.length; i++){
//				if(boxes[i] != null){
//					if(box == null) {
//						box = boxes[i];
//					}
//					Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
//					Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
//					
//					if(length1.lengthSquared() > length2.lengthSquared()){
//						box = boxes[i];
//					}
//				
//				}
//			}
//			
//			data = bounding_box.getCollision(box);
//			if(data.isIntersecting){
//				bounding_box.correctPosition(box,  data);
//				transform.pos.set(bounding_box.getCenter(), 0);
//			}
//		}
//		
//		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.1f);
//		//camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));
//	}
//	
//	public void render(Shader shader, Camera camera){
//		shader.bind();
//		shader.setUniform("sampler", 0);
//		shader.setUniform("projection", transform.getProjection(camera.getProjection()));
//		texture.bind(0);
//		model.render();
//	}
}
