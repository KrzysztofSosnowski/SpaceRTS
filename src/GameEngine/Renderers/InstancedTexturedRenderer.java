package GameEngine.Renderers;

import GameEngine.Models.TexturedEntity;
import GameEngine.Models.TexturedModel;
import GameEngine.Scene.Scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class InstancedTexturedRenderer extends TexturedRenderer {

    public InstancedTexturedRenderer(int programID) {
        super(programID);
    }

    //This will assemble the map of entities
    public Map<TexturedModel, List<TexturedEntity>> assembleMap(List<TexturedEntity> entities) {

        Map<TexturedModel, List<TexturedEntity>> map = new HashMap<>();
        for (TexturedEntity entity : entities) {

            //Add the entity to the list if that model already exists
            if (map.containsKey(entity.getModel())) {
                map.get(entity.getModel()).add(entity);
            } else {

                //Create a new list for the map
                ArrayList<TexturedEntity> list = new ArrayList<>(0);
                list.add(entity);
                map.put(entity.getModel(), list);
            }
        }
        //System.out.println(map);
        return map;
    }

    public Map<TexturedModel, List<TexturedEntity>> assembleMap(TexturedEntity[] entities) {

        //Create a list from the array
        List<TexturedEntity> list = new ArrayList<>(entities.length);
        for (TexturedEntity entity : entities) {
            list.add(entity);
        }
        return assembleMap(list);
    }

    //This will render the entities onto the screen
    public void renderEntityMap(Map<TexturedModel, List<TexturedEntity>> map, Scene scene) {

        //Get each of the different models
        for (Map.Entry<TexturedModel, List<TexturedEntity>> entry : map.entrySet()) {
            bindModel(entry.getKey());
            for (TexturedEntity entity : entry.getValue()) {

                //Send the uniform data
                sendUniforms(entity, scene);

                //Make a call to the render function
                glDrawArrays(GL_TRIANGLES, 0, entry.getKey().getVerticesSize());
            }
            unbindModel();
        }
    }
}
