package GameEngine.Models;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TexturedEntity extends Entity implements Cloneable {

    protected TexturedModel model;

    public TexturedEntity() {
        super(new Entity());
    }

    public TexturedEntity(Entity entity, TexturedModel model) {
        super(entity);
        this.model = model;
    }

    public TexturedEntity(Entity entity, String texturePath) {
        super(entity);
        this.model = new TexturedModel(entity.getModel(), texturePath);
    }

    public TexturedEntity(TexturedEntity e) {
        this.position = new Vector3f(e.getPosition());
        this.scale = new Vector3f(e.getScale());
        this.rotation = new Matrix4f(e.getRotation());
        this.model = e.getModel();
    }

    @Override
    public TexturedEntity clone() throws CloneNotSupportedException {
        TexturedEntity te = new TexturedEntity(this);
        return te;
    }

    //Getters and setters
    @Override
    public TexturedModel getModel() {
        return model;
    }
}

