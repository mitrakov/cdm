package ru.mitrakov.self.cdm.client.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Tommy
 */
public class Storage implements IStorage {
    private int enemySid = 0;
    private int captainId = 0;
    private int curUserUnit = 0;
    private String token = "";
    private List<String> unitNames = new ArrayList<>();

    @Override
    public void setEnemySid(int sid) {
        enemySid = sid;
    }

    @Override
    public int getEnemySid() {
        return enemySid;
    }    

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setUnitNames(Collection<String> names) {
        unitNames.clear();
        unitNames.addAll(names);
    }

    @Override
    public List<String> getUnitNames() {
        return unitNames;
    }

    @Override
    public void setCaptainId(int id) {
        captainId = id;
    }

    @Override
    public int getCaptainId() {
        return captainId;
    }

    @Override
    public void setCurUserUnit(int unitId) {
        curUserUnit = unitId;
    }

    @Override
    public int getCurUserUnit() {
        return curUserUnit;
    }
}
