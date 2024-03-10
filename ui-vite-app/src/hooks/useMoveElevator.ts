import { Elevator } from "../utils/SystemInterfaces";
import { useElevatorSetterType } from "./useElevatorSetter";

export const useMoveElevator = (setter: useElevatorSetterType, floors: number) => {
  return (id: number, elevator: Elevator, direction: number) => {
    let newFloor = elevator.currentFloor + direction;
    if (newFloor < 0) newFloor = 0;
    if (newFloor >= floors) newFloor = floors - 1;
    setter(id, {
      ...elevator,
      currentFloor: newFloor,
    });
  };
};
