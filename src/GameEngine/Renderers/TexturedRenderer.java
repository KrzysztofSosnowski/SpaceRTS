package GameEngine.Renderers;

import GameEngine.Models.Entity;
import GameEngine.Models.TexturedEntity;
import GameEngine.Models.TexturedModel;
import GameEngine.Scene.Scene;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class TexturedRenderer extends Renderer {

    public TexturedRenderer(int programID) {
        super(programID);
    }

    //This will bind a textured model
    protected void bindModel(TexturedModel model) {
        super.bindModel(model);

        //Bind the texture
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTextureID());
    }

    @Override
    protected void unbindModel() {
        super.unbindModel();
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    protected void sendUniforms(Entity entity, Scene scene) {
        super.sendUniforms(entity, scene);

        //Tell the shader that there is a texture for the following model
        sendBoolean(true, "isTextured");
    }

    //This will render the textured model
    public void renderModel(TexturedModel model) {
        bindModel(model);
        glDrawArrays(GL_TRIANGLES, 0, model.getVerticesSize());
        unbindModel();
    }

    //This will render a model based on its position and the MVP matrix
    public void renderEntity(TexturedEntity entity, Scene scene) {

        //Send the uniform variables and render the model
        sendUniforms(entity, scene);
        renderModel(entity.getModel());
    }
}
