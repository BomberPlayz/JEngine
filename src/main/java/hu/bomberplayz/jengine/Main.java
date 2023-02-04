package hu.bomberplayz.jengine;

import com.google.common.eventbus.EventBus;
import hu.bomberplayz.jengine.components.IObject;
import hu.bomberplayz.jengine.components.Vector2d;
import hu.bomberplayz.jengine.components.Window;
import hu.bomberplayz.jengine.ui.Renderer;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.awt.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    // The window handle
    Window window;

    public static EventBus eventBus = new EventBus();



    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        IObject object = new IObject() {

            @Override
            public void render(float delta) {
                // draw a green quad
                Renderer.drawFilledRoundedRect(0, 0, 100, 100, 10, new Color(0, 255, 0));
            }

            @Override
            public void update(float delta) {

            }

            @Override
            public void destroy() {

            }

            @Override
            public void init() {
                // Set the projection matrix
                glMatrixMode(GL_PROJECTION);
                glLoadIdentity();
                Vector2d size = window.getSize();
                glOrtho(0, size.getX(), size.getY(), 0, 1, -1);
                glMatrixMode(GL_MODELVIEW);
            }


        };
        window.addObject(object);

        window.renderLoop();

        // Free the window callbacks and destroy the window
        window.destroy();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        window = new Window(300, 300);

        // Make the OpenGL context current
        window.takeContext();

        // Enable v-sync
        glfwSwapInterval(1);

        window.show();
    }





    public static void main(String[] args) {
        new Main().run();
    }
}
