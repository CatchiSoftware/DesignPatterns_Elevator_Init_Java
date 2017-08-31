package net.catchi.elevator.implementation;

import java.util.LinkedList;
import java.util.List;

import net.catchi.configuration.*;
import net.catchi.elevator.ElevatorRequest;
import net.catchi.elevator.GameClock;
import net.catchi.states.*;
/**
 * Created by schwartz on 5/1/17.
 */
public class ElevatorDriver
{
    public ElevatorDriver(ElevatorConfiguration _elevator,
                   BuildingConfiguration _building)
    {
        elevator = _elevator;
        building = _building;

        m_state = new ElevatorState(
                0,
                DoorState.Open(),
                ElevatorMovement.Stopped(),
                ElevatorMovement.Stopped());

        m_action = new ElevatorAction(ElevatorAction.Opening, 0);

        m_stepAction = new StepAction("ElevatorDriver Action") {
            @Override
            public void Execute(int stepNumber) {
                proceedToNextState(stepNumber);
            }
        };
    }

    ElevatorConfiguration elevator;
    BuildingConfiguration building;

    
    public void registerGameClock(GameClock toRegister)
    {
        toRegister.registerStepAction(m_stepAction);
    }

    public ElevatorState  getState() {
        return m_state;
    }

    public List<ElevatorRequest>  getElevatorRequests() {
        return m_openRequests;
    }

    public void RegisterElevatorRequest(ElevatorRequest request)
    {
        m_openRequests.add(request);
    }

    boolean hasGoal()
    {
        return m_goals.size() > 0;
    }

    List<ElevatorRequest>    getGoals() {
        return m_goals;
    }

    public void addGoal(ElevatorRequest request)
    {
        m_goals.add(request);
    }

    void setGoals(List<ElevatorRequest> goals)
    {
        m_goals = goals;
    }

    // The 
    void proceedToNextState(int stepNumber)
    {
        try
        {
            System.out.println( "Elevator: "
                    + elevator.name

                    + " At height: "
                    + Integer.toString(m_state.height)
                    + " action: "
                    + m_action.actionName);

            if(m_action.actionName ==  ElevatorAction.Opening)
            {
                m_state.door = DoorState.Open();
                int floorNumber = m_state.height/building.heightPerFloor;

                m_goals.removeIf((ElevatorRequest request)->
                {
                    return request.floorNumber == floorNumber;
                });

                m_action.duration -= 1;
                if(m_action.duration <= 0)
                {
                    m_action = new ElevatorAction(ElevatorAction.Stopped, 2);
                }
                return;
            }
            if(m_action.actionName ==  ElevatorAction.Stopping)
            {
                ElevatorState nextState = m_state;
                nextState.movement = ElevatorMovement.Stopped();
                nextState.height = m_action.terminalHeight;
                m_state = nextState;
                m_action = new ElevatorAction(ElevatorAction.Opening,
                        m_state.height);
                return;
            }

            if(m_action.actionName ==  ElevatorAction.Move_Up)
            {
                HandleMovementUp(stepNumber);
                return;
            }

            if(m_action.actionName ==  ElevatorAction.Move_Down)
            {
                HandleMovementUp(stepNumber);
                return;
            }

            //Fallback: Act as if the elevator is stopped.
            ElevatorRequest toExecute = getNextGoal();
            if(toExecute != null)
            {
                int targetHeight = toExecute.floorNumber*building.heightPerFloor;
                if(targetHeight == m_state.height)
                {
                    m_action = new ElevatorAction(ElevatorAction.Opening, targetHeight);
                }
                else if(targetHeight > m_state.height)
                {
                    m_action = new ElevatorAction(ElevatorAction.Move_Up,
                            targetHeight);
                }
                else
                {
                    m_action = new ElevatorAction(ElevatorAction.Move_Down,
                            targetHeight);
                }
            }
        }
        catch(Exception exc)
        {
            System.out.println(exc.toString());
        }

    }

    public void OpenDoor(int floorNumber)
    {
        m_action = new ElevatorAction(ElevatorAction.Opening,
                floorNumber*building.heightPerFloor);
    }

    // Violates encapsulation:
    public void setState(ElevatorState state)
    {
        m_state = state;
    }


    private ElevatorRequest getNextGoal()
    {
        if(m_goals.size() > 0)
            return m_goals.get(0);

        return null;
    }


