import static org.lwjgl.glfw.GLFW.*;
// Import OpenGL for images | Jesus
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;
import world.Tile;
import world.TileRenderer;
import world.World;

public class Main {
	public Main() {
		Window.setCallbacks();
		if(!glfwInit()) //initializes GLFW r | Dylan
		{
			System.err.println("GLFW Failed to initialize");
			System.exit(1);
		} 
		
		// Draw window
		Window win = new Window();
		//Window Size | Jesus
		win.setSize(1280, 720);
		// If you want to see full screen, change the setsize() resolution to
		// your monitors resolution and uncomment the next line
		//win.setFullScreen(true);
		win.createWindow("Finals Weak");
		
		
		//creating a context | Dylan
		GL.createCapabilities();
		//textures MUST be created below here | Dylan
		//Create camera | Jesus
		Camera camera = new Camera(win.getWidth(), win.getHeight());
		
		glEnable(GL_TEXTURE_2D);
		
		TileRenderer tiles = new TileRenderer();
		
		Shader shader = new Shader("shader");
		Texture tex = new Texture("playertest.png");
		Texture tex2 = new Texture("player2.png");
		
		
/*		Matrix4f scale = new Matrix4f()
				//Translate(Horizontal, Vertical, Z) | Jesus
				.translate(new Vector3f (0,0,0))
				// Scale value can be changed to make texture bigger | Jesus
				.scale(50);
		
		Matrix4f target = new Matrix4f();
		
*/
		// Replaced the above matrix with the world | Brooke
		
		World world = new World();
		
		world.setTile(Tile.test2, 0, 0); //new vampire tile | Brooke
		
		//camera.setPosition(new Vector3f(-100,0,0));
		// Don't need above | Brooke 
		
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
				//target = scale;
				
				//While window not closed, if ESC then exit | Dylan + Jesus
				//win.getInput().isMouseButtonDown(0)) for Mouse input
				if(win.getInput().isKeyPressed(GLFW_KEY_ESCAPE)) { //This takes in one key at a time, so long pressing on a key will just be interpreted as one
					glfwSetWindowShouldClose(win.getWindow(), true);
				}
				
				if(win.getInput().isKeyDown(GLFW.GLFW_KEY_A)) {
					camera.getPosition().sub(new Vector3f(5, 0, 0));
				}
				
				if(win.getInput().isKeyDown(GLFW.GLFW_KEY_D)) {
					camera.getPosition().sub(new Vector3f(-5, 0, 0));
				}
				
				if(win.getInput().isKeyDown(GLFW.GLFW_KEY_W)) {
					camera.getPosition().sub(new Vector3f(0, -5, 0));
				}
				
				if(win.getInput().isKeyDown(GLFW.GLFW_KEY_S)) {
					camera.getPosition().sub(new Vector3f(0, 5, 0));
				}
				
				world.correctCamera(camera, win);
				
				win.update();
				
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
				
			/*	for (int i = 0; i < 2; i++)
					for (int j = 0; j < 4; j++)
						tiles.renderTile((byte) 0, i, j, shader, scale, camera);
			*/	
				//replaced above with world | Brooke
				
				world.render(tiles, shader, camera);
				
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