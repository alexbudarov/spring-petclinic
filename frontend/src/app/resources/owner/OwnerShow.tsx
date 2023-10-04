import { FunctionField, Show, SimpleShowLayout, TextField } from "react-admin";

export const OwnerShow = () => (
    <Show>
        <SimpleShowLayout>
            <TextField source="firstName"/>
            <TextField source="lastName"/>
            <TextField source="address"/>
            <TextField source="city"/>
            <TextField source="telephone"/>            
            <FunctionField source="pets" sortable={false} render={(record: any) => {
                return (record.pets || []).map((p: any) => p.name).join(", ")
            }}
            />
        </SimpleShowLayout>
    </Show>
)