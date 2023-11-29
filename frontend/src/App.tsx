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
import { ownerRecordRepresentation } from "./app/functions/resources/ownerRecordRepresentation";

export const App = () => (
  <Admin dataProvider={dataProvider}>
    <Resource
      name="vet"
      list={VetList}
      recordRepresentation={vetRecordRepresentation}
    />
    <Resource
      name="owner"
      list={ListGuesser}
      show={ShowGuesser}
      edit={EditGuesser}
      recordRepresentation={ownerRecordRepresentation}
    />
  </Admin>
);
