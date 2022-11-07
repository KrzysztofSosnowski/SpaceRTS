package GameEngine;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final long id;
    private final String title;
    private final int width;
    private final int height;

    public Window() {
        this("New Window", 600, 600);
    }

    public Window(String title, int width, int height) {

        this.title = title;
        this.width = width;
        this.height = height;

        //Create the GLFW window and assign the id
        this.id = glfwCreateWindow(width, height, title, NULL, NULL);
        if (id == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        //Configure the window hints
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(id, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
    }

    //Make this window the current context
    public void makeContext() {
        glfwMakeContextCurrent(id);
        createCapabilities();
    }

    //Display the window on the screen
    public void show() {
        glfwShowWindow(id);
    }

    //Destroy this window
    public void destroy() {
        glfwDestroyWindow(id);
    }

    //Returns whether this window should close
    public boolean shouldClose() {
        return glfwWindowShouldClose(id);
    }

    //Swap the current buffers to stop tearing
    public void swapBuffers() {
        glfwSwapBuffers(id);
    }

    //Return the current windows id
    public long getId() {
        return this.id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }
}
