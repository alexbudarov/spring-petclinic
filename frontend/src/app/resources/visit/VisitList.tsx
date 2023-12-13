import { Datagrid, DateField, DateInput, FunctionField, List, NumberField, ReferenceField, ReferenceInput, ReferenceOneField, ShowButton, TextField, TextInput } from "react-admin"

const filters = [
  <TextInput label="Description" source="description" />,
  <DateInput label="Date after" source="dateAfter" />,
  <DateInput label="Date before" source="dateBefore" />,
]

export const VisitList = () => {
  return (
      <List filters={filters}>
        <Datagrid bulkActionButtons={false}>
          <NumberField source="petId" />
          <DateField source="date" />
          <TextField source="description" />
          <NumberField source="assignedVetId" />
          <ShowButton />
        </Datagrid>
      </List>
  );
}