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
		Texture tex = new Texture("./resource/playertest.png");
		
		while(!glfwWindowShouldClose(win)) { //while not closed
			if(glfwGetKey(win, GLFW_KEY_ESCAPE) == GL_TRUE) {
				glfwSetWindowShouldClose(win, true);
			}
			
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT); //set all pixels to black
			//glClearColor(r,g,b);  //clear to colors
			
			tex.bind();
			
			model.render();
//			glBegin(GL_QUADS);//drawing a quad
//				glTexCoord2f(0, 0);
//				//glColor4f(color_red,0,color_blue,0); //apply color
//				glVertex2f(-0.5f, 0.5f);
//				
//				glTexCoord2f(1, 0);
//				glVertex2f(0.5f, 0.5f);
//				
//				glTexCoord2f(1, 1);
//				glVertex2f(0.5f, -0.5f);
//				
//				glTexCoord2f(0, 1);
//				glVertex2f(-0.5f, -0.5f);
//			glEnd();
			
			glfwSwapBuffers(win);
			
		}
		
		//when game finished
		glfwTerminate(); //clear memory 
	}
	
	public static void main(String[] args){
		new Main();
	}
	
}