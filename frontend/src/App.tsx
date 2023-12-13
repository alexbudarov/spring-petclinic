import {
  Admin,
  Resource,
  ListGuesser,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { ownerRecordRepresentation } from "./app/functions/ownerRecordRepresentation";
import { PetList } from "./app/resources/pet/PetList";
import { PetEdit } from "./app/resources/pet/PetEdit";
import { PetCreate } from "./app/resources/pet/PetCreate";
import { VisitList } from "./app/resources/visit/VisitList";
import { VisitShow } from "./app/resources/visit/VisitShow";
import { vetRecordRepresentation } from "./app/functions/vetRecordRepresentation";

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
    <Resource
      name="visit"
      list={VisitList}
      show={VisitShow}
    />
    <Resource
      name="vet"
      recordRepresentation={vetRecordRepresentation}
    />    
  </Admin>
);
