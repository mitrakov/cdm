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


/**
 * Main Class
 * @author Tommy
 */
public final class Model {
    public static IStorage mStorage;   // see note #1
    public static INetwork mNetwork;   // see note #1
    
    public static void main(String[] args) {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true); // turn asserts on
        new Model().start();
    }
    
    private void start() {
        // creating objects
        IStorage storage = new Storage();
        INetwork network = new Network();
        
        // creating jMonkey Engine
        Engine engine = new Engine(network);
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
}
