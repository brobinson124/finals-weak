import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;



public class Model {
	private int draw_count;
	private int vertex_id;
	private int texture_id;
	
	private int indices_id;
	
	public Model(float[] vertices, float[] texture_coords, int[] indices) {
		draw_count = indices.length;//amount of vertices we draw with
		
		
		
		vertex_id = glGenBuffers();//generate vertex ID
		glBindBuffer(GL_ARRAY_BUFFER, vertex_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
									//data: static vs dynamic(change later on)
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
		glBindBuffer(GL_ARRAY_BUFFER, 0); //unbind buffer
	}
	
	public void render() {
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, vertex_id);
		glVertexPointer(3, GL_FLOAT, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, texture_id);
		glTexCoordPointer(2, GL_FLOAT, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indices_id);
		
		glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0); //draw elements
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	}
	
	private FloatBuffer createBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
}