package GameEngine;

import GameEngine.Exceptions.SingletonException;
import GameEngine.Scene.Scene;
import GameEngine.Utils.GameTimer;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine {

    public static GameTimer timer;
    // Static Singleton methods
    private static Engine engine;
    // Engine instance methods and constructors
    private GLFWErrorCallback errorCallback;
    private Window window;
    private Scene scene;

    public Engine() throws SingletonException {
        this(500, 500, "3D Engine");
    }

    public Engine(int width, int height) throws SingletonException {
        this(width, height, "3D Engine");
    }

    public Engine(int width, int height, String title) throws SingletonException {

        // Singleton creation
        if (engine == null) {
            try {
                // Set the singleton instance
                engine = this;

                //Setup the error callback so that openGL can print errors
                glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

                //Initialise GLFW
                if (!glfwInit())
                    throw new IllegalStateException("Unable to initialize GLFW");

                //Create the window
                window = new Window(title, width, height);
                window.makeContext();
                window.show();

                //Setup the cursor
                glfwSetInputMode(window.getId(), GLFW_STICKY_KEYS, GL_TRUE);
                glfwSetInputMode(window.getId(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                glfwSetCursorPos(window.getId(), 0, 0);

                //Create the scene and timer
                scene = new Scene(window.getId());
                timer = new GameTimer();
            } catch (IllegalStateException e) {
                System.out.println("Could not initialise the engine");
                System.out.println(e.toString());
            }
        } else {
            throw new SingletonException("Duplicate instance attempted");
        }
    }

    public static Engine getInstance() {
        if (engine == null) {
            try {
                new Engine();
            } catch (SingletonException e) {
                System.out.println(e);
            }
        }
        return engine;
    }

    //This will start the game Engine
    public void run() {

        //Start the game loop to run the scene
        loop();

        //Destroy the window and the callbacks
        window.destroy();
        scene.getKeyCallback().free();
        scene.getCursorPosCallback().free();
        glfwTerminate();
        errorCallback.free();
    }

    //This is the game loop which will continuously run until the window is closed
    private void loop() {

        //Set the clear color and enable openGL operations
        glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //Run the game and rendering loop.
        while (!window.shouldClose()) {

            timer.start();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //Render and run the scene
            scene.run();
            scene.render();

            //Poll for events and swap buffers
            window.swapBuffers();
            glfwPollEvents();
            timer.update();
        }
    }

    //Getters and Setters
    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
