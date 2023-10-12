import { Create, DateInput, Labeled, ReferenceField, ReferenceInput, SimpleForm, TextInput } from "react-admin"
import { redirectFromPetToOwner } from "./redirectFromPetToOwner"

export const PetCreate = () => {
  return <>
    <Create redirect={redirectFromPetToOwner}>
      <SimpleForm>
        <Labeled label="Owner">
          <ReferenceField source="ownerId" reference="owner"/>
        </Labeled>
        <TextInput source="name" required autoFocus/>
        <DateInput source="birthDate" required/>
        <ReferenceInput source="typeId" reference="pet-type" required/>
      </SimpleForm>
    </Create>
  </>
}
