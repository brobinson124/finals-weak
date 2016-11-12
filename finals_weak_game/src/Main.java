import static org.lwjgl.glfw.GLFW.*;
// Import OpenGL for images | Jesus
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

public class Main {
	public Main() {
		if(!glfwInit()) //initializes GLFW r | Dylan
		{
			System.err.println("GLFW Failed to initialize");
			System.exit(1);
		} 
		
		// Draw window
		Window win = new Window();
//		Way to override screen size
		win.setSize(100, 100);
		
		win.createWindow("Finals Weak");
		
		//creating a context | Dylan
		GL.createCapabilities();
		//textures MUST be created below here | Dylan
		
		//Create camera | Jesus
		Camera camera = new Camera(1800, 1600);
		
		glEnable(GL_TEXTURE_2D);
		
		float[] vertices = new float[]{
				-0.5f, 0.5f, 0, //TL		0 
					0.5f, 0.5f, 0, //TR		1
					0.5f, -0.5f, 0, //BR 	2
					-0.5f, -0.5f, 0, //BL 	3
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
		
		Model model = new Model(vertices, texture, indices);
		Shader shader = new Shader("shader");
		Texture tex = new Texture("./resource/playertest.png");
		Texture tex2 = new Texture("./resource/player2.png");
		
		
		
		
		Matrix4f scale = new Matrix4f()
				//Translate(Horizontal, Vertical, Z) | Jesus
				.translate(new Vector3f (100,0,0))
				// Scale value can be changed to make texture bigger | Jesus
				.scale(128);
		
		Matrix4f target = new Matrix4f();
		
		camera.setPosition(new Vector3f(-100,0,0));
		
		// Framerate for game | Jesus
		double frameCap = 1.0/60.0;
		double frameTime = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		//Keyboard Input | Jesus
		while(!win.shouldClose()) {
			boolean canRender = false;
			//Get current time and find the difference
			double time2 = Timer.getTime();
			double passed = time2 - time;
			// Process anything that hasn't yet | Jesus
			unprocessed += passed;
			frameTime += passed;
			time = time2;
			
			while (unprocessed >= frameCap){
				unprocessed -= frameCap;
				canRender = true;
				// Update Camera Variable
				target = scale;
				
				//While window not closed, if ESC then exit | Dylan + Jesus
//				if(glfwGetKey(win, GLFW_KEY_ESCAPE) == GL_TRUE) {
//					glfwSetWindowShouldClose(win, true);
//				}
				glfwPollEvents();
				
				// Display FPS on Console | Jesus
				if (frameTime >= 1.0) {
					frameTime = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
				
			}
			if (canRender){
				//set all pixels to black, clears screen essentially | Dylan
				glClear(GL_COLOR_BUFFER_BIT); 
				
				//glClearColor(r,g,b);  //clear to colors
				
//				if(glfwGetKey(win, GLFW_KEY_E) == GL_TRUE) {
//					tex = tex2;
//				}
				
				//tex.bind();
				shader.bind();
				shader.setUniform("sampler", 0);
				shader.setUniform("projection", camera.getProjection().mul(target));
				tex.bind(0);
				model.render();
				
				win.swapBuffers();
				frames++;
				
			}
			
		}
		
		//when game finished
		glfwTerminate(); //clear memory 
	}
	
	public static void main(String[] args){
		new Main();
	}
	
}