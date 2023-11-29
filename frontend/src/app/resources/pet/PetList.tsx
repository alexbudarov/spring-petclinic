import { Datagrid, DateField, DeleteButton, EditButton, List, ReferenceField, TextField } from "react-admin"

export const PetList = () => {
  return (
    <List>
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