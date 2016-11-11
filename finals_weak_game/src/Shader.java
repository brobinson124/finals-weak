import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class Shader {
	private int program;
	private int vertex_shader; //proccess all the vertecies that shader takes
	private int fragment_shader; //gives things color
	
	public Shader(String filename){
		program  = glCreateProgram();
		
		vertex_shader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertex_shader, readFile(filename+".vs")); //what shader??
		
		glCompileShader(vertex_shader);
		
		if(glGetShaderi(vertex_shader, GL_COMPILE_STATUS)  != 1){ //is there a problem getting shader
			System.err.println(glGetShaderInfoLog(vertex_shader)); //prints what is wrong
			System.exit(1);;
		}
		
		fragment_shader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragment_shader, readFile(filename+".fs")); //what shader?? sending source
		
		glCompileShader(fragment_shader);
		
		if(glGetShaderi(fragment_shader, GL_COMPILE_STATUS)  != 1){ //is there a problem getting shader
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
		//uniform variable is stored in graphics card
		int location = glGetUniformLocation(program, name);
		//is location valid
		if(location != -1){
			glUniform1i(location, value);
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
