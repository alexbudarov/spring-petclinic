import { Create, DateInput, NumberInput, SimpleForm, TextInput } from "react-admin"

export const PetCreate = () => {
  return (
    <Create redirect="list">
      <SimpleForm>
        <TextInput source="name" required autoFocus/>
        <DateInput source="birthDate"/>
        <NumberInput source="typeId" required />
        <NumberInput source="ownerId" required />
      </SimpleForm>
    </Create>
  )
}
