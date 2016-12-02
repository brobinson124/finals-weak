package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import collision.AABB;
import entity.Entity;
import entity.Player;
import entity.Transform;
import world.Tile;
import world.TileRenderer;
import world.World;
import render.*;
import io.*;

public class Main {
	
	public Main() {
		Window.setCallbacks();
		
//		AABB box1 = new AABB(new Vector2f(0,0), new Vector2f(1,1));
//		AABB box2 = new AABB(new Vector2f(2,0), new Vector2f(1,1));
//		
//		if(box1.isIntersecting(box2)){
//			System.out.println("The boxes are intersecting");
//		}
		
		if(glfwInit() != true) {
			System.err.println("GLFW failed to initialize!");
			System.exit(1);
		}
		
		Window win = new Window();
		win.setSize(1024,  760);
		win.setFullscreen(false);
		win.createWindow("Finals-Weak");
		
		GL.createCapabilities();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Camera camera = new Camera(win.getWidth(), win.getHeight());
		glEnable(GL_TEXTURE_2D);
		
		TileRenderer tiles = new TileRenderer();
		
		Entity.InitAsset();
		
//		float[] vertices = new float[] {
//				-0.5f, 0.5f, 0, 	//top left		0
//				0.5f, 0.5f, 0, 		//top right		1
//				0.5f, -0.5f, 0, 	//bottom right	2
//				-0.5f, -0.5f, 0, 	//bottom left	3
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
//		Model model = new Model(vertices, texture, indices);

		Shader shader = new Shader("shader");
		
		//Texture tex = new Texture("./resources/stoneTex.jpg");
		
		World world = new World("test_level", camera);
		world.calculateView(win);
//		Transform t = new Transform();
//		t.scale.x = 1;
//		t.scale.y = 1;
//		Player player = new Player(t);

		
//		Matrix4f scale = new Matrix4f()
//				.translate(new Vector3f(0,0,0))
//				.scale(16);
//		
//		Matrix4f target = new Matrix4f();
//		
//		camera.setPosition(new Vector3f(-100,0,0));
		
		double frame_cap = 1.0/60.0;
		
		double frame_time = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		
		
		while(!win.shouldClose()) {
			boolean can_render = false;
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;
			
			time = time_2;
			
			while(unprocessed >= frame_cap){
				if(win.hasResized()){
					camera.setProjection(win.getWidth(), win.getHeight());
					world.calculateView(win);
					glViewport(0, 0, win.getWidth(), win.getHeight());
				}
				unprocessed -= frame_cap;
				can_render = true;
				
				//target = scale;
				if(win.getInput().isKeyPressed(GLFW_KEY_ESCAPE)) {
					glfwSetWindowShouldClose(win.getWindow(), true);
				}
				
				
				world.update((float)frame_cap,  win,  camera);
				
				world.correctCamera(camera, win);
				
				glfwPollEvents();
				//win.update();
				if(frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			if(can_render) {
				glClear(GL_COLOR_BUFFER_BIT);
				
//				shader.bind();
//				shader.setUniform("sampler", 0);
//				shader.setUniform("projection", camera.getProjection().mul(target));
//				tex.bind(0);
//				model.render();
				
//				for(int i = 0; i< 8; i++){
//					for(int j = 0; j< 4; j++){
//						tiles.renderTile((byte)0,  i,  j,  shader,  scale,  camera);
//					}
//				}
				
				world.render(tiles, shader, camera);
				
				//player.render(shader,  camera, world);
				
				win.swapBuffers();
				frames++;
			}
			
		}
		
		Entity.deleteAsset();
		
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
