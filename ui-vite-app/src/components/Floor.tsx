import { Center } from "@chakra-ui/react";

function FloorBox({ children }: { children: React.ReactNode }) {
  return (
    <Center minH={12} h={12} minW={12} w={12} border={children ? `1px solid black` : ""}>
      {children}
    </Center>
  );
}

export default FloorBox;
