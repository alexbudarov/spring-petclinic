import { DateInput, Edit, RedirectionSideEffect, ReferenceField, ReferenceInput, SimpleForm, TextInput } from "react-admin";

export const PetEdit = () => (
  <Edit redirect={redirectAfterSave} mutationMode="pessimistic">
    <SimpleForm>
      <ReferenceField source="ownerId" reference="owner"/>
      <TextInput source="name" required autoFocus/>
      <DateInput source="birthDate" required/>
      <ReferenceInput source="typeId" reference="pet-type" required/>
      {/* <AutocompleteInput optionText="name" /> */}
    </SimpleForm>
  </Edit>
)

const redirectAfterSave: RedirectionSideEffect = (resource, id, data) => {
  const ownerId = data?.ownerId
  if (ownerId) {
    return 'owner/' + ownerId + '/show';
  }
  return 'owner'
}