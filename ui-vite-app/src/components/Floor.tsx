import { Center } from "@chakra-ui/react";

function FloorBox({ children }: { children: React.ReactNode }) {
  const size = "50px";
  return (
    <Center minH={size} h={size} minW={size} w={size} border={children ? `1px solid black` : ""}>
      {children}
    </Center>
  );
}

export default FloorBox;
