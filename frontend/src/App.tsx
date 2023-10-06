import {
  Admin,
  Resource,
  ListGuesser,
  EditGuesser,
  ShowGuesser,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { VetList } from "./app/resources/vet/VetList";
import { OwnerList } from "./app/resources/owner/OwnerList";
import { OwnerShow } from "./app/resources/owner/OwnerShow";
import { ownerRecordRepresentation } from "./app/functions/ownerRecordRepresentation";
import { OwnerEdit } from "./app/resources/owner/OwnerEdit";
import { OwnerCreate } from "./app/resources/owner/OwnerCreate";

export const App = () => (
  <Admin dataProvider={dataProvider}>
    <Resource
      name="owner"
      list={OwnerList}
      edit={OwnerEdit}
      show={OwnerShow}
      create={OwnerCreate}
      recordRepresentation={ownerRecordRepresentation}
    />
    <Resource
      name="pet-type"
      list={ListGuesser}
      edit={EditGuesser}
      show={ShowGuesser}
    />
    <Resource
      name="pet"
      list={ListGuesser}
      edit={EditGuesser}
      show={ShowGuesser}
    />
    <Resource
      name="vet"
      list={VetList}
    />
    <Resource
      name="visit"
      list={ListGuesser}
      edit={EditGuesser}
      show={ShowGuesser}
    />
  </Admin>
);
