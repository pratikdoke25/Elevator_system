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
}: {
  isOpen: boolean;
  onClose: () => void;
  setupState: Setup;
  setSetup: (prevFunc: (prev: Setup) => Setup) => void;
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
            <VStack>
              <FormControl variant="floating">
                <FormLabel>Floors</FormLabel>
                <NumberSelector
                  value={setupState.floors}
                  min={1}
                  max={20}
                  setValue={(value: number) => {
                    setSetup((prev: Setup) => ({ ...prev, floors: value }));
                  }}
                />
              </FormControl>
              <FormControl>
                <FormLabel>Elevators</FormLabel>
                <NumberSelector
                  value={setupState.elevators}
                  setValue={(value: number) => {
                    setSetup((prev: Setup) => ({ ...prev, elevators: value }));
                  }}
                  min={1}
                  max={16}
                />
              </FormControl>
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
