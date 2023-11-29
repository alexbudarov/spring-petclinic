import {
  Admin,
  Resource,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { vetRecordRepresentation } from "./app/functions/vetRecordRepresentation";
import { VetList } from "./app/functions/resources/vet/VetList";
import { ownerRecordRepresentation } from "./app/functions/resources/ownerRecordRepresentation";
import { OwnerList } from "./app/resources/owner/OwnerList";
import { OwnerShow } from "./app/resources/owner/OwnerShow";
import { OwnerEdit } from "./app/resources/owner/OwnerEdit";
import { OwnerCreate } from "./app/resources/owner/OwnerCreate";

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
      show={OwnerShow}
      edit={OwnerEdit}
      create={OwnerCreate}
      recordRepresentation={ownerRecordRepresentation}
    />
  </Admin>
);
