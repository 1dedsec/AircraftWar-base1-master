package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.*;

import java.util.List;

/**
 * @author asus
 */
public interface Strategy {
    List<BaseBullet> doShootOperation(AbstractAircraft aircraft);
}
