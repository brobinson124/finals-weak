import static org.lwjgl.glfw.GLFW.*;
// Import OpenGL for images | Jesus
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

public class Main {
	public Main() {
		if(!glfwInit()) //initializes GLFW r | Dylan
		{
			System.err.println("GLFW Failed to initialize");
			System.exit(1);
		} 
		
		// Draw window
		//width, height, name, isFullscreen, share | Dylan
		long win = glfwCreateWindow(1600, 1800, "Window", 0, 0);
					
		
		glfwShowWindow(win);
		//make so window can have context and display graphics | Dylan
		glfwMakeContextCurrent(win);
		
		//creating a context | Dylan
		GL.createCapabilities();
		//textures MUST be created below here | Dylan
		
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
		
		
		//Keyboard Input | Jesus
		while(!glfwWindowShouldClose(win)) { 
			//While window not closed, if ESC then exit | Dylan + Jesus
			if(glfwGetKey(win, GLFW_KEY_ESCAPE) == GL_TRUE) {
				glfwSetWindowShouldClose(win, true);
			}
			
			glfwPollEvents();
			
			//set all pixels to black, clears screen essentially | Dylan
			glClear(GL_COLOR_BUFFER_BIT); 
			
			//glClearColor(r,g,b);  //clear to colors
			
			if(glfwGetKey(win, GLFW_KEY_E) == GL_TRUE) {
				tex = tex2;
			}
	
			
			
			//tex.bind();
			shader.bind();
			shader.setUniform("sampler", 0);
			tex.bind(0);
			model.render();
			
			glfwSwapBuffers(win);
			
		}
		
		//when game finished
		glfwTerminate(); //clear memory 
	}
	
	public static void main(String[] args){
		new Main();
	}
	
}