import { Edit, SimpleForm, TextInput } from "react-admin";

export const OwnerEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="firstName"/>
            <TextInput source="lastName"/>
            <TextInput source="address"/>
            <TextInput source="city"/>
            <TextInput source="telephone"/>
        </SimpleForm>
    </Edit>
)