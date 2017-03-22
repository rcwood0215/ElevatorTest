/*
Author: Chris Woodard
Date: 1/6/2016
Purpose: This simple program was developed to sybolize a two elevator system.
 */
package woodard_elevators;

public class Woodard_Elevators {

     public static void main(String[] args) {
        final int maxElevators = 2;  // constant to set number of elevators in bldg
        final int maxFloors = 10;  // constant to set number of floors in bldg
        final int maxElevators2 = 10; // for 2nd control center
        final int maxFloors2 = 15;  // for 2nd control center
        ElevatorControlCenter ecc1;
        ElevatorControlCenter ecc2;

        // testing with 2 elevators and 10 floors
        ecc1 = new ElevatorControlCenter(maxElevators, maxFloors);
        //floor = new Floor(2);
        
        /* using a stream example to get ID
        ecc1.getElevators().stream().forEach((elevator) -> {
            System.out.println(elevator.elevatorID);
         });
        */
       
        ecc1.callElevator(10);
        ecc1.callElevator(3);
        ecc1.callElevator(2);
        ecc1.callElevator(8);
        ecc1.requestFloor(1, 5);
        ecc1.requestFloor(0, 3);
        ecc1.callElevator(6);
        ecc1.setStatusMaintenance(0);
        for (int i = 0; i < ecc1.getElevators().size(); i++){
            System.out.println(ecc1.getElevators().get(i).elevatorID + 
                    " status " + ecc1.getElevators().get(i).status);
        }
        
        ecc1.callElevator(8);
        ecc1.requestFloor(1, 5);
        ecc1.requestFloor(0, 3);
        ecc1.callElevator(6);

        ecc1.getElevators().stream().forEach((elevator) -> {
            System.out.println(elevator.elevatorID + "'s current floor is " +
                    elevator.getCurrentFloor());});
        ecc1.getElevators().stream().forEach((elevator) -> {
            System.out.println(elevator.elevatorID + "'s next floor is " +
                    elevator.nextFloor());});
        ecc1.getElevators().stream().forEach((elevator) -> {
            System.out.println(elevator.elevatorID + "'s requested floors list: " +
                    elevator.getFloors());});

        
        for (int i = 0; i < ecc1.getElevators().size(); i++){
            System.out.println(ecc1.getElevators().get(i).elevatorID + 
                    " status " + ecc1.getElevators().get(i).status);
        }
        
        ecc1.callElevator(9);
        
        ecc1.getElevators().stream().forEach((elevator) -> {
            System.out.println("Elevator " + elevator.elevatorID +
            "'s total lifetime floors is " + elevator.getTotalFloors() +
                    " total requests " + elevator.getTotalRequests());});
        
        // works for N floors and N elevators.
        
        System.out.println("*********Test for multiple many elveators*******");
        ecc2 = new ElevatorControlCenter(maxElevators2, maxFloors2);
                
        ecc2.callElevator(10);
        ecc2.callElevator(3);
        ecc2.callElevator(2);
        ecc2.callElevator(8);
        ecc2.requestFloor(1, 5);
        ecc2.requestFloor(0, 3);
        ecc2.callElevator(6);
        
        ecc2.setStatusMaintenance(1);
        for (int i = 0; i < ecc2.getElevators().size(); i++){
            System.out.println(ecc2.getElevators().get(i).elevatorID + 
                    " status " + ecc2.getElevators().get(i).status);
        }
        
        ecc2.callElevator(8);
        ecc2.requestFloor(1, 5);
        ecc2.requestFloor(0, 3);
        ecc2.callElevator(6);

        ecc2.getElevators().stream().forEach((elevator) -> {
            System.out.println(elevator.elevatorID + "'s current floor is " +
                    elevator.getCurrentFloor());});
        ecc2.getElevators().stream().forEach((elevator) -> {
            System.out.println(elevator.elevatorID + "'s next floor is " +
                    elevator.nextFloor());});
        ecc2.getElevators().stream().forEach((elevator) -> {
            System.out.println(elevator.elevatorID + "'s requested floors list " +
                    elevator.getFloors());});

        
        for (int i = 0; i < ecc2.getElevators().size(); i++){
            System.out.println(ecc2.getElevators().get(i).elevatorID + 
                    " status " + ecc2.getElevators().get(i).status);
        }
        
        ecc2.callElevator(9);
        
        ecc2.getElevators().stream().forEach((elevator) -> {
            System.out.println("Elevator " + elevator.elevatorID +
            "'s total lifetime floors is " + elevator.getTotalFloors() +
                    " total requests " + elevator.getTotalRequests());});
        /*        
        ecc1.getElevators().get(0).addFloorRequest(10);
        ecc1.getElevators().get(1).addFloorRequest(2);
        
        ecc1.getElevators().stream().forEach((elevator) -> {
            System.out.println("Elevator " + elevator.elevatorID +
            "'s next floor is " + elevator.nextFloor());});
        
        ecc1.getElevators().get(1).removeFloorVisited();
        
        ecc1.getElevators().get(1).addFloorRequest(5);
        
        System.out.println("Elevator " + ecc1.getElevators().get(1).elevatorID +
                "'s next floor is " + ecc1.getElevators().get(1).nextFloor());

        for (int i = 0; i < ecc1.getElevators().size(); i++){
            System.out.println("Status from getStatus() " + 
                    ecc1.getElevators().get(i).getStatus());
        }        
        
        
        //floor.pushButton(FloorControlPanel.Down, floor.floorID);
        ecc1.callElevator(3);
        ecc1.callElevator(2);
        ecc1.getElevators().stream().forEach((elevator) -> {
            System.out.println("Elevator " + elevator.elevatorID +
            "'s current direction " + elevator.getDirection());});        
        ecc1.callElevator(8);
        ecc1.requestFloor(1, 5);
        ecc1.requestFloor(0, 3);
        ecc1.callElevator(6);
        for (int i = 0; i < ecc1.getElevators().size(); i++){
            System.out.println(ecc1.getElevators().get(i).elevatorID + " has the "
                    + "following floors in it's queue." + ecc1.getElevators().get(i).getFloors());
        }
        //floor.pushButton(FloorControlPanel.Up, floor.floorID);
        System.out.println(ecc1.getElevatorStatus("ev2"));

        ecc1.process();
        
        ecc1.getElevators().stream().forEach((elevator) -> {
            System.out.println("Elevator " + elevator.elevatorID +
            "'s total lifetime floors is " + elevator.getTotalFloors() +
                    " total requests " + elevator.getTotalRequests());});
        
        for (int i = 0; i < ecc1.getElevators().size(); i++){
            System.out.println(ecc1.getElevators().get(i).elevatorID + " has the "
                    + "following floors in it's queue." + ecc1.getElevators().get(i).getFloors());
        }
        */
     }
    
}
