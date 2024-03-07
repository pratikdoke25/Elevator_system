import { ChakraProvider } from "@chakra-ui/react";
import Root from "./components/Root";

function App() {
  return (
    <ChakraProvider>
      <Root />
    </ChakraProvider>
  );
}

export default App;
