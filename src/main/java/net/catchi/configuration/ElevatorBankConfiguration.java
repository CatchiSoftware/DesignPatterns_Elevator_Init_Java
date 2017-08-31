package net.catchi.configuration;

import java.util.List;

/**
 * Created by schwartz on 5/1/17.
 */
public class ElevatorBankConfiguration {
    public ElevatorBankConfiguration(String _name,
                              BuildingConfiguration _building,
                              List<ElevatorConfiguration> _elevators)
    {
        name =_name;
        building = _building;
        elevators = _elevators;
    }

    String name;
    BuildingConfiguration building;
    List<ElevatorConfiguration> elevators;
};

