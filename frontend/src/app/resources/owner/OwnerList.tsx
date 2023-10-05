import { Datagrid, FunctionField, List, TextField, TextInput } from "react-admin"

const filters = [
    <TextInput label="Last name" source="lastName" alwaysOn />,
];

export const OwnerList = () => (
    <List filters={filters}>
        <Datagrid rowClick="show" bulkActionButtons={false}>
            <TextField source="id"/>
            <TextField source="firstName"/>
            <TextField source="lastName"/>
            <TextField source="address"/>
            <TextField source="city"/>
            <TextField source="telephone"/>
            <FunctionField source="pets" sortable={false} render={(record: any) => {
                return (record.pets || []).map((p: any) => p.name).join(", ")
            }}
            />
        </Datagrid>
    </List>
)