package GameEngine.Models;

public class Material {
    float dampening;
    float reflectivity;

    public Material() {
        this(0.1f, 5);
    }

    public Material(float reflectivity, float dampening) {
        this.dampening = dampening;
        this.reflectivity = reflectivity;
    }

    //Getters and Setters
    public float getDampening() {
        return dampening;
    }

    public void setDampening(float dampening) {
        this.dampening = dampening;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
