package ru.mitrakov.self.cdm.client.engine;

import ru.mitrakov.self.cdm.client.game.*;
import com.jme3.math.*;
import com.jme3.scene.*;
import com.jme3.app.state.*;
import com.jme3.collision.*;
import com.jme3.renderer.Camera;
import com.jme3.app.Application;
import com.jme3.scene.shape.Box;
import com.jme3.material.Material;
import ru.mitrakov.self.cdm.client.TrixCamera;
import static ru.mitrakov.self.cdm.client.game.Cell.CellType.*;

/**
 *
 * @author Unit
 */
public class SceneState extends AbstractAppState {
    private static final float UNIT_SCALE = .3f;
    private static final float UNIT_CHOSEN = .4f;
    
    protected final IBattle battle;
    protected final Node node = new Node(getClass().toString());
    protected Engine engine;
    
    private transient Geometry curCell;
    private transient Spatial curUnit, activeUnit;
    private transient Vector3f desiredCamPos;
    private transient final Quaternion desiredCamDir = new Quaternion();
    
    public SceneState(IBattle battle) {
        assert battle != null;
        this.battle = battle;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        assert app != null;
        engine = (Engine)app;
        engine.getRootNode().attachChild(node);
        
        // create battlefield
        for (Cell cell : battle.getCells())
            node.attachChild(createBox(cell));
        
        // create units
        for (Unit unit : battle.getMyUnits())
            node.attachChild(createUnit(unit, battle.isAggressor()));
        for (Unit unit : battle.getEnemyUnits())
            node.attachChild(createUnit(unit, !battle.isAggressor()));
        
        // move camera to a start position (true = aggressor always starts a battle)
        moveCameraOnStartPosition(true);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        
        // updating units (TODO! rewrite!)
        for (Spatial s : node.getChildren()) {
            Unit unit = s.getUserData("unit");
            if (unit != null) {
                if (battle.getMyUnits().contains(unit) || battle.getEnemyUnits().contains(unit))
                    s.setLocalTranslation(unit.x, -.5f, unit.y);
                else {
                    s.setUserData("unit", null); // free reference to avoid leaks
                    node.detachChild(s);
                }
            }
        }

        // selecting units
        switch (battle.getMode()) {
            case MyTurnChooseUnit: {
                cleanupCell();
                Spatial s = getUnitSpatial();
                if (s != null) {
                    Unit unit = s.getUserData("unit");
                    if (unit != null) {
                        if (s != curUnit) {
                            cleanupUnit();
                            curUnit = s;
                            curUnit.setLocalScale(UNIT_CHOSEN);
                        }
                    }
                }
                break;
            }
            case MyTurn: {
                cleanupUnit();
                Geometry s = getCellSpatial();
                if (s != null) {
                    Cell cell = s.getUserData("cell");
                    if (cell != null) {
                        if (s != curCell) {
                            cleanupCell();
                            curCell = s;
                            curCell.getMaterial().setColor("Color", ColorRGBA.LightGray);
                        }
                    }
                }
                break;
            }
            case MyTurnReadyToFire: {
                cleanupUnit();
                cleanupCell();
                if (activeUnit != null) {
                    Quaternion qUnit = activeUnit.getLocalRotation();
                    Quaternion qCam = engine.getCamera().getRotation();
                    Quaternion q = new Quaternion(qUnit.getX(), qCam.getY(), qUnit.getZ(), qCam.getW());
                    activeUnit.setLocalRotation(q);
                }
                break;
            }
            default: {
                cleanupUnit();
                cleanupCell();
            }
        }
        
        //updating camera location
        if (desiredCamPos != null) {
            Camera camera = engine.getCamera(); assert camera != null;
            Vector3f camPos = camera.getLocation();
            if (camPos.distance(desiredCamPos) < 0.1) { // if camPos == desiredCamPos
                desiredCamPos = null;
            } else {
                camPos.interpolate(desiredCamPos, 0.1f);
                camera.setRotation(desiredCamDir);
                camera.onFrameChange();
            }
        }
    }
    
    

