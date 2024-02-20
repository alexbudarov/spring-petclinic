import { AutocompleteInput, DateInput, Edit, ReferenceInput, SimpleForm, TextInput, required } from "react-admin";

export const OwnerEdit = () => {
  return (
    <Edit>
      <SimpleForm>
        <TextInput source="firstName" required autoFocus />
        <TextInput source="lastName" required />
        <TextInput source="address" required />
        <TextInput source="city" required />
        <TextInput source="telephone" required />
      </SimpleForm>
    </Edit>
  )
}
