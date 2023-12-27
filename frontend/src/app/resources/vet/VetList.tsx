import { ChipField, Datagrid, List, ReferenceInput, ReferenceManyField, SingleFieldList, TextField } from "react-admin"

const filters = [
  <ReferenceInput label="Specialty" source="specialtyId" reference="specialty" alwaysOn={true} />
]

export const VetList = () => {
  return (
    <List filters={filters} pagination={false}>
      <Datagrid bulkActionButtons={false}>
        <TextField source="firstName" />
        <TextField source="lastName" />
        <ReferenceManyField label="Specialties" reference="specialty" target="vetId">
          <SingleFieldList linkType={false}>
            <ChipField source="name" />
          </SingleFieldList>
        </ReferenceManyField>
     </Datagrid>
    </List>
  )
}