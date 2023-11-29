import { AutocompleteInput, Create, DateInput, Labeled, ReferenceInput, SimpleForm, TextInput, required } from "react-admin"

export const PetCreate = () => {
  return <>
    <Create redirect="list">
      <SimpleForm>
        <TextInput source="name" required autoFocus/>
        <DateInput source="birthDate"/>
        <ReferenceInput source="typeId" reference="pet-type">
            <AutocompleteInput validate={required()} />
        </ReferenceInput>
        <ReferenceInput source="ownerId" reference="owner">
        <AutocompleteInput validate={required()} />
      </ReferenceInput>
      </SimpleForm>
    </Create>
  </>
}
