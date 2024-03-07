import FloorBox from "./Floor";
import { Box } from "@chakra-ui/react";

function ElevatorBox() {
  return (
    <FloorBox>
      <Box boxSize={10} bg={`#000`}></Box>
    </FloorBox>
  );
}

export default ElevatorBox;
