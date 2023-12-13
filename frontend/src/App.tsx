import {
  Admin,
  Resource,
  ListGuesser,
  CustomRoutes,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { ownerRecordRepresentation } from "./app/functions/ownerRecordRepresentation";
import { PetList } from "./app/resources/pet/PetList";
import { PetEdit } from "./app/resources/pet/PetEdit";
import { PetCreate } from "./app/resources/pet/PetCreate";
import { VisitList } from "./app/resources/visit/VisitList";
import { VisitShow } from "./app/resources/visit/VisitShow";
import { vetRecordRepresentation } from "./app/functions/vetRecordRepresentation";
import { Route } from "react-router-dom";
import { AppLayout } from "./AppLayout";
import { VisitRequest } from "./app/resources/visit/VisitRequest";

export const App = () => (
  <Admin dataProvider={dataProvider} layout={AppLayout}>
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
    <Resource
      name="specialty"
      recordRepresentation="name"
    />
    <CustomRoutes>
      <Route path="/visit/request" element={<VisitRequest />} />
    </CustomRoutes>
  </Admin>
);
