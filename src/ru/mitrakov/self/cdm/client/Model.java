package ru.mitrakov.self.cdm.client;

import ru.mitrakov.self.cdm.client.gui.*;
import ru.mitrakov.self.cdm.client.game.*;
import ru.mitrakov.self.cdm.client.engine.*;
import ru.mitrakov.self.cdm.client.handlers.*;
import ru.mitrakov.self.cdm.client.networking.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.system.AppSettings;
import java.util.prefs.BackingStoreException;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.IOException;
import java.net.*;


/**
 * Main Class
 * @author Tommy
 */
public final class Model {
    public static IStorage mStorage;   // see note #1
    public static INetwork mNetwork;   // see note #1
    public static boolean needRestart = true;
    
    private static Engine engine;
    
    public static void main(String[] args) throws InterruptedException {
        // turn asserts on
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        // convert the app into a singleton process
        registerInstance();
        // check whether the app should run in background
        if (args.length == 1 && args[0].equals("background"))
            needRestart = false;
        // infinite loop to make the app restartable
        while (true) {
            if (needRestart) {
                needRestart = false;
                if (engine == null || !engine.getContext().isCreated())
                    new Model().start();
            } else Thread.sleep(100);
        }
    }
    
    private void start() {
        // creating objects
        IStorage storage = new Storage();
        INetwork network = new Network();
        
        // creating jMonkey Engine
        engine = new Engine(network);
        engine.setPauseOnLostFocus(false);
        engine.setShowSettings(false);  // bug in jMonkey Engine! if "ShowSettings" is set to true, it inexplicably loads incorrect settings
        
        // setting up settings
        AppSettings settings = new AppSettings(true);
        try {
            settings.load("ru.mitrakov.self.cdm.client");
        } catch (BackingStoreException ex) {Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);}
        settings.setTitle("Coup de main (v.0.1.0)");
        settings.setResolution(800, 600);
        engine.setSettings(settings);
        
        // creating objects
        IGui gui = new Gui(engine, storage);        
        IBattleManager battleManager = new BattleManager(engine);
        EngineInputController inputController = new EngineInputController(engine, gui, storage, network, battleManager);
        
        // Network handlers
        Handler firstHandler = new FirstHandler(null, storage);
        Handler userHandler = new UserHandler(firstHandler, network, gui, storage);
        Handler battleHandler = new BattleHandler(userHandler, gui, battleManager);
        
        // Nifty Gui Screen Controllers
        ScreenController sc1 = new GuiLandingController(gui);
        ScreenController sc2 = new GuiSignInController(gui, network, engine);
        ScreenController sc3 = new GuiSignUpController(gui, network, engine);
        ScreenController sc4 = new GuiMainController(gui, network, storage, engine);
        ScreenController sc5 = new GuiSettingsController(gui, engine, storage);
        ScreenController sc6 = new GuiInviteController(gui, storage, network);
        ScreenController sc7 = new GuiWeaponController(gui, battleManager);
        
        // @mitrakov: note #1
        // jMonkey trouble. I don't find the way to create "Popup" Controllers and 
        // to inject 'em into the Nifty (in contrast to ScreenController, that could 
        // be easily injected). Therefore all Controllers have default constructors
        // and access such objects as Storage and Network through global static variables
        mStorage = storage;
        mNetwork = network;
        
        // set up objects
        network.setHandler(firstHandler);
        network.start();
        
        // start jMonkey Engine!
        engine.setScreenControllers(sc1, sc2, sc3, sc4, sc5, sc6, sc7);
        engine.setInputController(inputController);
        engine.start();
    }
    
    private static void registerInstance() {
        // creating new thread because accept() is a blocking operation
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // start listening on a loopback socket (if no exceptions thrown,
                    // then we run a first instance of the app
                    ServerSocket serverSocket = new ServerSocket(37686, 10, InetAddress.getByAddress(new byte[] {127,0,0,1}));
                    while (true) {
                        try (Socket sock = serverSocket.accept()) {
                            // this code would run only if the user is attempting
                            // to start another instance of the app;
                            // it usually means that the user wants
                            // to switch the app from background to normal mode;
                            // so we just restart itself (whilst the other instance
                            // will be closed automatically)
                            if (sock.getInetAddress().isLoopbackAddress())
                                needRestart = true;
                        }
                    }
                } catch (IOException ignored) {
                    // failed to seize socket! It means that the user is attempting
                    // to start another instance; so we notify the first instance
                    // about this (causing it to restart) and shutdown
                    try {
                        System.err.println("Another instance is already running");
                        new Socket("127.0.0.1", 37686).close();
                        System.exit(0);
                    } catch (Exception ignore) {}
                }
            }
        });
        th.setDaemon(true);
        th.start();
    } 
}
