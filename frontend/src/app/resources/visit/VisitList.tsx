import { Datagrid, DateField, DateInput, List, NumberField, ReferenceField, ShowButton, TextField, TextInput } from "react-admin"

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
          <NumberField source="assignedVetId" />
          <ShowButton />
        </Datagrid>
      </List>
  );
}