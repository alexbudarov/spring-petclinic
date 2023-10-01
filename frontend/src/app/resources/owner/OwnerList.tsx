import { Datagrid, FunctionField, List, TextField } from "react-admin"

export const OwnerList = () => (
    <List>
        <Datagrid bulkActionButtons={false}>
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