import { StackDivider, VStack } from "@chakra-ui/react";
import { Elevator, Floor } from "../utils/SystemInterfaces";
import ElevatorBox from "./Elevator";
import FloorBox from "./Floor";

function ElevatorLine({ elevator, floors }: { elevator: Elevator; floors: Floor[] }) {
  // const elevatorSetter = useContext(ElevatorSetterContext);
  // const moveElevator = useMoveElevator(elevatorSetter, floors.length);
  const floorNodes = floors
    .map((floor, i) => (
      <FloorBox key={i}>
        {floor.floorNumber == elevator.currentFloor && (
          <ElevatorBox peopleInside={elevator.people} />
        )}
      </FloorBox>
    ))
    .reverse();
  return (
    <VStack divider={<StackDivider />}>
      {floorNodes}
      {/* {Controls(moveElevator, elevator)} */}
    </VStack>
  );
}

export default ElevatorLine;

// function Controls(
//   moveElevator: (id: number, elevator: Elevator, direction: number) => void,
//   elevator: Elevator
// ) {
//   return (
//     <VStack>
//       <Button
//         onClick={() => {
//           moveElevator(elevator.id, elevator, 1);
//         }}
//       >
//         Up
//       </Button>
//       <Button
//         onClick={() => {
//           moveElevator(elevator.id, elevator, -1);
//         }}
//       >
//         Down
//       </Button>
//     </VStack>
//   );
// }
