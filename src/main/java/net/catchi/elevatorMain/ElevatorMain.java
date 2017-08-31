package net.catchi.elevatorMain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.catchi.configuration.*;
import net.catchi.elevator.*;
import net.catchi.elevator.implementation.StepAction;
import net.catchi.users.ElevatorUserGeneration;

/**
 * Created by schwartz on 5/1/17.
 */
public class ElevatorMain {
    public static void main(String[] args)
    {
        System.out.println("Starting Up");
        GameClock clock = new GameClock(100);

        BuildingConfiguration buildingConfiguration = new BuildingConfiguration("Devonshire Place",
            -1,
            8,
            13);

        ElevatorConfiguration elevatorConfiguration = new ElevatorConfiguration("Elevator 1",
            4000);

        List<ElevatorConfiguration> elevatorConfigurations = new LinkedList<ElevatorConfiguration>();
        elevatorConfigurations.add(elevatorConfiguration);


        ElevatorBankConfiguration bankConfiguration = new ElevatorBankConfiguration("Main Elevators",
            buildingConfiguration,
            elevatorConfigurations);

        Elevator firstElevator = new Elevator(elevatorConfiguration,
            buildingConfiguration);

        List<Elevator> elevators = new ArrayList<Elevator>();
        elevators.add(firstElevator);

        ElevatorBank bank = new ElevatorBank(buildingConfiguration,
            elevators,
            clock);

        ElevatorUserGeneration userGenerator = new ElevatorUserGeneration(5,
            buildingConfiguration.minFloor,
            buildingConfiguration.maxFloor,
            bank);

        StepAction nextAction = new StepAction("SimpleAction") {
            @Override
            public void Execute(int stepNumber){
                userGenerator.registerUser(stepNumber);
            }
        };


        clock.registerStepAction(nextAction);
        clock.run();

        System.out.println( "Successfully executed run.  Shutting down");

    }
}
