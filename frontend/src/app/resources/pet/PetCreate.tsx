import { AutocompleteInput, Create, DateInput, Labeled, ReferenceField, ReferenceInput, SimpleForm, TextInput, required } from "react-admin"
import { redirectFromPetToOwner } from "./redirectFromPetToOwner"

export const PetCreate = () => {
  return <>
    <Create redirect={redirectFromPetToOwner}>
      <SimpleForm>
        <Labeled label="Owner">
          <ReferenceField source="ownerId" reference="owner"/>
        </Labeled>
        <TextInput source="name" required autoFocus/>
        <DateInput source="birthDate"/>
        <ReferenceInput source="typeId" reference="pet-type">
            <AutocompleteInput validate={required()} />
        </ReferenceInput>
      </SimpleForm>
    </Create>
  </>
}
