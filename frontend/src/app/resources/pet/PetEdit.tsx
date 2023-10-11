import { DateInput, Edit, ReferenceField, ReferenceInput, SimpleForm, TextInput } from "react-admin";

export const PetEdit = () => (
  <Edit>
    <SimpleForm>
      <ReferenceField source="ownerId" reference="owner"/>
      <TextInput source="name" required autoFocus/>
      <DateInput source="birthDate" required/>
      <ReferenceInput source="typeId" reference="pet-type" required/>
      {/* <AutocompleteInput optionText="name" /> */}
    </SimpleForm>
  </Edit>
)