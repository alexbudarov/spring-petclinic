import {
  Admin,
  Resource,
  ListGuesser,
  EditGuesser,
  ShowGuesser,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { ownerRecordRepresentation } from "./app/functions/ownerRecordRepresentation";
import { PetList } from "./app/resources/pet/PetList";
import { PetEdit } from "./app/resources/pet/PetEdit";
import { PetCreate } from "./app/resources/pet/PetCreate";

export const App = () => (
  <Admin dataProvider={dataProvider}>
    <Resource
      name="pet-type"
      list={ListGuesser}
      recordRepresentation="name"
    />
    <Resource
      name="owner"
      list={ListGuesser}
      recordRepresentation={ownerRecordRepresentation}
    />
    <Resource
      name="pet"
      list={PetList}
      edit={PetEdit}
      create={PetCreate}
      recordRepresentation="name"
    />
  </Admin>
);
