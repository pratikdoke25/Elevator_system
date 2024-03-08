import { Person } from "../SystemInterfaces";
import FloorBox from "./Floor";
import {
  Box,
  Popover,
  PopoverArrow,
  PopoverBody,
  PopoverContent,
  PopoverHeader,
  PopoverTrigger,
  Table,
  Tbody,
  Td,
  Text,
  Thead,
  Tr,
} from "@chakra-ui/react";

function ElevatorBox({ peopleInside }: { peopleInside: Person[] }) {
  const exitsOnFloorCount = peopleInside.reduce((acc, person) => {
    if (typeof acc[person.targetFloor] === "undefined") acc = { ...acc, [person.targetFloor]: 0 };
    acc[person.targetFloor] += 1;
    return { ...acc };
  }, {} as { [targetFloor: number]: number });
  const ElevatorInside = (
    <Table>
      <Thead>
        <Tr>
          <Td>People Count</Td>
          <Td>Target Floor</Td>
        </Tr>
      </Thead>
      <Tbody>
        {Object.entries(exitsOnFloorCount)
          .sort(([, target]) => {
            return -target;
          })
          .map(([floor, count]) => (
            <Tr>
              <Td>{count}</Td>
              <Td>{floor}</Td>
            </Tr>
          ))}
      </Tbody>
    </Table>
  );

  return (
    <FloorBox>
      <Popover trigger="hover">
        <PopoverTrigger>
          <Box boxSize={"full"} bg={"pink"}></Box>
        </PopoverTrigger>
        <PopoverContent>
          <PopoverArrow />
          <PopoverHeader>People inside</PopoverHeader>
          <PopoverBody>
            {peopleInside.length > 0 ? ElevatorInside : <Text color={"gray"}>Empty</Text>}
          </PopoverBody>
        </PopoverContent>
      </Popover>
    </FloorBox>
  );
}

export default ElevatorBox;
