import { Button, Center, HStack, Text, useDisclosure } from "@chakra-ui/react";
import { createContext, useEffect, useState } from "react";
import { Elevator, Floor, Setup, System } from "../SystemInterfaces";
import { useElevatorSetter, useElevatorSetterType } from "../hooks/useElevatorSetter";
import { usePost as CreatePost } from "../hooks/usePost";
import { url as baseUrl } from "../url";
import ElevatorLine from "./ElevatorLine";
import Floors from "./Floors";
import SetupDialog from "./SetupDialog";

export const ElevatorSetterContext = createContext(null as unknown as useElevatorSetterType);

const post = CreatePost(baseUrl);

function Root() {
  const defaultSetup = { floors: 3, elevators: 2 } as Setup;
  const [setup, setSetup] = useState(defaultSetup);
  const [elevators, setElevators] = useState([] as Elevator[]);
  const [floors, setFloors] = useState([] as Floor[]);
  const setElevator = useElevatorSetter(setElevators);
  const handleNewState = ({ elevators, floors }: System) => {
    console.log(elevators, floors);
    setElevators(elevators);
    setFloors(floors);
    return { elevators, floors };
  };
  const { isOpen, onOpen, onClose } = useDisclosure();

  useEffect(() => {
    post("/setup", setup).then(handleNewState).catch(console.error);
  }, [setup]);
  const newStep = () => {
    post("/step", {}).then(handleNewState).catch(console.error);
  };
  return (
    <ElevatorSetterContext.Provider value={setElevator}>
      <SetupDialog isOpen={isOpen} onClose={onClose} setupState={setup} setSetup={setSetup} />
      <Center h={"100vh"}>
        <HStack>
          {Floors(floors)}
          {elevators.map((elev, i) => (
            <ElevatorLine key={i} elevator={elev} floors={floors} />
          ))}
        </HStack>
        {elevators.length * floors.length > 0 ? (
          <Button onClick={newStep}>Next Step</Button>
        ) : (
          <Text>"Loading..."</Text>
        )}
        <Button onClick={onOpen}>Setup</Button>
      </Center>
    </ElevatorSetterContext.Provider>
  );
}

export default Root;
