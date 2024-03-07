import { Button, VStack } from "@chakra-ui/react";
import { useContext } from "react";
import { Elevator, Floor } from "../SystemInterfaces";
import { useMoveElevator } from "../hooks/useMoveElevator";
import ElevatorBox from "./Elevator";
import FloorBox from "./Floor";
import { ElevatorSetterContext } from "./Root";

function ElevatorLine({ elevator, floors }: { elevator: Elevator; floors: Floor[] }) {
  const elevatorSetter = useContext(ElevatorSetterContext);
  const moveElevator = useMoveElevator(elevatorSetter, floors.length);
  const floorNodes = floors
    .map((floor, i) => (
      <FloorBox key={i}>{floor.floorNumber == elevator.currentFloor && <ElevatorBox />}</FloorBox>
    ))
    .reverse();
  return (
    <VStack>
      {floorNodes}
      <VStack>
        <Button
          onClick={() => {
            moveElevator(elevator.id, elevator, 1);
          }}
        >
          Up
        </Button>
        <Button
          onClick={() => {
            moveElevator(elevator.id, elevator, -1);
          }}
        >
          Down
        </Button>
      </VStack>
    </VStack>
  );
}

export default ElevatorLine;
