import { ChipField, Datagrid, List, ReferenceManyField, SingleFieldList, TextField } from "react-admin"

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