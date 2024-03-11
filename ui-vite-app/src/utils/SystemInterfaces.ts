export interface System {
  elevators: Elevator[];
  floors: Floor[];
}
export interface Setup {
  floors: number;
  elevators: number;
}

export interface Elevator {
  id: number;
  currentFloor: number;
  targetFloor: number | undefined;
  direction: 1 | -1 | 0;
  people: Person[];
}
export interface Floor {
  floorNumber: number;
  waitingPeople: Person[];
  button: ButtonState;
}
export interface Person {
  targetFloor: number;
}
export interface ButtonState {
  pressedButtons: Direction[];
}
export enum Direction {
  UP = "UP",
  DOWN = "DOWN",
  NONE = "NONE",
}

export interface UISetup {
  romanLetters: boolean;
}
