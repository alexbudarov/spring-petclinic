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

export const App = () => (
  <Admin dataProvider={dataProvider}>
    <Resource
      name="owner"
      list={OwnerList}
      edit={EditGuesser}
      show={ShowGuesser}
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
