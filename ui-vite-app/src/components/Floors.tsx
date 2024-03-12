import {
  Box,
  HStack,
  Icon,
  Popover,
  PopoverArrow,
  PopoverBody,
  PopoverContent,
  PopoverTrigger,
  StackDivider,
  Text,
  VStack,
} from "@chakra-ui/react";
import "@fontsource/luxurious-roman";
import { FaRegArrowAltCircleDown, FaRegArrowAltCircleUp } from "react-icons/fa";
import { FaPerson } from "react-icons/fa6";
import { Direction, Floor } from "../utils/SystemInterfaces";
import FloorBox from "./Floor";
import { useContext } from "react";
import { UISetupContext } from "./Root";

export default function Floors({ floors }: { floors: Floor[] }) {
  const { romanLetters } = useContext(UISetupContext);
  return (
    <VStack divider={<StackDivider />} alignItems="start">
      {floors
        .map((floor, i) => (
          <HStack key={i}>
            <VStack>
              <Icon
                as={FaRegArrowAltCircleUp}
                color={floor.button.pressedButtons.includes(Direction.UP) ? "gold" : "gray"}
              />
              <Icon
                as={FaRegArrowAltCircleDown}
                color={floor.button.pressedButtons.includes(Direction.DOWN) ? "gold" : "gray"}
              />
            </VStack>
            <FloorBox key={i}>
              <Text fontWeight={"bold"} as={"i"} fontFamily={"Luxurious Roman"}>
                {romanLetters ? RomanNumerals[floor.floorNumber] : floor.floorNumber}
              </Text>
            </FloorBox>
            <HStack
              overflowX={"auto"}
              maxW={"15vw"}
              sx={{
                "&::-webkit-scrollbar": {
                  width: "16px",
                  borderRadius: "8px",
                  backgroundColor: `rgba(0, 0, 0, 0.05)`,
                },
                "&::-webkit-scrollbar-thumb": {
                  backgroundColor: `rgba(0, 0, 0, 0.05)`,
                },
              }}
            >
              {floor.waitingPeople.map((person, i) => (
                <Popover trigger="hover">
                  <PopoverTrigger>
                    <Box>
                      <Icon key={i} as={FaPerson} />
                    </Box>
                  </PopoverTrigger>
                  <PopoverContent whiteSpace={"nowrap"} maxW={"min-content"}>
                    <PopoverArrow />
                    <PopoverBody>
                      <Text>Target: {person.targetFloor}</Text>
                    </PopoverBody>
                  </PopoverContent>
                </Popover>
              ))}
            </HStack>
          </HStack>
        ))
        .reverse()}
    </VStack>
  );
}

const RomanNumerals = {
  0: "P",
  1: "I",
  2: "II",
  3: "III",
  4: "IV",
  5: "V",
  6: "VI",
  7: "VII",
  8: "VIII",
  9: "IX",
  10: "X",
  11: "XI",
  12: "XII",
  13: "XIII",
  14: "XIV",
  15: "XV",
  16: "XVI",
  17: "XVII",
  18: "XVIII",
  19: "XIX",
  20: "XX",
} as { [key: number]: string };
