package net.catchi.elevator;

import net.catchi.states.*;
import java.util.Vector;

/**
 * Created by schwartz on 5/1/17.
 */
public class ElevatorBankFacade
{
    public ElevatorBankFacade(ElevatorBank _bank)
    {
        elevatorBank = _bank;
    }


    public int getNumElevators()
    {
        return elevatorBank.elevators.size();
    }

    public ElevatorState[] getElevatorStates()
    {
        ElevatorState[] toReturn = new ElevatorState[getNumElevators()];
        int index = 0;
        for(Elevator elevator:elevatorBank.elevators)
        {
            toReturn[index] = elevator.State();
            index += 1;
        }

        return  toReturn;
    }

    public void registerPickupRequest(int floor, boolean isAscending) {
        elevatorBank.registerPickupRequest(floor, isAscending);
    }

    void registerDropoffRequest(String agentName,
                                String elevatorName,
                                int destinationFloor)
    {
        for(Elevator elevator:elevatorBank.elevators)
        {
            if(elevator.buildingConfiguration.name == elevatorName)
            {
                int currentHeight = elevator.State().height;
                int desiredHeight = elevator.FloorHeight(destinationFloor);
                elevator.registerRequest(
                        new ElevatorRequest(
                                destinationFloor,
                                desiredHeight >= currentHeight));
            }
        }
    }

    private ElevatorBank elevatorBank;
};
