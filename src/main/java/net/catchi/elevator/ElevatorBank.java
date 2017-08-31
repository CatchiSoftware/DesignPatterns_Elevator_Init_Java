package net.catchi.elevator;

import java.util.LinkedList;
import java.util.List;
import  java.util.Random;

import net.catchi.configuration.*;
import net.catchi.elevator.implementation.ElevatorStrategy;

/**
 * Created by schwartz on 5/1/17.
 */
public class ElevatorBank
{
    public ElevatorBank(BuildingConfiguration _buildingConfiguration,
                 List<Elevator > _elevators,
                 GameClock _gameClock) {

        buildingConfiguration = _buildingConfiguration;
        elevators = _elevators;
        gameClock = _gameClock;

        for (Elevator elevator : elevators) {
            elevator.registerGameClock(gameClock);
        }
    }

    public BuildingConfiguration buildingConfiguration;
    public GameClock gameClock;
    public List<Elevator > elevators;

    public void registerPickupRequest(int floorNum, boolean direction)
    {
        ElevatorRequest toRegister = new ElevatorRequest(floorNum, direction);
        int numElevators = elevators.size();
        if(numElevators == 0)
            return;

        int offset = 0;
        if(numElevators > 1) {
            offset = m_random.nextInt() % numElevators;
        }
        Elevator elevator = elevators.get(offset);
        elevator.registerRequest(toRegister);
        m_openRequests.add(toRegister);

    }

    void tryShiftingOpenRequests(int unusedInt)
    {}

    private List<ElevatorRequest> m_openRequests = new LinkedList<ElevatorRequest>();

    private  Random m_random = new Random();
};


