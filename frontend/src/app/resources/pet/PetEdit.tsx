import { DateInput, Edit, Labeled, ReferenceField, ReferenceInput, SimpleForm, TextInput } from "react-admin";
import { redirectFromPetToOwner } from "./redirectFromPetToOwner";

export const PetEdit = () => (
  <Edit redirect={redirectFromPetToOwner} mutationMode="pessimistic">
    <SimpleForm>
      <Labeled label="Owner">
        <ReferenceField source="ownerId" reference="owner"/>
      </Labeled>
      <TextInput source="name" required autoFocus/>
      <DateInput source="birthDate" required/>
      <ReferenceInput source="typeId" reference="pet-type" required/>
    </SimpleForm>
  </Edit>
)
