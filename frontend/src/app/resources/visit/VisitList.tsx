import { Datagrid, DateField, DateInput, List, ReferenceField, ReferenceInput, ShowButton, TextField, TextInput } from "react-admin"
import { VisitOwnerTelephoneField } from "./VisitOwnerTelephoneField";

const filters = [
  <TextInput label="Description" source="description" />,
  <DateInput label="Date after" source="dateAfter" />,
  <DateInput label="Date before" source="dateBefore" />,
  <ReferenceInput label="Assigned Vet" source="assignedVetId" reference="vet" />,
]


export const VisitList = () => {
  return (
      <List filters={filters}>
        <Datagrid bulkActionButtons={false}>
          <ReferenceField source="petId" reference="pet" sortBy="pet.name" />
          <DateField source="date" options={{ dateStyle: 'medium' }} />
          <TextField source="description" />
          <ReferenceField source="assignedVetId" reference="vet" sortBy="assignedVet.firstName" />
          <ReferenceField label="Owner name" source="petOwnerId" reference="owner" sortable={false} link={false} />
          <VisitOwnerTelephoneField />
          <ShowButton />
        </Datagrid>
      </List>
  );
}