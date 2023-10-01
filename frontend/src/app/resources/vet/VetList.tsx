import { Datagrid, FunctionField, List, TextField } from "react-admin"

export const VetList = () => (
    <List>
        <Datagrid bulkActionButtons={false}>
            <TextField source="id"/>
            <TextField source="firstName"/>
            <TextField source="lastName"/>        
            <FunctionField source="specialties" sortable={false} render={(record: any) => {
                return (record.specialties || []).map((s: any) => s.name).join(", ")
            }}
            />
        </Datagrid>
    </List>
)