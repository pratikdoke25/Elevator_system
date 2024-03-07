import { Box } from "@chakra-ui/react";

function FloorBox({ children }: { children: React.ReactNode }) {
  return (
    <Box boxSize={10} border={`1px solid #000`}>
      {children}
    </Box>
  );
}

export default FloorBox;
