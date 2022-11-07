package GameEngine.Utils;

public class GameTimer {

    private double fps = 0;
    private double dt = 0f;
    private double lastTime = 0;
    private double currentTime = 0;
    private double timeCounter = 0;
    private int frames = 0;

    public GameTimer() {
        lastTime = System.currentTimeMillis();
    }

    //This function will update the currentTime at the start of the loop
    //This should be called at the start of a game loop
    public void start() {

        //Update the current time
        currentTime = System.currentTimeMillis();
    }

    //This function will calculate dt and the fps as well as reseting everything after a second
    //This should be called at the end of the game loop
    public void update() {

        //Update the last time and calculate the dt
        //Keep the dt in seconds
        dt = (currentTime - lastTime) / 1000;
        lastTime = currentTime;
        timeCounter += dt;
        frames++;

        //Calculate the frames per second, reset timer and frames
        fps = frames / timeCounter;
        if (timeCounter >= 1) {
            timeCounter = 0;
            frames = 0;
        }

        //Make sure that dt is not 0
        if (dt == 0) {
            dt = 0f;
        }
    }

    //Getters and Setters
    public double getFPS() {
        return fps;
    }

    public double getDT() {
        return dt;
    }

    public double getLastTime() {
        return lastTime;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getTimeCounter() {
        return timeCounter;
    }

    public int getFrames() {
        return frames;
    }
}
