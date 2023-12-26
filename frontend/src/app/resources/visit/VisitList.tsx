import { Datagrid, DateField, DateInput, List, NumberField, ReferenceField, ReferenceManyField, ReferenceOneField, ShowButton, TextField, TextInput } from "react-admin"

const filters = [
  <TextInput label="Description" source="description" />,
  <DateInput label="Date after" source="dateAfter" />,
  <DateInput label="Date before" source="dateBefore" />,
]

export const VisitList = () => {
  return (
      <List filters={filters}>
        <Datagrid bulkActionButtons={false}>
          <ReferenceField source="petId" reference="pet" sortBy="pet.name"/>
          <DateField source="date" options={{ dateStyle: 'medium' }} />
          <TextField source="description" />
          <ReferenceField source="assignedVetId" reference="vet" sortBy="assignedVet.firstName"/>
          {/* <ReferenceOneField label="Owner name" reference="owner" target="visitId">
            <TextField source="lastName" />
          </ReferenceOneField>
          <ReferenceOneField label="Owner address" reference="owner" target="visitId">
            <TextField source="address" />
          </ReferenceOneField>
          <ReferenceOneField label="Owner city" reference="owner" target="visitId">
            <TextField source="city" />
          </ReferenceOneField> */}
          <ReferenceField label="Owner name" source="petOwnerId" reference="owner" sortable={false} link={false} />
          <ReferenceField label="Owner telephone" source="petOwnerId" reference="owner" sortable={false} link={false}>
            <TextField source="telephone" />
          </ReferenceField>
          <ShowButton />
        </Datagrid>
      </List>
  );
}