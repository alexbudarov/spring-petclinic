import { DateInput, Edit, NumberInput, SimpleForm, TextInput } from "react-admin";

export const PetEdit = () => {
  return (
    <Edit>
      <SimpleForm>
        <TextInput source="name" required autoFocus/>
        <DateInput source="birthDate"/>
        <NumberInput source="typeId" required />
        <NumberInput source="ownerId" required />
      </SimpleForm>
    </Edit>
  )
}
