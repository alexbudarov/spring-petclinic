import { Edit, SimpleForm, TextInput } from "react-admin";

export const OwnerEdit = () => (
  <Edit>
    <SimpleForm>
      <TextInput source="firstName" required />
      <TextInput source="lastName" required />
      <TextInput source="address" required />
      <TextInput source="city" required />
      <TextInput source="telephone" required />
    </SimpleForm>
  </Edit>
)