    @Override
    public void cleanup() {
        engine.getRootNode().detachChild(node);
        super.cleanup();
    }
    
    
    
    // PUBLIC METHODS
    public void moveCameraOnStartPosition(boolean curAggressorTurn) {
        TrixCamera trixCam = engine.getTrixCam(); assert trixCam != null;
        
        // set fly camera mode
        trixCam.setFlyMode();
        
        // set up the camera
        Vector3f start = curAggressorTurn ? new Vector3f(Battle.WIDTH/2, 8, Battle.HEIGHT + 5) : new Vector3f(Battle.WIDTH/2, 8, -5);
        if (curAggressorTurn)
             desiredCamPos = (new Vector3f(Battle.WIDTH/2, 8, Battle.HEIGHT + 5));
        else desiredCamPos = (new Vector3f(Battle.WIDTH/2, 8, -5));
        desiredCamDir.lookAt(new Vector3f(Battle.WIDTH/2, 0, Battle.HEIGHT/2).subtract(start), Vector3f.UNIT_Y);
    }
    
    public void moveCameraOnUnit(Unit unit, boolean isAggressor) {
        assert unit != null;
        TrixCamera trixCam = engine.getTrixCam(); assert trixCam != null;
        
        // set fly camera mode
        trixCam.setFlyMode();
        
        // set up the camera
        Vector3f start = new Vector3f(unit.x, 2.5f, unit.y + (isAggressor ? 4 : -4));
        desiredCamPos = (new Vector3f(unit.x, 2.5f, unit.y + (isAggressor ? 4 : -4)));
        desiredCamDir.lookAt(new Vector3f(unit.x, -.5f, unit.y).subtract(start), Vector3f.UNIT_Y);
    }
    
    public void moveCameraOnFirePosition(Unit unit, boolean isAggressor) {
        assert unit != null;
        TrixCamera trixCam = engine.getTrixCam(); assert trixCam != null;
        
        Vector3f unitXyz = new Vector3f(unit.x, 1.2f, unit.y);
        float dir = FastMath.HALF_PI * (isAggressor ? 1 : -1);
        trixCam.setMoveAroundMode(unitXyz, dir);
    }
    
    public Unit getUnit() {
        Spatial s = getUnitSpatial();
        if (s != null)
            return s.getUserData("unit");
        return null;
    }
    
    public Cell getCell() {
        Geometry g = getCellSpatial();
        if (g != null)
            return g.getUserData("cell");
        return null;
    }
    
    public int getCurrentDirection() {
        if (engine == null) return 0;
        Camera camera = engine.getCamera();
        
        float x = camera.getDirection().x;
        float y = camera.getDirection().z;
        float cos = x / FastMath.sqrt(x*x + y*y);
        float _angle = (float) Math.acos(cos);
        float angle = y > 0 ? 2 * FastMath.PI - _angle : _angle;
        int val = 1 * FastMath.PI/8 <= angle && angle <  3 * FastMath.PI / 8 ? 1 :
                  3 * FastMath.PI/8 <= angle && angle <  5 * FastMath.PI / 8 ? 2 :
                  5 * FastMath.PI/8 <= angle && angle <  7 * FastMath.PI / 8 ? 3 :
                  7 * FastMath.PI/8 <= angle && angle <  9 * FastMath.PI / 8 ? 4 :
                  9 * FastMath.PI/8 <= angle && angle < 11 * FastMath.PI / 8 ? 5 :
                 11 * FastMath.PI/8 <= angle && angle < 13 * FastMath.PI / 8 ? 6 :
                 13 * FastMath.PI/8 <= angle && angle < 15 * FastMath.PI / 8 ? 7 : 0;
        return val;
    }
    
    
    
    // PRIVATE/PROTECTED METHODS
    protected Spatial getUnitSpatial() {
        return getUnitSpatial(engine.getCamera().getLocation(), engine.getCamera().getDirection());
    }
    
    protected Spatial getUnitSpatial(Vector3f point, Vector3f dir) {
        Ray ray = new Ray(point, dir);
        CollisionResults results = new CollisionResults();
        node.collideWith(ray, results);
        for (CollisionResult result : results) {
            Node p = result.getGeometry().getParent();
            if (p != null) {
                Unit t = p.getUserData("unit");
                if (t != null && t.mine) return p;
            }
        }
        return null;
    }
    
