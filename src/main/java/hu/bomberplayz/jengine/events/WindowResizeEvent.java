package hu.bomberplayz.jengine.events;

public class WindowResizeEvent extends Event {
    private int width;
    private int height;

    public WindowResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
