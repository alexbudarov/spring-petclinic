import {
  Admin,
  Resource,
  EditGuesser,
  ShowGuesser,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { vetRecordRepresentation } from "./app/functions/vetRecordRepresentation";
import { VetList } from "./app/functions/resources/vet/VetList";
import { ownerRecordRepresentation } from "./app/functions/resources/ownerRecordRepresentation";
import { OwnerList } from "./app/resources/owner/OwnerList";

export const App = () => (
  <Admin dataProvider={dataProvider}>
    <Resource
      name="vet"
      list={VetList}
      recordRepresentation={vetRecordRepresentation}
    />
    <Resource
      name="owner"
      list={OwnerList}
      show={ShowGuesser}
      edit={EditGuesser}
      recordRepresentation={ownerRecordRepresentation}
    />
  </Admin>
);
