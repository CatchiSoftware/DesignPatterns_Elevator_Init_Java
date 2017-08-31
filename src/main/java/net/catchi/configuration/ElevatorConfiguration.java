package net.catchi.configuration;

/**
 * Created by schwartz on 5/1/17.
 */
public class ElevatorConfiguration {
    public ElevatorConfiguration(String _name,
                          int _maxCargoWeight) {
        name = _name;
        maxCargoWeight = _maxCargoWeight;
        stoppingDistanceWhenMoving = 4;
        distancePerStep = 8;
    }

    public String name;
    public int maxCargoWeight;
    public int stoppingDistanceWhenMoving;
    public int distancePerStep;
}
