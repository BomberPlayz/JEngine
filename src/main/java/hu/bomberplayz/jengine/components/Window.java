package hu.bomberplayz.jengine.components;

import com.google.common.eventbus.EventBus;
import hu.bomberplayz.jengine.Main;
import hu.bomberplayz.jengine.events.KeyInputEvent;
import hu.bomberplayz.jengine.events.WindowResizeEvent;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private long handle;

    private EventBus eventBus = new EventBus();

    private ArrayList<IObject> objects = new ArrayList<>();



    public Window(int width, int height) {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        handle = glfwCreateWindow(width, height, "JEngine Program", NULL, NULL);
        if (handle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            Main.eventBus.post(new KeyInputEvent(key, scancode, action, mods));
        });

        glfwSetFramebufferSizeCallback(handle, (window, width1, height1) -> {
            Main.eventBus.post(new WindowResizeEvent(width1, height1));
        });
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void registerEvent(Object object) {
        eventBus.register(object);
    }

    public void show() {
        glfwShowWindow(handle);
    }

    public void hide() {
        glfwHideWindow(handle);
    }

    public void destroy() {
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
    }

    public long getHandle() {
        return handle;
    }

    public void takeContext() {
        glfwMakeContextCurrent(handle);
    }

    public Vector2d getSize() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            glfwGetWindowSize(handle, width, height);
            return new Vector2d(width.get(), height.get());
        }
    }

    public void setSize(Vector2d size) {
        glfwSetWindowSize(handle, (int) size.getX(), (int) size.getY());
    }

    public void setSize(int width, int height) {
        glfwSetWindowSize(handle, width, height);
    }

    public Vector2d getPosition() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer x = stack.mallocInt(1);
            IntBuffer y = stack.mallocInt(1);
            glfwGetWindowPos(handle, x, y);
            return new Vector2d(x.get(), y.get());
        }
    }

    public void addObject(IObject object) {
        objects.add(object);
    }


    float lastFrameTime = 1.0f;
    float delta = 0.1f;
    public void renderLoop() {
        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        for (IObject object : objects) {
            object.init();
        }

        while (!glfwWindowShouldClose(handle)) {
            float currentFrameTime = (float) glfwGetTime();
            for (IObject object : objects) {
                object.update(delta);
            }
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            for (IObject object : objects) {
                object.render(delta);
            }

            glfwSwapBuffers(handle); // swap the color buffers
            delta = currentFrameTime - lastFrameTime;

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }



}
