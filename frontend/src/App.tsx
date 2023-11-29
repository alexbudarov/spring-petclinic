import {
  Admin,
  Resource,
  ListGuesser,
  EditGuesser,
  ShowGuesser,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { vetRecordRepresentation } from "./app/functions/vetRecordRepresentation";
import { VetList } from "./app/functions/resources/vet/VetList";

export const App = () => (
  <Admin dataProvider={dataProvider}>
    <Resource
      name="vet"
      list={VetList}
      recordRepresentation={vetRecordRepresentation}
    />
  </Admin>
);