    ElevatorState m_state;

    // Requests that should be serviced should this elevator
    // pass by the referenced floors.
    List<ElevatorRequest>  m_openRequests = new LinkedList<ElevatorRequest>();

    // Ordered list of steps requests to be serviced 
    List<ElevatorRequest> m_goals = new LinkedList<ElevatorRequest>();

    void HandleMovementUp(int stepNumber)
    {
        if(!m_state.door.IsClosed)
        {
            m_state.door.IsClosed = true;
            return;
        }

        int nextHeight = m_state.height + elevator.distancePerStep;
        int maxHeight = building.maxFloor*building.heightPerFloor;
        if(m_action.terminalHeight > maxHeight)
            m_action.terminalHeight = maxHeight;

        if(nextHeight >= m_action.terminalHeight)
        {
            m_action = new ElevatorAction(ElevatorAction.Opening,
                    m_action.terminalHeight);

            m_state.height = m_action.terminalHeight;
            m_state.movement = ElevatorMovement.Stopped();
            return;
        }

        Integer nextFloor = 0;
        if(WillElevatorPassFloor(m_state.height,
                m_state.movement,
                elevator.distancePerStep,
                building.heightPerFloor,
                nextFloor))
        {
            for(ElevatorRequest request: m_openRequests)
            {
                if((request.floorNumber == nextFloor) &&
                        (request.requestedInsideElevator ||
                                request.direction))
                {
                    nextHeight = nextFloor*building.heightPerFloor;
                    m_action = new ElevatorAction(
                            ElevatorAction.Opening,
                            nextHeight);

                    m_state.height = nextHeight;
                    m_state.movement = ElevatorMovement.Stopped();

                    return;
                }
            }
        }

        m_state.height = nextHeight;
    }

    void HandleMovementDown(int stepNumber)
    {
        if(!m_state.door.IsClosed)
        {
            m_state.door.IsClosed = true;
            return;
        }

        int nextHeight = m_state.height - elevator.distancePerStep;
        int minHeight = building.minFloor*building.heightPerFloor;
        if(m_action.terminalHeight < minHeight)
            m_action.terminalHeight = minHeight;

        if(nextHeight <= m_action.terminalHeight)
        {
            m_action = new ElevatorAction(ElevatorAction.Opening,
                    m_action.terminalHeight);

            m_state.height = m_action.terminalHeight;
            m_state.movement = ElevatorMovement.Stopped();
            return;
        }

        Integer nextFloor = 0;
        if(WillElevatorPassFloor(m_state.height,
                m_state.movement,
                elevator.distancePerStep,
                building.heightPerFloor,
                nextFloor))
        {
            for(ElevatorRequest request: m_openRequests)
            {
                if((request.floorNumber == nextFloor) &&
                        (request.requestedInsideElevator ||
                                request.direction))
                {
                    nextHeight = nextFloor*building.heightPerFloor;
                    m_action = new ElevatorAction(
                            ElevatorAction.Opening,
                            nextHeight);

                    m_state.height = nextHeight;
                    m_state.movement = ElevatorMovement.Stopped();

                    return;
                }
            }
        }

        m_state.height = nextHeight;
    }

    public static boolean
    WillElevatorPassFloor(int currentHeight,
                          ElevatorMovement direction,
                          int stepLength,
                          int heightPerFloor,
                          Integer nextFloorNum)
    {
        int nextHeight = (direction.isAscending)?
                currentHeight + stepLength:
                currentHeight - stepLength;

        if(direction.isAscending)
        {
            if((currentHeight < 0) && (nextHeight >= 0))
            {
                nextFloorNum = 0;
                return true;
            }

            if((currentHeight / heightPerFloor) < (nextHeight/heightPerFloor))
            {
                nextFloorNum = nextHeight/heightPerFloor;
                return true;
            }

            return false;
        }

        if(direction.isDescending)
        {
            if((nextHeight <= 0) && (currentHeight > 0))
            {
                nextFloorNum = 0;
                return true;
            }

            if((nextHeight/heightPerFloor) < (currentHeight/heightPerFloor))
            {
                nextFloorNum = nextHeight/heightPerFloor;
                return true;
            }

            return false;
        }

        return false;
    }


    ElevatorAction m_action;
    StepAction m_stepAction;
    String m_registrationID;
}
