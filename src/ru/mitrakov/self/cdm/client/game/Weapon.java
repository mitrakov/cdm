package ru.mitrakov.self.cdm.client.game;

import java.util.Objects;

/**
 *
 * @author Tommy
 */
public class Weapon {
    public static enum WeaponType {Skip, Knife, Revolver, Rifle, DoubleRifle, 
        ChainSaw, Rpg, Ball, Tnt, Feign, BarberWire, SalvoAttack, ReverseBall, 
        Mine, Steal, Trench, LongRangeRifle, FragmentationShell, SpinBall, HardBall, 
        Shotgun, ShotgunEx, WinOver, Bridge, Smg, AirBurst, FireBall, FlameThrower, 
        FlashBang, SmokeBomb, Apb, ComplexBall, Gas, MachineGun, MachineGunEx, 
        LogPillbox}
    
    public final WeaponType type;
    public int count;

    public Weapon(int weaponIdx, int count) {
        WeaponType[] types = WeaponType.values();
        assert 0 <= weaponIdx && weaponIdx < types.length;
        this.type = types[weaponIdx];
        this.count = count;
    }

    // GENERATED CODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + this.count;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Weapon other = (Weapon) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.count != other.count) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Weapon{" + "type=" + type + ", count=" + count + '}';
    }
}
