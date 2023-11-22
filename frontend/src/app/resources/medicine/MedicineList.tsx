import { BooleanField, Datagrid, List, TextField, TextInput } from "react-admin"

const filters = [
    <TextInput label="Name" source="name" alwaysOn />
];

export const MedicineList = () => (
    <List filters={filters}>
        <Datagrid rowClick={false} bulkActionButtons={false}>
            <TextField source="name"/>
            <BooleanField source="safeForChildren" label="Safe for cubs"/>
        </Datagrid>
    </List>
)