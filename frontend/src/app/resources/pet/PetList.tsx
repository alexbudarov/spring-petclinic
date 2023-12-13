import { Datagrid, DateField, DeleteButton, EditButton, List, NumberField, ReferenceField, ReferenceInput, TextField } from "react-admin"

const filters = [
  <ReferenceInput label="Owner" source="ownerId" reference="owner" alwaysOn={true} />
]

export const PetList = () => {
  return (
    <List filters={filters}>
      <Datagrid bulkActionButtons={false}>
        <TextField source="name" sortable={false} />
        <DateField source="birthDate" sortable={false} />
        <ReferenceField source="typeId" reference="pet-type" sortable={false} />
        <ReferenceField source="ownerId" reference="owner" sortable={false} />
        <EditButton />
        <DeleteButton mutationMode="pessimistic"/>
     </Datagrid>
    </List>
  )
}