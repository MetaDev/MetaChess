/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBufferData;

/**
 *
 * @author Harald
 */
public class MetaRenderer {

   static  FloatBuffer vertices;

    public static void createBoard() {
        MetaRenderer.vertices = BufferUtils.createFloatBuffer(3 * 6);
        vertices.put(-0.6f).put(-0.4f).put(0f).put(1f).put(0f).put(0f);
        vertices.put(0.6f).put(-0.4f).put(0f).put(0f).put(1f).put(0f);
        vertices.put(0f).put(0.6f).put(0f).put(0f).put(0f).put(1f);
        vertices.flip();
    }

    public static void render() {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);    }
}
