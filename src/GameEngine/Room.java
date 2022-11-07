package GameEngine;

import GameEngine.Loaders.ModelLoader;
import GameEngine.Models.TexturedEntity;
import GameEngine.Models.TexturedModel;

import java.util.ArrayList;
import java.util.List;

public class Room extends TexturedEntity {
    private final ModelLoader modelLoader;

    private String name;
    private int level;

    private List<TexturedModel> models;

    public Room(String name, String filename, int levels) {
        super();
        modelLoader = new ModelLoader();

        level = 0;
        models = new ArrayList<>();

        for (int i = 1; i <= levels; i++)
            models.add(new TexturedModel(modelLoader.loadOBJModel(filename + "_lvl" + i + ".obj"), filename + "_lvl" + i + ".png"));

        model = models.get(level);
    }

    public void levelUp() {
        level ++;

        if(level > models.size()-1)
            level = 0;

        model = models.get(level);
    }
}
