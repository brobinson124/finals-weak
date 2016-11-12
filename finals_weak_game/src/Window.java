import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;


public class Window {
	private long window;
	
	private int width, height;

	public Window() {
		
		setSize(1800, 1600);
		
		
	}
	
	public void createWindow(String title) {
		window = glfwCreateWindow(width, height, title,  0, 0);
		
		if (window == 0)
			throw new IllegalStateException("Failed to create window");
		
		// Center Window on Screen | Jesus
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window,
				(vid.width()-width)/2,
				(vid.height()-height)/2);
		
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window) != false; //?
	}
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }

}
