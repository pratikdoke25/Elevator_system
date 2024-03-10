import { Elevator } from "../utils/SystemInterfaces";

export interface useElevatorSetterType {
  (id: number, elevatorLine: Elevator): void;
}

export const useElevatorSetter = (
  setElevators: (update: (prev: Elevator[]) => Elevator[]) => void
): useElevatorSetterType => {
  return (id: number, elevatorLine: Elevator) => {
    setElevators((prev: Elevator[]) => {
      const newElevators = [...prev];
      newElevators[id] = elevatorLine;
      return newElevators;
    });
  };
};
