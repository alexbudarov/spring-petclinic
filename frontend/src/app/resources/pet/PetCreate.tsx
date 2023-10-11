import { AutocompleteInput, Create, DateInput, FunctionField, ReferenceInput, SimpleForm, TextInput } from "react-admin"
import { ownerRecordRepresentation } from "../../functions/ownerRecordRepresentation"

export const PetCreate = () => {
    return <>
        <Create>
            <SimpleForm>
                <FunctionField source="owner" render={(record: any) => `${ownerRecordRepresentation(record.owner)}`} />
                <TextInput source="name" required autoFocus/>
                <DateInput source="birthDate" required/>
                <ReferenceInput source="type" reference="pet-type" required>
                    <AutocompleteInput optionText="name" />
                </ReferenceInput>
            </SimpleForm>
        </Create>
    </>
}