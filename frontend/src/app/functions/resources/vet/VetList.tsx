import { AutocompleteInput, BulkDeleteButton, ChipField, Datagrid, List, ReferenceArrayField, ReferenceInput, ReferenceManyField, SingleFieldList, TextField, TextInput } from "react-admin"

export const VetList = () => (
    <List>
        <Datagrid rowClick={false} bulkActionButtons={false}>
            <TextField source="firstName"/>
            <TextField source="lastName"/>
            <ReferenceManyField label="Specialties" reference="specialty" target="vetId">
                <SingleFieldList linkType={false}>
                  <ChipField source="name" />
                </SingleFieldList>
            </ReferenceManyField>
        </Datagrid>
    </List>
)