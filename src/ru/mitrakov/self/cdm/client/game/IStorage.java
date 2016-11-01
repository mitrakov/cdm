package ru.mitrakov.self.cdm.client.game;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author Tommy
 */
public interface IStorage {
    public void setEnemySid(int sid);
    public int getEnemySid();
    
    public void setToken(String token);
    public String getToken();
    
    public void setUnitNames(Collection<String> names);
    public List<String> getUnitNames();
    
    public void setCaptainId(int id);
    public int getCaptainId();
    
    public void setCurUserUnit(int unitId);
    int getCurUserUnit();
}
