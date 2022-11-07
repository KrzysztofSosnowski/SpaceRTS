package GameEngine.Scene;

import GameEngine.Engine;
import GameEngine.Utils.EulerAngle;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f direction;
    private Matrix4f projection;
    private Matrix4f view;
    private EulerAngle angles;
    private final int width;
    private final int height;

    //Create the camera
    public Camera() {
        this(new Vector3f(0, 0, 0), 0, 0, 0);
    }

    public Camera(Vector3f position, float pitch, float yaw) {
        this(position, pitch, yaw, 0);
    }

    public Camera(Vector3f position, float pitch, float yaw, float roll) {
        this.position = position;
        this.angles = new EulerAngle(pitch, yaw, roll);
        this.direction = angles.toVector();
        this.width = Engine.getInstance().getWindow().getWidth();
        this.height = Engine.getInstance().getWindow().getHeight();
        this.projection = calculateProjection();
        this.view = calculateView();
    }

    //Create the projection matrix
    public Matrix4f calculateProjection() {
        Matrix4f result = new Matrix4f();
        result.perspective((float) Math.toRadians(45f), (float) width / height, 0.01f, 100.0f);
        return result;
    }

    //Create the view matrix
    public Matrix4f calculateView() {
        Matrix4f result = new Matrix4f();
        Vector3f temp = new Vector3f();
        direction = angles.toVector();
        position.sub(direction, temp);
        result.lookAt(position, temp, new Vector3f(0, 1, 0));
        return result;
    }

    //Update the camera by calculating nre projection and view
    public void update() {
        projection = calculateProjection();
        view = calculateView();
    }

    //This will point the camera in the direction of the given vector
    public void lookTo(Vector3f direction) {

        //Set the camera EulerAngles to one which looks in that direction
        angles.toAngles(direction);
        update();
    }

    //This will look at a specific point in world space
    public void lookAt(Vector3f point) {

        //Get the direction by minusing the point to look at and the camera position
        Vector3f direction = point.sub(position, new Vector3f());
        lookTo(direction);
    }

    //Getters and Setters
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public Matrix4f getView() {
        return view;
    }

    public EulerAngle getAngles() {
        return angles;
    }

    public void setAngles(EulerAngle angles) {
        this.angles = angles;
    }
}
