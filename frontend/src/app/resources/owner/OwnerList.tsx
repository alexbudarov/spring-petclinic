import { AutocompleteInput, Datagrid, List, ReferenceArrayField, ReferenceInput, TextField, TextInput } from "react-admin"

const filters = [
    <TextInput label="Last name" source="lastName" alwaysOn />,
    <ReferenceInput label="Pet type" source="petTypeId" reference="pet-type" alwaysOn>
        <AutocompleteInput />
    </ReferenceInput>
];

export const OwnerList = () => (
    <List filters={filters}>
        <Datagrid rowClick="show" bulkActionButtons={false}>
            <TextField source="firstName"/>
            <TextField source="lastName"/>
            <TextField source="address"/>
            <TextField source="city"/>
            <TextField source="telephone"/>
            <ReferenceArrayField label="Pets" reference="pet" source="petIds" sortable={false} />
        </Datagrid>
    </List>
)