    protected Geometry getCellSpatial() {
        return getCellSpatial(engine.getCamera().getLocation(), engine.getCamera().getDirection());
    }
    
    protected Geometry getCellSpatial(Vector3f point, Vector3f dir) {
        Ray ray = new Ray(point, dir);
        CollisionResults results = new CollisionResults();
        node.collideWith(ray, results);
        for (CollisionResult result : results) {
            Geometry p = result.getGeometry();
            if (p != null) {
                Cell c = p.getUserData("cell");
                if (c != null) return p;
            }
        }
        return null;
    }
    
    protected ColorRGBA getColor(Cell.CellType type) {
        switch(type) {
            case CellNone:   return new ColorRGBA(.86f, .78f, .86f, 1);
            case CellPlain:  return new ColorRGBA(.59f, .39f, .25f, 1);
            case CellSand:   return new ColorRGBA(.86f, .86f, .81f, 1);
            case CellWater:  return ColorRGBA.Cyan;
            case CellHill:   return new ColorRGBA(.81f, .88f, .84f, 1);
            case CellPit:    return new ColorRGBA(.78f, .78f, .81f, 1);
            case CellStone:  return new ColorRGBA(.59f, .39f, .25f, 1);
            case CellTree:   return new ColorRGBA(.59f, .39f, .25f, 1);
            case CellCorpse: return ColorRGBA.Black;
            case CellBridge: return ColorRGBA.Brown;
            case CellFog:    return new ColorRGBA(.92f, .92f, .92f, 1);
            default:         return new ColorRGBA(.86f, .78f, .86f, 1);
        }
    }
    
    protected Spatial createBox(Cell cell) {
        Box b = new Box(.5f, .5f, .5f);
        Geometry g = new Geometry("Box", b);
        g.setUserData("cell", cell);

        Material mat = new Material(engine.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        g.setMaterial(mat);
        g.setLocalTranslation(cell.idx % Battle.WIDTH, -1, cell.idx / Battle.WIDTH);
        mat.setColor("Color", getColor(cell.cellType));
        
        switch(cell.cellType) {
            case CellStone: 
                node.attachChild(createStone(cell.idx));
                break;
            case CellTree: 
                node.attachChild(createTree(cell.idx));
                break;
            default:
        }

        return g;
    }
    
    protected Spatial createUnit(Unit unit, boolean isAggressor) {
        Spatial g = engine.getAssetManager().loadModel("Models/tommy.j3o");
        g.setUserData("unit", unit);
        g.setLocalScale(UNIT_SCALE);
        g.setLocalTranslation(unit.x, -.5f, unit.y);
        if (isAggressor)
            g.rotate(0, FastMath.PI, 0);
        return g;
    }
    
    protected Spatial createTree(int idx) {
        Spatial g = engine.getAssetManager().loadModel("Models/tree.j3o");
        g.setLocalScale(0.4f);
        g.setLocalTranslation(idx % Battle.WIDTH, .2f, idx / Battle.WIDTH);
        return g;
    }
    
    protected Spatial createStone(int idx) {
        Spatial g = engine.getAssetManager().loadModel("Models/stone.j3o");
        g.setLocalScale(3f);
        g.rotate(0, FastMath.HALF_PI, FastMath.HALF_PI);
        g.setLocalTranslation(idx % Battle.WIDTH, -.1f, idx / Battle.WIDTH - .2f);
        return g;
    }
    
    private void cleanupUnit() {
        if (curUnit != null) {
            curUnit.setLocalScale(UNIT_SCALE);
            activeUnit = curUnit;
            curUnit = null;
        }
    }
    
    private void cleanupCell() {
        if (curCell != null) {
            Cell cell = curCell.getUserData("cell");
            if (cell != null)
                curCell.getMaterial().setColor("Color", getColor(cell.cellType));
            curCell = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("SceneState FINALIZED!!!");
        super.finalize();
    }
}
