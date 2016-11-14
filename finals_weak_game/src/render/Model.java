package render;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*; //get access to shader commands

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;



public class Model {
	private int draw_count;
	private int vertex_id;
	private int texture_id;
	
	private int indices_id;
	
	public Model(float[] vertices, float[] texture_coords, int[] indices) {
		draw_count = indices.length;//amount of vertices we draw with | Dylan
		
		
		
		vertex_id = glGenBuffers();//generate vertex ID | Dylan
		glBindBuffer(GL_ARRAY_BUFFER, vertex_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
									//data: static vs dynamic(change later on) | Dylan
		texture_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, texture_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(texture_coords), GL_STATIC_DRAW);
		
		indices_id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indices_id);
		
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0); //unbind buffer | Dylan
	}
	
	public void render() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1); //enable texture attribute | Dylan
		
		glBindBuffer(GL_ARRAY_BUFFER, vertex_id);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, texture_id);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indices_id);
		
		glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0); //draw elements | Dylan
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	protected void finalize() {
		glDeleteBuffers(vertex_id);
		glDeleteBuffers(texture_id);
		glDeleteBuffers(indices_id);
	}
	
	private FloatBuffer createBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
}
