package com.roervik.tdt4100.gameproject.core.io.input;

import com.roervik.tdt4100.gameproject.core.gfx.Window;

public class Controller {
    private static Input input = new Input();
    public static void initInputController(final Window window) {
        input.init(window);
    }

    public static Input getInput() {
        return input;
    }
}
