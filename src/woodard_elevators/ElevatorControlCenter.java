/*
 * This was written based on the directions given not based on what is known
 *  about elevator operations in real world scenarios, so this was coded based
 *  on my understanding of the requirements as given.
 *
 */
package woodard_elevators;

import Enums.FloorRelationToElevator;
import Enums.ElevatorDirection;
import Enums.ElevatorStatus;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author Robert "Chris" Woodard
 */
public class ElevatorControlCenter {
    // This will create the list of elevators.  It will allow the number of
    //  of elevators to change based on the amount needed but only when the
    //  application starts.
    private final ArrayList<Elevator> elevators;
    private ArrayList<Elevator> tempElevators;
    
    // initialize elevator control center with the numbe of floors and elevators
    public ElevatorControlCenter(int maxElevators, int maxFloors){
        
        // initialize building elevator list
        elevators = new ArrayList<>();
        for (int i = 1; i <= maxElevators; i++){
            elevators.add(new Elevator("ev" + i, maxFloors));
        }
    }
    
    // Creates elevators based on total elevators in the system.
    public ArrayList<Elevator> getElevators(){
        return elevators;
    }
    
    // used at floor level (external control) to call the elevator
    public void callElevator(int floor){
        Elevator elevator;
        int index = getElevatorIndex();
        elevator = elevators.get(index);
        // Calls method to randomly select an elevator and handles a situation
        //  where all are in maintenance mode.
        if (index != -1){
            // test to see if the elevator is on the current floor.
            if (floor == elevator.getCurrentFloor()){
                elevator.addTotalRequests();
                System.out.println("Please enter the elevator.");
            } else {
                // test to see if there are floor requests already. If not, send
                //  to the requested floor.
                if (elevator.nextFloors.size() == 0){
                    elevator.addFloorRequest(floor);
                    elevator.addTotalRequests();
                    moveCallElevator(elevator, floor);
                } else {
                    elevator.addTotalRequests();
                    moveCallElevator(elevator, floor);
                }
            }            
        } else{
            System.out.println("Currently all elevators are having maintenance "
                    + "performed.  Please use the stairs.");
        }
    }
    
    // sets an elevator to maitenance mode and moves the elevators queue to an
    //  elevator that is functioning.
    public void setStatusMaintenance(int index){
        ElevatorStatus status = ElevatorStatus.Maintenance;
        Elevator elevatorOld = elevators.get(index);
        elevatorOld.setStatus(status);        
        // precheck to make sure we have elevators available
        if (getElevatorIndex() != -1){
            // loop through all requests and move to another elevator
            for (int i = 0; i < elevatorOld.nextFloors.size(); i++){
                requestFloor(getElevatorIndex(), elevatorOld.nextFloor());
                elevatorOld.removeFloorVisited();
            } 
        } else {
            System.out.println("Currently all elevators are having maintenance "
                + "performed.  Please use the stairs.");
        }
    }
    
    // this is used by callElevator above to get further information to pass on
    //  to the actual algorithm (moveElevator) to move the elevator.
    private void moveCallElevator(Elevator elevator, int requestedFloor){
        int nbrFloors = elevator.nbrFloorsToMove(elevator.nextFloor());
        ElevatorDirection direction = convertElevatorDirection(elevator, requestedFloor);
        moveElevator(nbrFloors, requestedFloor, direction, elevator);
    }
    
    // used inside the elevator (internal control) to select floors.
    public void requestFloor(int currentElevator, int floor){
        if (elevators.get(currentElevator).status == ElevatorStatus.Maintenance) {
            System.out.println("Sorry, you must use another elevator.");
        } else {
        elevators.get(currentElevator).addFloorRequest(floor);
        elevators.get(currentElevator).addTotalRequests();            
        }
    }
    
    // Appropriately moves the elevator based on the number of floors it must be
    //  moved.
    private void moveElevator(int totalFloors, int requestedFloor, 
            ElevatorDirection direction, Elevator elevator){
        for (int f = 0; f < totalFloors; f++){
            switch (direction){
                case Up:
                    elevator.floorUp();
                    // Reports when elevator has reached a floor that has been
                    //  requested.
                    if (elevator.getCurrentFloor() == requestedFloor){
                        System.out.println(elevator.elevatorID + " is now on floor " 
                                + elevator.getCurrentFloor() + ".");
                        elevator.removeFloorVisited();
                    }
                    break;
                case Down:
                    elevator.floorDown();
                    // Reports when elevator has reached a floor that has been
                    //  requested.
                    if (elevator.getCurrentFloor() == requestedFloor){
                        System.out.println(elevator.elevatorID + " is now on floor " 
                                + elevator.getCurrentFloor() + ".");
                        elevator.removeFloorVisited();
                    }
                    break;
            }
        }
    }
    
    // gives us the position of the floor to the elevator so we know which way
    //  to move the elevator.
    private ElevatorDirection convertElevatorDirection(Elevator elevator, int floor){
        FloorRelationToElevator directionToFloor = elevator.getFloorToElevator(floor);
        switch (directionToFloor){
            case Above:
                return ElevatorDirection.Up;
            case Below:
                return ElevatorDirection.Down;
            case Same:
                return ElevatorDirection.Standing;
        }
        return ElevatorDirection.Standing;
    }

    // this gives us a random elevator index
    private int getElevatorIndex(){
        int index;
        // create a temp elevator ArrayList to reshuffle because we do not want
        //  to change the order of the original
        tempElevators = new ArrayList<>();
        // clone the original list of elevators
        tempElevators = (ArrayList<Elevator>)elevators.clone();
        Collections.shuffle(tempElevators);
        // loop through and find an elevator that is not in Maintenance mode.  If
        //  none available, report -1 to tell the call method that there is none
        //  available.
        for (Elevator tempE : tempElevators){
            //index = randomGenerator.nextInt(elevators.size());
            if(tempE.status != ElevatorStatus.Maintenance){
                for (Elevator e : elevators){
                    if (e.elevatorID == null ? tempE.elevatorID == null : e.elevatorID.equals(tempE.elevatorID)){
                        index = elevators.indexOf(e);
                        return index;
                    }
                }
            return -1;
            }
        }
        return -1;
    }
}
