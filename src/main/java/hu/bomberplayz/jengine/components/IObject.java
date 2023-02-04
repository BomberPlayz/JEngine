package hu.bomberplayz.jengine.components;

public interface IObject {
    void render(float delta);
    void update(float delta);
    void destroy();
    void init();
}
