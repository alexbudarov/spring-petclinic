import { BulkDeleteButton, Datagrid, EditButton, List, TextField, TextInput } from "react-admin"

const filters = [
    <TextInput label="Last name" source="lastName" alwaysOn />,
    <TextInput label="Telephone" source="telephone" alwaysOn />
];

export const OwnerList = () => (
    <List filters={filters}>
        <Datagrid rowClick="show" bulkActionButtons={<BulkDeleteButton mutationMode="pessimistic"/>}>
            <TextField source="firstName"/>
            <TextField source="lastName"/>
            <TextField source="address"/>
            <TextField source="city"/>
            <TextField source="telephone"/>
            <TextField source="petIds"/>
            <EditButton />
        </Datagrid>
    </List>
)