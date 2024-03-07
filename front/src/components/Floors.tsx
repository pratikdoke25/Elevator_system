import { HStack, Text, VStack } from "@chakra-ui/react";
import { Floor } from "../SystemInterfaces";
import { ChatIcon } from "@chakra-ui/icons";
import FloorBox from "./Floor";

export default function Floors(floors: Floor[]) {
  return (
    <VStack>
      {floors
        .map((floor, i) => (
          <HStack ml={"auto"} mr={0} key={i}>
            {floor.waitingPeople.map((person, i) => (
              <HStack key={i}>
                <ChatIcon /> <Text>{person.targetFloor}</Text>
              </HStack>
            ))}
            <FloorBox key={i}>
              <Text>{floor.floorNumber}</Text>
            </FloorBox>
          </HStack>
        ))
        .reverse()}
    </VStack>
  );
}
