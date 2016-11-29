package io;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;


public class Window {
	private long window;
	
	private int width, height;
	private boolean fullScreen;
	
	private Input input;
	
	public static void setCallbacks() {
		//We don't use custom callback, since it makes newer
		//versions of LWGWL upset | Jesus
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}

	public Window() {
		
		setSize(640, 480);
		setFullScreen(false);
		
		
	}
	
	public void createWindow(String title) {
		// Checks if screen should be full screen | Jesus
		window = glfwCreateWindow(width, height, title, fullScreen ? glfwGetPrimaryMonitor() : 0, 0);
		
		if (window == 0)
			throw new IllegalStateException("Failed to create window");
		
		if (!fullScreen) {
			// Center Window on Screen | Jesus
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window,
					(vid.width()-width)/2,
					(vid.height()-height)/2);
			
		}	
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
		input = new Input(window);
		
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
	
	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}
	
	public void update() {
		input.update();
		glfwPollEvents();
		
	}
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public boolean getFullScreen(){ return fullScreen; }
	public long getWindow() { return window; }
	public Input getInput() { return input; }
	

}
