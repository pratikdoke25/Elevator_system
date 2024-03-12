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
import { Elevator, Floor, Setup, System, UISetup } from "../utils/SystemInterfaces";
import ElevatorLine from "./ElevatorLine";
import Floors from "./Floors";
import SetupDialog from "./SetupDialog";

export const ElevatorSetterContext = createContext(null as unknown as useElevatorSetterType);
export const UISetupContext = createContext({ romanLetters: true } as UISetup);

const post = CreatePost(baseUrl);

function Root() {
  const defaultSetup = { floors: 8, elevators: 8 } as Setup;
  const [UISetup, setUISetup] = useState({ romanLetters: true } as UISetup);
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
    post("/setup", { ...setup, floors: setup.floors + 1 })
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
      <UISetupContext.Provider value={UISetup}>
        <SetupDialog
          isOpen={isOpen}
          onClose={onClose}
          setupState={setup}
          setSetup={setSetup}
          romanLetters={UISetup.romanLetters}
          setRomanLetters={(value: boolean) =>
            setUISetup((prev) => ({ ...prev, romanLetters: value }))
          }
        />
        <Box
          bgGradient="linear(to-b, purple.200, blue.400)"
          minH={"100vh"}
          minW={"100vw"}
          p={"5px"}
        >
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
            <HStack
              divider={<StackDivider />}
              ml={`calc(40vw - ${setup.elevators * 30}px)`} // Adjust the multiplier value to increase or decrease the margin
              mr={"auto"}
            >
              {elevators.map((elev, i) => (
                <ElevatorLine key={i} elevator={elev} floors={floors} />
              ))}
              <Floors floors={floors} />
            </HStack>
            {loading ? (
              <HStack mt={4}>
                <Button onClick={newStep}>Next Step</Button>
                <Button onClick={onOpen}>Setup</Button>
              </HStack>
            ) : (
              <Text>Loading...</Text>
            )}
          </VStack>
        </Box>
      </UISetupContext.Provider>
    </ElevatorSetterContext.Provider>
  );
}

export default Root;
