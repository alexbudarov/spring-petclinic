import {
  Admin,
  Resource,
  ListGuesser,
  EditGuesser,
  ShowGuesser,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { ownerRecordRepresentation } from "./app/functions/ownerRecordRepresentation";

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
      list={ListGuesser}
      edit={EditGuesser}
      show={ShowGuesser}
      recordRepresentation="name"
    />
  </Admin>
);
