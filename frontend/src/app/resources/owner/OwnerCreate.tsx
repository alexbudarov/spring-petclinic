import { Create, SimpleForm, TextInput } from "react-admin"

export const OwnerCreate = () => {
    return <>
        <Create redirect="list">
            <SimpleForm>
                <TextInput source="firstName" required autoFocus/>
                <TextInput source="lastName" required/>
                <TextInput source="address" required/>
                <TextInput source="city" required/>
                <TextInput source="telephone" required/>
            </SimpleForm>
        </Create>
    </>
}