package io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class Window {
	private long window;
	
	private int width, height;
	private boolean fullscreen;
	private boolean hasResized;
	private GLFWWindowSizeCallback windowSizeCallback;
	
	private Input input;
	
	public static void setCallbacks() {
		glfwSetErrorCallback(new GLFWErrorCallback() {
			@Override
			public void invoke(int error, long description) {
				throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
			}
		});
	}
	
	private void setLocalCallbacks(){
		windowSizeCallback = new GLFWWindowSizeCallback(){
			@Override
			public void invoke(long argWindow, int argWidth, int argHeight) {
				width = argWidth;
				height = argHeight;
				hasResized = true;
				// TODO Auto-generated method stub
				
			}	
		};
		
		glfwSetWindowSizeCallback(window, windowSizeCallback);
	}
	
	public Window(){
		setSize(649, 480);
		setFullscreen(false);
		hasResized = false;
	}
	
	public void createWindow(String title) {
		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		
		if(window == 0){
			throw new IllegalStateException("Failed to create window");
		}
		
		if(!fullscreen){
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window,
					(vid.width() - width)/2,
					(vid.height() -height)/2);
			
			
			glfwShowWindow(window);
		}
		
		glfwMakeContextCurrent(window);
		
		input = new Input(window);
		setLocalCallbacks();
	}
	
	public void cleanUp(){
		windowSizeCallback.close();
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window) != false;
	}
	
	public void swapBuffers(){
		glfwSwapBuffers(window);
	}
	
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void setFullscreen(boolean fullscreen){
		this.fullscreen = fullscreen;
	}
	
	public void update() {
		input.update();
		glfwPollEvents();
		hasResized = false;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public boolean hasResized() {return hasResized; }
	public boolean getFullscreen() { return fullscreen; }
	public long getWindow() { return window; }
	public Input getInput() {return input;}
}
