import { Datagrid, DateField, DeleteButton, EditButton, List, NumberField, TextField } from "react-admin"

export const PetList = () => {
  return (
    <List>
      <Datagrid bulkActionButtons={false}>
        <TextField source="name" sortable={false} />
        <DateField source="birthDate" sortable={false} />
        <NumberField source="typeId" sortable={false} />
        <NumberField source="ownerId" sortable={false} />
        <EditButton />
        <DeleteButton mutationMode="pessimistic"/>
     </Datagrid>
    </List>
  )
}