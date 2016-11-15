package ru.mitrakov.self.cdm.client.engine;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.mitrakov.self.cdm.client.TrixCamera;
import ru.mitrakov.self.cdm.client.json.commands.cmd.Login;
import ru.mitrakov.self.cdm.client.networking.INetwork;

/**
 * test
 * @author normenhansen
 */
public class Engine extends SimpleApplication {
    
    protected Nifty nifty;
    protected TrixCamera trixCam;
    protected ScreenController[] controllers;
    protected EngineInputController inputController;
    
    private final INetwork network;

    public Engine(INetwork network) {
        assert network != null;
        this.network = network;
    }

    @Override
    public void simpleInitApp() {
        // creating Nifty Gui
        // @mitrakov: "new NiftyJmeDisplay" may produce EventServiceExistsException due to a bug in Nifty GUI. see https://hub.jmonkeyengine.org/t/nifty-and-appstates/25066
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/gui/interface.xml", "empty", controllers);

        // attaching Nifty Gui to jMonkey Engine
        guiViewPort.addProcessor(niftyDisplay);
        
        // unregister default FlyCamera and register our own Camera Input Controller
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        trixCam = new TrixCamera(cam, inputManager);
        trixCam.register();
        
        // we cannot call "init()" right inside the Model because "inputManager" 
        // is not been created yet. Therefore we call "init()" here
        if (inputController != null)
            inputController.init();
        
        // creating light objects
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(0, -1, 0));
        sun.setColor(ColorRGBA.White.mult(1.5f));
        rootNode.addLight(sun);
//        AmbientLight light = new AmbientLight();
//        light.setColor(ColorRGBA.White.mult(0.7f));
//        rootNode.addLight(light);
        
        // temp
        Spatial tree = assetManager.loadModel("Models/tree.j3o");
        rootNode.attachChild(tree);
        
        // show landing page
        hold();
        String login = settings.getString("login");
        String password = settings.getString("password");
        if (login != null && password != null) {
            network.send(new Login(login, password));   // landing page will be shown by callback
        } else nifty.gotoScreen("landing");
    }

    @Override
    public void simpleUpdate(float tpf) {}

    @Override
    public void simpleRender(RenderManager rm) {}
    
    public void hold() {
        trixCam.disable();
        if (inputManager != null)                       // sometimes inputManager == NULL (I don't know exactly why)
            inputManager.setCursorVisible(true);
    }
    
    public void resume() {
        trixCam.enable();
        if (inputManager != null)                       // sometimes inputManager == NULL (I don't know exactly why)
            inputManager.setCursorVisible(false);
    }
    
    public void setScreenControllers(ScreenController ... controllers) {
        this.controllers = controllers;
    }
    
    public void setInputController(EngineInputController inputController) {
        this.inputController = inputController;
    }
    
    public void saveLoginPassword(String login, String password) {
        try {
            settings.putString("login", login);
            settings.putString("password", password);
            settings.save("ru.mitrakov.self.cdm.client");
        } catch (Exception ex) { Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);}
    }
    
    // GENERATED CODE
    public Nifty getNifty() {
        return nifty;
    }

    public TrixCamera getTrixCam() {
        return trixCam;
    }
}
