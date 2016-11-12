package render;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
public class Shader {
	private int program;
	private int vertex_shader; //process all the vertices that shader takes | Dylan
	private int fragment_shader; //gives things color | Dylan
	
	public Shader(String filename){
		program  = glCreateProgram();
		
		vertex_shader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertex_shader, readFile(filename+".vs")); //what shader??
		
		glCompileShader(vertex_shader);
		
		if(glGetShaderi(vertex_shader, GL_COMPILE_STATUS)  != 1){ //Check if there a problem getting shader | Dylan
			System.err.println(glGetShaderInfoLog(vertex_shader)); //prints what is wrong | Dylan
			System.exit(1);;
		}
		
		fragment_shader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragment_shader, readFile(filename+".fs")); //what shader?? sending source
		
		glCompileShader(fragment_shader);
		
		if(glGetShaderi(fragment_shader, GL_COMPILE_STATUS)  != 1){ //Check if there is a problem getting shader
			System.err.println(glGetShaderInfoLog(fragment_shader)); //prints what is wrong
			System.exit(1);;
		}
		
		glAttachShader(program, vertex_shader);
		glAttachShader(program, fragment_shader);
		
		//sending attributes to shader
		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");
		
		//link and validate shader
		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) != 1){
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
			
		}
		glValidateProgram(program);
		if(glGetProgrami(program, GL_VALIDATE_STATUS) != 1){
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
			
		}
		
	}
	
	public void setUniform(String name, int value){
		//uniform variable is stored in graphics card | Dylan
		int location = glGetUniformLocation(program, name);
		//is location valid | Dylan
		if(location != -1){
			glUniform1i(location, value);
		}
	}
		
	public void setUniform(String name, Matrix4f value){
		//uniform variable is stored in graphics card | Dylan
		int location = glGetUniformLocation(program, name);
		// 4x4 of Data, allow us to hold all info about scale, rotation, projection | Jesus
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		
		//is location valid | Dylan
		if(location != -1){
			glUniformMatrix4fv(location, false, buffer);
		}
	}
	
	public void bind() {
		glUseProgram(program);
	}
	
	
	//need a way to read from source
	private String readFile(String filename){
		StringBuilder string = new StringBuilder(); //where contents of files go
		BufferedReader buffReader;
		
		try {
			buffReader = new BufferedReader(new FileReader(new File("./shaders/" + filename)));
			String line;
			while((line = buffReader.readLine()) != null){ //reading lines
				string.append(line);
				string.append("\n");//append new line
			}
			buffReader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return string.toString();
	}
}
