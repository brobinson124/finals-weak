package michaelProject;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

public class Main {
	
	public Main() {
		if(glfwInit() != true) {
			System.err.println("GLFW failed to initialize!");
			System.exit(1);
		}
		
		long win = glfwCreateWindow(640, 480, "Window", 0, 0);
		
		glfwShowWindow(win);
		
		glfwMakeContextCurrent(win);
		
		GL.createCapabilities();
		
		Camera camera = new Camera(640, 480);
		glEnable(GL_TEXTURE_2D);
		
		float[] vertices = new float[] {
				-0.5f, 0.5f, 0, 	//top left		0
				0.5f, 0.5f, 0, 		//top right		1
				0.5f, -0.5f, 0, 	//bottom right	2
				-0.5f, -0.5f, 0, 	//bottom left	3
		};
		
		float[] texture = new float[] {
				0,0,
				1,0,
				1,1,
				0,1,
		};
		
		int[] indices = new int[] {
				0,1,2,
				2,3,0
		};
		
		Model model = new Model(vertices, texture, indices);
		
		Shader shader = new Shader("shader");
		
		Texture tex = new Texture("./resources/stoneTex.jpg");
		
		Matrix4f scale = new Matrix4f()
				.translate(new Vector3f(100,0,0))
				.scale(64);
		
		Matrix4f target = new Matrix4f();
		
		camera.setPosition(new Vector3f(-100,0,0));
		
		while(glfwWindowShouldClose(win) != true) {
			target = scale;
			if(glfwGetKey(win, GLFW_KEY_ESCAPE) == GL_TRUE) {
				glfwSetWindowShouldClose(win, true);
			}
			
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			shader.bind();
			shader.setUniform("sampler", 0);
			shader.setUniform("projection", camera.getProjection().mul(target));
			tex.bind(0);
			model.render();
			
			glfwSwapBuffers(win);
		}
		
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
