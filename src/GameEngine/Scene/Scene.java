package GameEngine.Scene;

import GameEngine.Loaders.ShaderLoader;
import GameEngine.Models.Entity;
import GameEngine.Models.Model;
import GameEngine.Models.TexturedEntity;
import GameEngine.Models.TexturedModel;
import GameEngine.Renderers.InstancedRenderer;
import GameEngine.Renderers.InstancedTexturedRenderer;
import GameEngine.Utils.EulerAngle;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Scene {

    private Camera camera;
    private Light light;
    private List<TexturedEntity> texturedEntities;
    private List<Entity> entities;
    private final InstancedTexturedRenderer itr;
    private final InstancedRenderer ir;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorPosCallback;

    public Scene(long windowID) {

        //Load the shaders
        ShaderLoader shaderLoader = new ShaderLoader("shaders/vertex.glsl", "shaders/fragment.glsl");
        int programID = shaderLoader.getProgramID();
        glUseProgram(programID);

        //Create the camera to look forward
        camera = new Camera(new Vector3f(0, 0, 0), 0, 180);
        light = new Light(new Vector3f(0, 1, 0), new Vector3f(1, 1, 1));

        //Create the lists and renderers
        texturedEntities = new ArrayList<>(0);
        entities = new ArrayList<>(0);
        itr = new InstancedTexturedRenderer(programID);
        ir = new InstancedRenderer(programID);

        //Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(windowID, keyCallback = new GLFWKeyCallback() {

            private final float speed = 0.3f;

            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, true);
                else if (key == GLFW_KEY_W) {

                    //Move the camera forward
                    camera.setPosition(camera.getPosition().sub(camera.getDirection().mul(speed)));
                    camera.update();
                } else if (key == GLFW_KEY_S) {

                    //Move the camera back
                    camera.setPosition(camera.getPosition().add(camera.getDirection().mul(speed)));
                    camera.update();
                } else if (key == GLFW_KEY_A) {

                    //Move the camera left
                    camera.setPosition(camera.getPosition().add(camera.getDirection().cross(new Vector3f(0, 1f, 0)).mul(speed)));
                    camera.update();
                } else if (key == GLFW_KEY_D) {

                    //Move the camera left
                    camera.setPosition(camera.getPosition().sub(camera.getDirection().cross(new Vector3f(0, 1f, 0)).mul(speed)));
                    camera.update();
                }
            }
        });

        //Setup the cursor position callback
        glfwSetCursorPosCallback(windowID, cursorPosCallback = new GLFWCursorPosCallback() {

            //Create the variables
            private EulerAngle angles;
            private final float sensitivity = 20;
            private double xPrev = 0, yPrev = 0;

            @Override
            public void invoke(long window, double x, double y) {

                //Update the camera by moving the EulerAngles
                angles = camera.getAngles();
                angles.setYaw(angles.getYaw() + (float) ((x - xPrev) / sensitivity));
                angles.setPitch(angles.getPitch() + (float) ((y - yPrev) / sensitivity));
                angles.constrain();

                xPrev = x;
                yPrev = y;
            }
        });
    }

    //Add an entity to the list
    public void addEntity(Entity e) {
        if (!entities.contains(e)) {
            entities.add(e);
        }
    }

    //Add a texturedEntity to the list
    public void addEntity(TexturedEntity e) {
        if (!texturedEntities.contains(e)) {
            texturedEntities.add(e);
        }
    }

    //This will run all of the entities
    public void run() {
        if (!texturedEntities.isEmpty()) {
            for (TexturedEntity e : texturedEntities) {
                e.run();
            }
        }
        if (!entities.isEmpty()) {
            for (Entity e : entities) {
                e.run();
            }
        }
    }

    //Render all of the entities in the lists
    public void render() {

        //Update the camera and render
        camera.update();
        if (!texturedEntities.isEmpty()) {
            Map<TexturedModel, List<TexturedEntity>> texturedMap = itr.assembleMap(texturedEntities);
            itr.renderEntityMap(texturedMap, this);
        }
        if (!entities.isEmpty()) {
            Map<Model, List<Entity>> map = ir.assembleMap(entities);
            ir.renderEntityMap(map, this);
        }
    }

    //Getters and setters
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public List<TexturedEntity> getTexturedEntities() {
        return texturedEntities;
    }

    public void setTexturedEntities(List<TexturedEntity> texturedEntities) {
        this.texturedEntities = texturedEntities;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public void setKeyCallback(GLFWKeyCallback keyCallback) {
        this.keyCallback = keyCallback;
    }

    public GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public void setCursorPosCallback(GLFWCursorPosCallback cursorPosCallback) {
        this.cursorPosCallback = cursorPosCallback;
    }
}
