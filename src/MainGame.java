import java.io.UnsupportedEncodingException;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import json.JSONException;
import json.JSONObject;
import json.JSONTokener;

public class MainGame extends MIDlet {
    public static Display display;
    public static MIDlet instance;

    public MainGame() {
        instance = this;
        display = Display.getDisplay(this);
        display.setCurrent(GameCanvas.getInstance());
        System.out.println("===========  init MainGame");
    }

    protected void startApp() {
        System.out.println("===========  startApp");
    }

    protected void pauseApp() {
        System.out.println("===========  pauseApp");
    }

    protected void destroyApp(boolean arg0) {
        System.out.println("===========  destroyApp");
        System.out.println("===========  destroyApp end");
    }
}

