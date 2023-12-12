import { Datagrid, DateField, DateInput, FunctionField, List, ReferenceField, ReferenceInput, ReferenceOneField, ShowButton, TextField, TextInput } from "react-admin"
import { VisitOwnerTelephoneField } from "./VisitOwnerTelephoneField";
import { ownerRecordRepresentation } from "../../functions/resources/ownerRecordRepresentation";

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
          <ReferenceOneField label="Owner name" reference="owner" target="visitId" sortable={false}>
            <FunctionField render={ownerRecordRepresentation} />
          </ReferenceOneField>
          <ReferenceOneField label="Owner telephone" reference="owner" target="visitId" sortable={false}>
            <TextField source="telephone" />
          </ReferenceOneField>
          <ShowButton />
        </Datagrid>
      </List>
  );
}