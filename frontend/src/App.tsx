import {
  Admin,
  Resource,
  ListGuesser,
  EditGuesser,
  ShowGuesser,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { vetRecordRepresentation } from "./app/functions/vetRecordRepresentation";

export const App = () => (
  <Admin dataProvider={dataProvider}>
    <Resource
      name="vet"
      list={ListGuesser}
      recordRepresentation={vetRecordRepresentation}
    />
  </Admin>
);
