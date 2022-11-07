package GameEngine.Loaders;

import GameEngine.Models.Entity;
import GameEngine.Models.Model;
import GameEngine.Models.TexturedEntity;
import org.joml.Vector3f;

public class EntityLoader {

    private final ModelLoader modelLoader;

    public EntityLoader() {
        modelLoader = new ModelLoader();
    }

    //This function will load a models obj path and return a newly created Entity
    public Entity loadEntity(String modelPath) {
        return loadEntity(modelPath, new Vector3f(0, 0, 0), new Vector3f(1));
    }

    public Entity loadEntity(String modelPath, Vector3f position) {
        return loadEntity(modelPath, position, new Vector3f(1));
    }

    public Entity loadEntity(String modelPath, Vector3f position, Vector3f scale) {
        Model model = modelLoader.loadOBJModel(modelPath);
        Entity e = new Entity(model, position, scale);
        return e;
    }

    //This function will load an Entity and then bind a texture to it
    public TexturedEntity loadTexturedEntity(String modelPath, String texturePath) {
        return loadTexturedEntity(modelPath, texturePath, new Vector3f(0, 0, 0), new Vector3f(1));
    }

    public TexturedEntity loadTexturedEntity(String modelPath, String texturePath, Vector3f position) {
        return loadTexturedEntity(modelPath, texturePath, position, new Vector3f(1));
    }

    public TexturedEntity loadTexturedEntity(String modelPath, String texturePath, Vector3f position, Vector3f scale) {

        Entity e = loadEntity(modelPath, position, scale);
        TexturedEntity te = new TexturedEntity(e, texturePath);
        return te;
    }
}
