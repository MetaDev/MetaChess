package meta;

import view.MetaView;
import control.MetaLoop;
import editor.BoardEditor;
import editor.GUIEditor;
import editor.KeyMapEditor;
import editor.PieceEditor;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLUtil;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_STICKY_KEYS;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.system.MemoryUtil.NULL;

public class MetaWindow {

    private static MetaWindow instance;

    private static void setNatives() {
        String sysArch = System.getProperty("os.arch");
        String sysArchFolder = "x" + sysArch.substring(sysArch.length() - 2, sysArch.length());
        Path JGLLib = Paths.get("MetaChessApp", "lib", "lwjgl", "native");
        switch (LWJGLUtil.getPlatform()) {
            case WINDOWS: {
                JGLLib = Paths.get(JGLLib.toAbsolutePath().toString(), "windows", sysArchFolder);
            }
            break;

            case LINUX: {
                JGLLib = Paths.get(JGLLib.toAbsolutePath().toString(), "linux", sysArchFolder);
            }
            break;

            case MACOSX: {
                JGLLib = Paths.get(JGLLib.toAbsolutePath().toString(), "macosx", sysArchFolder);
            }
            break;
        }
        System.setProperty("java.library.path", JGLLib.toAbsolutePath().toString());
    }
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWWindowSizeCallback windowSizeCallback;
    // The window handle
    private long window;

    public static long getWindow() {
        return instance.window;
    }

    public void start() {
        createMetaBoard();
        try {
            //initialise window
            initWindow();
            //init render
            initRender();
            //start gameloop
            startLoop();
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
            windowSizeCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
    }

    public static void main(String[] args) {
        setNatives();
        instance = new MetaWindow();
        instance.start();
    }

    // Initialize here
    private void initWindow() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GL11.GL_TRUE) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        //opengl version 32
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
//        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        int WIDTH = MetaConfig.getTileSize();
        int HEIGHT = MetaConfig.getTileSize();

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        //pass key to Meta
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                //pass input to correct player model
                switch (action) {
                    case GLFW_PRESS:
                        MetaConfig.getBoardModel().getInputPlayer().setKeyState(key, 1);
                        break;

                    case GLFW_RELEASE:
                        MetaConfig.getBoardModel().getInputPlayer().setKeyState(key, -1);
                        break;

                }

                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
                }
            }
        });
        
        //set resize callback
        glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {

            @Override
            public void invoke(long window, int width, int height) {
                MetaView.setWindowSize(height, width);
            }
        });
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        //set inpu mode
        glfwSetInputMode(window, GLFW_STICKY_KEYS, GL_TRUE);

    }

    private static void createMetaBoard() {

        // create and save all initial constants
        MetaConfig.initConstants();

        // initialize editors
        BoardEditor.init();

        PieceEditor.init();

        KeyMapEditor.init();
        // the GUI is initialized the last
        GUIEditor.init();

        // EnemyBuilder enemyBuilder = new EnemyBuilder();
        // enemyBuilder.assemble(worldBuilder);
    }

    // Update logic and control here
    private static void update() {
        MetaLoop.update();
    }

    private void initRender() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();

        // Set the clear color
        glClearColor(0f, 0.0f, 0.0f, 0.0f);
    }

    public void startLoop() {
        long sleepTime = 1000L / 60L;
        while (glfwWindowShouldClose(window) == GL_FALSE) {
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            //update game state
            update();
            //render game based on updated state
            render();
            //sleep
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(MetaWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void render() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        //refresh view
        MetaView.refresh();

        glfwSwapBuffers(window); // swap the color buffers

    }

}
