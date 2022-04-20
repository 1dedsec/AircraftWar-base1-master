package edu.hitsz.factory.aircraft_factory;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * @author asus
 */
public interface AircraftFactory {
    AbstractAircraft createEnemyAircraft();
}