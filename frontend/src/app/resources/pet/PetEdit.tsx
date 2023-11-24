import { AutocompleteInput, Datagrid, DateField, DateInput, Edit, Identifier, Labeled, ReferenceField, ReferenceInput, SimpleForm, SortPayload, TextField, TextInput, required, useGetManyReference, useRecordContext } from "react-admin";
import { redirectFromPetToOwner } from "./redirectFromPetToOwner";
import { Typography } from "@mui/material";

export const PetEdit = () => (
  <Edit redirect={redirectFromPetToOwner} mutationMode="pessimistic">
    <SimpleForm>
      <Labeled label="Owner">
        <ReferenceField source="ownerId" reference="owner"/>
      </Labeled>
      <TextInput source="name" required autoFocus/>
      <DateInput source="birthDate"/>
      <ReferenceInput source="typeId" reference="pet-type">
            <AutocompleteInput validate={required()} />
      </ReferenceInput>
      <PetVisits/>
    </SimpleForm>
  </Edit>
)

const PetVisits = () => {
  const pet = useRecordContext();
  const petId = pet?.id;

  const sort: SortPayload = { field: 'date', order: 'DESC' };
  const { data, total, isLoading } = useGetManyReference(
      'visit',
      { 
        target: 'petId',
        id: petId,
        sort: sort
      }
  );
  const visits = data || [];

  return <>
    <Typography variant="h6">Visits</Typography>
    <Datagrid
            data={visits}
            total={total}
            isLoading={isLoading}
            sort={sort}
            bulkActionButtons={false}
        >
      <DateField source="date" options={{dateStyle: 'long'}} />
      <TextField source="description" />
    </Datagrid>
  </>
}
