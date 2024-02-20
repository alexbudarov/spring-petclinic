import { ChipField, Datagrid, DeleteButton, EditButton, List, ReferenceManyField, SingleFieldList, TextField, TextInput } from "react-admin"

const filters = [
  <TextInput label="Name" source="q" alwaysOn={true} />
]

export const OwnerList = () => {
  return (
    <List filters={filters}>
      <Datagrid bulkActionButtons={false}>
        <TextField source="firstName" />
        <TextField source="lastName" />
        <TextField source="address" />
        <TextField source="city" />
        <TextField source="telephone" />
        <ReferenceManyField label="Pets" reference="pet" target="ownerId">
          <SingleFieldList>
            <ChipField source="name"  />
          </SingleFieldList>
        </ReferenceManyField>
        <EditButton />
        <DeleteButton mutationMode="pessimistic"/>
     </Datagrid>
    </List>
  )
}