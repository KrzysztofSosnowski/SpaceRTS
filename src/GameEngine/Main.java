package GameEngine;

import GameEngine.Loaders.EntityLoader;
import GameEngine.Models.Entity;
import GameEngine.Models.TexturedEntity;
import GameEngine.Scene.Camera;
import GameEngine.Scene.Light;
import GameEngine.Scene.Scene;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Engine engine = new Engine(1280, 800, "Cosmos RTS");
        Scene scene = engine.getScene();
        Camera camera = scene.getCamera();
        Light light = new Light(new Vector3f(2, 2, 4), new Vector3f(1, 1, 1));
        scene.setLight(light);

        EntityLoader loader = new EntityLoader();

        /*for (int i = 1; i <= 5; i++) {
            TexturedEntity room = loader.loadTexturedEntity("bedroom_lvl"+i+".obj", "bedroom_lvl"+i+".png");
            room.setPosition(new Vector3f(i*1.6f, 0, 0));
            room.setScale(new Vector3f(1f));
            scene.addEntity(room);
        }*/

        Room bedroom = new Room("Bedroom", "bedroom", 5);
        bedroom.setPosition(new Vector3f(0, 0, 0));
        bedroom.setScale(new Vector3f(1f));

        bedroom.setAction(() -> {
                //System.out.println(Engine.timer.getFPS());

                if(Engine.timer.getFrames() % 1000 == 0)
                    bedroom.levelUp();
        });

        Room botanic = new Room("Botanic", "botanic", 5);
        botanic.setPosition(new Vector3f(2, 0, 0));
        botanic.setScale(new Vector3f(1f));

        botanic.setAction(() -> {
            if(Engine.timer.getFrames() % 1000 == 0)
                botanic.levelUp();
        });

        Room cargo = new Room("Cargo", "cargo", 5);
        cargo.setPosition(new Vector3f(0, 0, 2));
        cargo.setScale(new Vector3f(1f));

        cargo.setAction(() -> {
            if(Engine.timer.getFrames() % 1000 == 0)
                cargo.levelUp();
        });

        Room reactor = new Room("Reactor", "reactor", 5);
        reactor.setPosition(new Vector3f(2, 0, 2));
        reactor.setScale(new Vector3f(1f));

        reactor.setAction(() -> {
            if(Engine.timer.getFrames() % 1000 == 0)
                reactor.levelUp();
        });

        Room livingroom = new Room("Living Room", "livingroom", 5);
        livingroom.setPosition(new Vector3f(0, 0, 4));
        livingroom.setScale(new Vector3f(1f));

        livingroom.setAction(() -> {
            if(Engine.timer.getFrames() % 1000 == 0)
                livingroom.levelUp();
        });

        scene.addEntity(bedroom);
        scene.addEntity(botanic);
        scene.addEntity(cargo);
        scene.addEntity(reactor);
        scene.addEntity(livingroom);

        camera.setPosition(new Vector3f(0, 5, 5));
        camera.lookAt(new Vector3f(0, 0, 0));

        engine.run();
    }
}

/*
 // Create the engine and get the objects
        Engine engine = new Engine(800, 600, "Cosmos RTS");
        Scene scene = engine.getScene();
        Camera camera = scene.getCamera();
        Light light = new Light(new Vector3f(0, 1, 1), new Vector3f(1, 1, 1));
        scene.setLight(light);

        //Create the Falcon and add to the scene
        EntityLoader loader = new EntityLoader();
        TexturedEntity falcon = loader.loadTexturedEntity("livingroom_lvl3.obj", "livingroom_lvl3.png");
        falcon.setPosition(new Vector3f(0, 0, 0));
        falcon.setScale(new Vector3f(1f));
        scene.addEntity(falcon);

        Vector3f lookAt = new Vector3f(-3, 0, -3);
        Entity sphere = loader.loadEntity("Flag_Ship_1_interior_3.obj", lookAt);
        sphere.setScale(new Vector3f(0.2f));
        scene.addEntity(sphere);

        //Should change the direction value each time
        Vector3f forward = new Vector3f(0, 0, 1);
        Quaternionf rotation = new Quaternionf();

        falcon.setAction(()-> {
            Vector3f direction = lookAt.sub(falcon.getPosition(), new Vector3f()).normalize();
            Quaternionf q = new Quaternionf().rotationTo(forward, direction);

            rotation.nlerp(q, (float)Engine.timer.getDT() / 5); // Rotation over 5 seconds
            //cube.getRotation().rotateX((float)(Math.toRadians(-45) * (Engine.timer.getDT() / 5))); // Handle X axis rotation seperately

            falcon.setRotation(rotation.get(new Matrix4f()));
            falcon.getPosition().add(new Vector3f(1, 0, 0).mul((float)Engine.timer.getDT() / 5)); // Move right 1 unit every 5 seconds
        });

        //Make the camera look at the falcon
        camera.setPosition(new Vector3f(0, 1, 3));
        camera.lookAt(falcon.getPosition());

        engine.run();
 */
