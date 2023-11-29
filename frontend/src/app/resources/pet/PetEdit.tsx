import { AutocompleteInput, DateInput, Edit, ReferenceInput, SimpleForm, TextInput, required } from "react-admin";

export const PetEdit = () => (
  <Edit>
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
  </Edit>
)
