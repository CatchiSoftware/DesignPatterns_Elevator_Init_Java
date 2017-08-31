package net.catchi.elevator;

import net.catchi.states.*;

/**
 * Created by schwartz on 5/1/17.
 */
public class ElevatorRequest {
    ElevatorRequest() {
    }

    public ElevatorRequest(int _floorNumber,
                    boolean _direction) {
        floorNumber = _floorNumber;
        direction = _direction;
        requestedInsideElevator = false;

    }

    public ElevatorRequest(int _floorNumber,
                    boolean _direction,
                    boolean _requestedInsideElevator) {
        floorNumber = _floorNumber;
        direction = _direction;
        requestedInsideElevator = _requestedInsideElevator;

    }


    public int floorNumber;
    public boolean direction;
    public boolean requestedInsideElevator;

    boolean compatibleDirection(ElevatorMovement elevatorMovement) {
        return elevatorMovement.isStopped ||
                requestedInsideElevator ||
                (elevatorMovement.isAscending == direction);
    }
}