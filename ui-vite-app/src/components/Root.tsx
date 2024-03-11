import {
  Box,
  Button,
  HStack,
  Spacer,
  StackDivider,
  Text,
  VStack,
  useDisclosure,
} from "@chakra-ui/react";
import { createContext, useEffect, useRef, useState } from "react";
import { useElevatorSetter, useElevatorSetterType } from "../hooks/useElevatorSetter";
import { usePost as CreatePost } from "../hooks/usePost";
import { url as baseUrl } from "../url";
import { Elevator, Floor, Setup, System } from "../utils/SystemInterfaces";
import ElevatorLine from "./ElevatorLine";
import Floors from "./Floors";
import SetupDialog from "./SetupDialog";

export const ElevatorSetterContext = createContext(null as unknown as useElevatorSetterType);

const post = CreatePost(baseUrl);

function Root() {
  const defaultSetup = { floors: 8, elevators: 3 } as Setup;
  const [setup, setSetup] = useState(defaultSetup);
  const [elevators, setElevators] = useState([] as Elevator[]);
  const [floors, setFloors] = useState([] as Floor[]);
  const setElevator = useElevatorSetter(setElevators);
  const handleNewState = ({ elevators, floors }: System) => {
    console.log(elevators, floors);
    setElevators(elevators);
    setFloors(floors);
    return { elevators, floors } as System;
  };
  const { isOpen, onOpen, onClose } = useDisclosure();
  const loading = useRef(true);
  // const running = useRef(false);
  useEffect(() => {
    post("/setup", setup)
      .then(handleNewState)
      .catch(console.error)
      .finally(() => (loading.current = false));
  }, [setup]);

  const newStep = () => {
    const body = {
      elevators,
      floors: floors.map((floor) => {
        return {
          ...floor,
          waitingPeople: [], // server does need it
        };
      }),
    } as System;
    post("/step", body).then(handleNewState).catch(console.error);
  };
  // const runAsSimulation = async () => {
  //   (function myLoop() {
  //     setTimeout(function () {
  //       newStep();
  //       if (running.current) myLoop(); //  decrement i and call myLoop again if i > 0
  //     }, 2000);
  //   })();
  // };
  return (
    <ElevatorSetterContext.Provider value={setElevator}>
      <SetupDialog isOpen={isOpen} onClose={onClose} setupState={setup} setSetup={setSetup} />
      <Box bgGradient="linear(to-b, purple.200, blue.400)" minH={"100vh"} p={"5px"}>
        <VStack>
          <Text
            as={"i"}
            fontSize="5xl"
            fontWeight="bold"
            fontFamily={"sans-serif"}
            mb={4}
            bgGradient="linear(to-r, black, blue.400)"
            bgClip="text"
          >
            Welcome to Elevators System
          </Text>
          <Spacer />
          <HStack divider={<StackDivider />}>
            {elevators.map((elev, i) => (
              <ElevatorLine key={i} elevator={elev} floors={floors} />
            ))}
            {Floors(floors)}
          </HStack>
          {loading ? (
            <HStack mt={4}>
              {/* <Button
                onClick={() => {
                  running.current = !running.current;
                  if (running.current) runAsSimulation();
                }}
              >
                {!running.current ? "Run as Simulation" : "Stop"}
              </Button> */}
              <Button onClick={newStep}>Next Step</Button>
              <Button onClick={onOpen}>Setup</Button>
            </HStack>
          ) : (
            <Text>Loading...</Text>
          )}
        </VStack>
      </Box>
    </ElevatorSetterContext.Provider>
  );
}

export default Root;
