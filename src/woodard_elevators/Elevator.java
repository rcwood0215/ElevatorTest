/*
Author: Chris Woodard
Date: 1/6/2016
Purpose: This class creates all the properties and functions of an elevator.
    It also will allow properties to be set by a program as it utilizes the
    class.
 */
package woodard_elevators;

import Enums.FloorRelationToElevator;
import Enums.ElevatorStatus;
import Enums.ElevatorDirection;
import java.util.Queue;
import java.util.LinkedList;

public class Elevator {
    private int curFloor;       // floor elevator is on
    private int totalFloors;    // elevator lifetime floors
    private int totalRequests;  // elevator lifetime requests
    Queue<Integer> nextFloors; // queue to hold requested destinations
    public final int maxFloors; // constant used to set max number floors
    public final String elevatorID;      // stores elevator ID
    ElevatorStatus status;
    
    // basic contructor for initializing basic variables to default when the
    //  elevator is created.
    protected Elevator(String id, int floors){
        elevatorID = id;
        maxFloors = floors;
        curFloor = 1;
        nextFloors = new LinkedList<>();
        status = ElevatorStatus.Standing;
    }
    
    // overloaded constructor to start elevator at floor other than 1st
    protected Elevator(String id, int floors, int startFloor){
        elevatorID = id;
        maxFloors = floors;
        curFloor = startFloor;
        nextFloors = new LinkedList<>();
        status = ElevatorStatus.Standing;
    }
    
    // allows the elevator to be set to maintenance mode
    protected void setStatus(ElevatorStatus newStatus){
        status = newStatus;
        curFloor = 1;
    }
    
    // adds to the total floors for this elevators lifetime.
    protected void addTotalFloors(){
        totalFloors++;
    }
    
    // adds to the total lifetime requests for this elevator.
    protected void addTotalRequests(){
        totalRequests++;
    }
    
    // allows us to get an elevators status directly
    protected ElevatorStatus getStatus(){
        if (status != ElevatorStatus.Maintenance){
            if (nextFloors.isEmpty()){
                status = ElevatorStatus.Standing;
            } else {
                status = ElevatorStatus.Moving;
            }
        }
        return status;
    }
    
    // holds the total floors traveled during this elevators "lifetime".
    protected int getTotalFloors(){
        return totalFloors;
    }
    
    // holds the total requests for this elevator over "lifetiem".
    protected int getTotalRequests(){
        return totalRequests;
    }
    
    // this will give us the next floor the elevator is headed to
    protected int nextFloor(){
        if (nextFloors.size() > 0){ // has a floor in the queue
            return nextFloors.peek();
        } else{  // does not have a floor in the queue
            return 0;
        }
    }
    
    // used to report all floors in the queue.
    protected Queue<Integer> getFloors(){
        return nextFloors;
    }
    
    // this gives us back the floor the elevator is currently on
    protected int getCurrentFloor(){
        return curFloor;
    }
    
    // this will move the elevator up a floor and add to overall floor count
    protected void floorUp(){
        curFloor++;
        addTotalFloors();
    }
    
    // this will move the elevator down a floor and add to overall floor count
    protected void floorDown(){
        curFloor--;
        addTotalFloors();
    }
        
    // this will add a floor to the queue when a button is pushed
    protected void addFloorRequest(int newFloor){
        nextFloors.add(newFloor);
    }
    
    // this will remove the last floor the elevator stopped at
    protected void removeFloorVisited(){
        nextFloors.remove();
    }
    
    protected ElevatorDirection getDirection(){
        if (nextFloors.size() > 0){
            if (curFloor < nextFloor()){
                return ElevatorDirection.Up;
            }
            else if (curFloor > nextFloor()){
                return ElevatorDirection.Down;
            }
        }
        return ElevatorDirection.Standing;
    }
    
    // takes current floor from the elevator or compares it to the requested floor.
    // this tells us where the floor is in relation to the elevator.
    protected FloorRelationToElevator getFloorToElevator(int floor){
        if (curFloor < floor){
            return FloorRelationToElevator.Above;
        }
        else if (curFloor > floor){
            return FloorRelationToElevator.Below;
        }
        else{
            return FloorRelationToElevator.Same;
        }
    }
    
    // looks at which way the elevator has to move and decides the number of
    //  floors to moved based on the direction.
    protected int nbrFloorsToMove(int floor){
        FloorRelationToElevator direction = getFloorToElevator(floor);
        switch (direction){
            case Above:
                return floor - this.curFloor;
            case Below:
                return this.curFloor - floor;
            default:
                return 0;
        }
    }
}
