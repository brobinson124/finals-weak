import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

public class Main {
	public Main() {
		if(!glfwInit()) //initializes GLFW r
		{
			System.err.println("GLFW Failed to initialize");
			System.exit(1);
		} 
		
		long win = glfwCreateWindow(700, 500, "Window", 0, 0);
					//width, height, name, isFullscreen, share
		
		glfwShowWindow(win);
		
		glfwMakeContextCurrent(win); //make so window can have context
		
		GL.createCapabilities();//creating a context 
		//textures must be created below here
		
		glEnable(GL_TEXTURE_2D);
		
		Texture tex = new Texture("./resource/playertest.png");
		
		while(!glfwWindowShouldClose(win)) { //while not closed
			if(glfwGetKey(win, GLFW_KEY_ESCAPE) == GL_TRUE) {
				glfwSetWindowShouldClose(win, true);
			}
			
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT); //set all pixels to black
			//glClearColor(r,g,b);  //clear to colors
			
			tex.bind();
			
			glBegin(GL_QUADS);//drawing a quad
				glTexCoord2f(0, 0);
				//glColor4f(color_red,0,color_blue,0); //apply color
				glVertex2f(-0.5f, 0.5f);
				
				glTexCoord2f(1, 0);
				glVertex2f(0.5f, 0.5f);
				
				glTexCoord2f(1, 1);
				glVertex2f(0.5f, -0.5f);
				
				glTexCoord2f(0, 1);
				glVertex2f(-0.5f, -0.5f);
			glEnd();
			
			glfwSwapBuffers(win);
			
		}
		
		//when game finished
		glfwTerminate(); //clear memory 
	}
	
	public static void main(String[] args){
		new Main();
	}
	
}