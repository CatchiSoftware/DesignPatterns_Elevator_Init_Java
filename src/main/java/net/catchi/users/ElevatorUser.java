package net.catchi.users;

import java.util.LinkedList;
import java.util.List;

import net.catchi.elevator.*;
import net.catchi.elevator.implementation.StepAction;
import net.catchi.states.*;

public class ElevatorUser
{
    public ElevatorUser(int _userID,
                 int _sourceFloor,
                 int _targetFloor)
    {
        userID = _userID;
        sourceFloor = _sourceFloor;
        targetFloor = _targetFloor;
        tripStatus = new TripStatus(userID, sourceFloor, targetFloor);

        registeredElevatorBank = null;
         myElevator = null;
         tripStatus = new TripStatus(_userID, _sourceFloor, _targetFloor);
         registrationTime = -1;
         pickupTime = -1;
            dropoffTime = -1;

    }

    public int userID;
    public int sourceFloor;
    public int targetFloor;

    public TripStatus tripStatus;
    public void registerWithBank(ElevatorBank elevatorBank)
    {
        String name = "User" + Integer.toString((userID));

        m_registrationToken = elevatorBank.gameClock.registerStepAction(
                new StepAction(name) {
                    @Override
                    public void Execute(int stepNumber) {
                        checkForElevatorUpdates(stepNumber);
                    }
                }
        );

        elevatorBank.registerPickupRequest(sourceFloor, targetFloor > sourceFloor);
        registeredElevatorBank = elevatorBank;
        tripStatus.wasRegistered = true;
        tripStatus.registrationTime = elevatorBank.gameClock.currentStep();
    }

    void checkForElevatorUpdates(int timeOffset)
    {
        if (isTripComplete() || registeredElevatorBank == null)
        {
            return;
        }

        int targetFloorHeight = targetFloor * registeredElevatorBank.buildingConfiguration.heightPerFloor;
        int sourceFloorHeight = sourceFloor * registeredElevatorBank.buildingConfiguration.heightPerFloor;

        if (myElevator!= null) {
            ElevatorState state = myElevator.State();
            if(state.door.IsOpen &&
                    state.height == targetFloorHeight)
            {
                dropoffTime = timeOffset;
                if (m_registrationToken != "")
                {
                    registeredElevatorBank.
                            gameClock.unregisterStepAction(m_registrationToken);
                }

                tripStatus.wasDroppedOff = true;
                tripStatus.timeToDropoff = timeOffset - tripStatus.registrationTime;
            }
        }
        else
        {
            Elevator foundElevator = null;
            for(Elevator elevator:
                registeredElevatorBank.elevators)
            {
                ElevatorState state = elevator.State();
                if(state.height == sourceFloorHeight &&
                        state.door.IsOpen &&
                        (state.overallDirection.isStopped ||
                                state.overallDirection.isAscending ==
                                        (targetFloor > sourceFloor)))
                {
                    foundElevator = elevator;
                    break;
                }
            }

            if (foundElevator != null)
            {
                myElevator = foundElevator;
                pickupTime = timeOffset;
                myElevator.registerRequest(new ElevatorRequest(targetFloor,
                        targetFloor >
                                sourceFloor,
                        true));
                tripStatus.wasPickedUp = true;
                tripStatus.timeToPickup = timeOffset - tripStatus.registrationTime;
            }
        }

    }

    boolean isTripComplete()
    {
        return dropoffTime >= 0;
    }

    ElevatorBank registeredElevatorBank;
    Elevator myElevator;
    int registrationTime;
    int pickupTime;
    int dropoffTime;

    static ElevatorUser createUser(int sourceFloor,int targetFloor) {
        int index = allUsers.size() + 1;
        ElevatorUser toReturn = new ElevatorUser(index, sourceFloor, targetFloor);
        allUsers.add(toReturn);
        return toReturn;

    }

    private String m_registrationToken;
    public static List<ElevatorUser > allUsers = new LinkedList<ElevatorUser>();
};


