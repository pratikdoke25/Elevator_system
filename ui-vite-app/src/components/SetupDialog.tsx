import {
  AlertDialog,
  AlertDialogBody,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogOverlay,
  Button,
  FormControl,
  FormLabel,
  Switch,
  VStack,
} from "@chakra-ui/react";
import { useRef } from "react";
import { Setup } from "../utils/SystemInterfaces";
import NumberSelector from "./NumberSelector";

function SetupDialog({
  isOpen,
  onClose,
  setupState,
  setSetup,
  romanLetters,

  setRomanLetters,
}: {
  isOpen: boolean;
  onClose: () => void;
  setupState: Setup;
  setSetup: (prevFunc: (prev: Setup) => Setup) => void;
  romanLetters: boolean;
  setRomanLetters: (value: boolean) => void;
}) {
  const cancelRef = useRef(null);
  return (
    <AlertDialog isOpen={isOpen} leastDestructiveRef={cancelRef} onClose={onClose}>
      <AlertDialogOverlay>
        <AlertDialogContent>
          <AlertDialogHeader fontSize="lg" fontWeight="bold">
            Edit Setup
          </AlertDialogHeader>

          <AlertDialogBody>
            <VStack align={"start"}>
              <FormControl variant="floating">
                <FormLabel>Highest Floor</FormLabel>
                <NumberSelector
                  min={1}
                  max={20}
                  value={setupState.floors}
                  setValue={(value: number) => {
                    if (value < 1) value = 1;
                    if (value > 20) value = 20;
                    setSetup((prev: Setup) => ({ ...prev, floors: value }));
                  }}
                />
              </FormControl>
              <FormControl>
                <FormLabel>Elevators</FormLabel>
                <NumberSelector
                  min={1}
                  max={16}
                  value={setupState.elevators}
                  setValue={(value: number) => {
                    if (value < 1) value = 1;
                    if (value > 16) value = 16;
                    setSetup((prev: Setup) => ({ ...prev, elevators: value }));
                  }}
                />
              </FormControl>
              <Switch
                isChecked={romanLetters}
                onChange={(e) => setRomanLetters(e.target.checked)}
                colorScheme="green"
              >
                {" "}
                Roman Letters
              </Switch>
            </VStack>
          </AlertDialogBody>

          <AlertDialogFooter>
            <Button ref={cancelRef} onClick={onClose}>
              Cancel
            </Button>
            <Button colorScheme="green" onClick={onClose} ml={3}>
              Save
            </Button>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialogOverlay>
    </AlertDialog>
  );
}

export default SetupDialog;
