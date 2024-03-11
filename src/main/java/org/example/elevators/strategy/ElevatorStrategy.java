package org.example.elevators.strategy;

import org.example.elevators.model.Direction;
import org.example.elevators.model.Elevator;
import org.example.elevators.model.Floor;

import java.util.*;

public class ElevatorStrategy {
    private final int minFloor;
    private final int maxFloor;
    private final int criticalNoOfWaitingPeople;
    Map<Integer, Query> queries;

    public ElevatorStrategy(int minFloor, int maxFloor, int criticalNoOfWaitingPeople) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.criticalNoOfWaitingPeople = criticalNoOfWaitingPeople;
    }

    public void run(List<Elevator> elevatorList, List<Floor> floorList) {
        List<Elevator> elevatorsWithoutTarget = new LinkedList<>();
        List<Elevator> elevatorsContainingPeople = new LinkedList<>();
        elevatorList.forEach(elevator -> {
            if (elevator.isNotEmpty())
                elevatorsContainingPeople.add(elevator);
            else
                elevatorsWithoutTarget.add(elevator);
        });

        PriorityQueue<Floor> criticalFloors = prepareQueries(floorList);
        for (Elevator elevator : elevatorsContainingPeople) {
            int i = elevator.getCurrentFloor();
            Direction direction = elevator.getDirection();
            while (true) {
                queries.get(i).untag(direction);
                i += direction.getValue();
                if (i == elevator.getTargetFloor()) {
                    queries.get(i).untagAll();
                    break;
                }
            }
        }
        if (elevatorsWithoutTarget.isEmpty())
            return;
        handleCritical(criticalFloors, elevatorsWithoutTarget);

        handleElevatorsWithoutTarget(elevatorsWithoutTarget);
    }

    private void handleElevatorsWithoutTarget(List<Elevator> elevatorsWithoutTarget) {
        for (Elevator elevator : elevatorsWithoutTarget) {
            int currentFloor = elevator.getCurrentFloor();
            int closestFloor = getFarthestFloorInSameDirection(currentFloor);
            Direction direction;

            if (closestFloor == -1) {
                closestFloor = getTheFarthestRequest(currentFloor);
                if (closestFloor == -1) {
                    return;
                }
                direction = closestFloor > currentFloor ? Direction.DOWN : Direction.UP;
            } else
                direction = closestFloor > currentFloor ? Direction.UP : Direction.DOWN;

            elevator.setTargetFloor(closestFloor);
            untagAllInDirection(closestFloor, currentFloor, direction); //elevator will go all the way to the farthest request and then come back
        }
    }

    private void handleCritical(PriorityQueue<Floor> criticalFloors, List<Elevator> elevatorsWithoutTarget) {
        while (!criticalFloors.isEmpty()) {
            var critical = criticalFloors.poll();
            int currentFloor = critical.getFloorNumber();
            Elevator closestElevator = getClosestElevator(currentFloor, elevatorsWithoutTarget);
            if (closestElevator == null)
                break;
            Direction direction = currentFloor > closestElevator.getCurrentFloor() ? Direction.UP : Direction.DOWN;
            elevatorsWithoutTarget.remove(closestElevator);
            closestElevator.setTargetFloor(currentFloor);
            untagAllInDirection(currentFloor, currentFloor, direction);
        }
    }

    private Elevator getClosestElevator(int currentFloor, List<Elevator> elevatorsWithoutTarget) {
        return elevatorsWithoutTarget.stream()
                .min(Comparator.comparingInt(elevator -> Math.abs(elevator.getCurrentFloor() - currentFloor)))
                .orElse(null);
    }

    private PriorityQueue<Floor> prepareQueries(List<Floor> floorList) {
        queries = new HashMap<>(floorList.size());
        PriorityQueue<Floor> criticalFloors = new PriorityQueue<>((f1, f2) -> f2.getWaitingPeople().size() - f1.getWaitingPeople().size());
        for (Floor floor : floorList) {
            if (floor.getWaitingPeople().size() > getCriticalNoOfWaitingPeople())
                criticalFloors.add(floor);
            queries.put(floor.getFloorNumber(), new Query(floor.getWaitingUp().size(),
                    floor.getWaitingDown().size(), floor.getFloorNumber()));
        }
        return criticalFloors;
    }

    private int getFarthestFloorInSameDirection(int startingFloor) {
        int farthestFloorUp = -1;
        for (int i = maxFloor; i > startingFloor; i--) {
            if (queries.get(i).getWaitingUp()) {
                farthestFloorUp = i;
                break;
            }
        }
        int farthestFloorDown = -1;
        for (int i = minFloor; i < startingFloor; i++) {
            if (queries.get(i).getWaitingDown()) {
                farthestFloorDown = i;
                break;
            }
        }
        return calcTheFarthestFloor(startingFloor, farthestFloorUp, farthestFloorDown);
    }

    private void untagAllInDirection(int from, int to, Direction direction) {
        int i = from;
        int step = to > from ? 1 : -1;
        while (true) {
            queries.get(i).untag(direction);
            if (i == to)
                break;
            i += step;
        }
        queries.get(to).untagAll();
    }

    private int getTheFarthestRequest(int startingFloor) {
        int theFarthestFloorUp = -1;
        for (int i = maxFloor; i >= startingFloor; i--) {
            if (queries.get(i).tagged()) {
                theFarthestFloorUp = i;
                break;
            }
        }
        int theFarthestFloorDown = -1;
        for (int i = minFloor; i <= startingFloor; i++) {
            if (queries.get(i).tagged()) {
                theFarthestFloorDown = i;
                break;
            }
        }
        return calcTheFarthestFloor(startingFloor, theFarthestFloorUp, theFarthestFloorDown);
    }

    private int calcTheFarthestFloor(int starting, int f1, int f2) {
        if (f1 == -1) return f2;
        if (f2 == -1) return f1;
        if (Math.abs(f1 - starting) > Math.abs(f2 - starting))
            return f1;
        return f2;
    }

    public int getCriticalNoOfWaitingPeople() {
        return criticalNoOfWaitingPeople;
    }
}
