import { AutocompleteInput, BulkDeleteButton, Datagrid, List, ReferenceArrayField, ReferenceInput, TextField, TextInput } from "react-admin"

export const VetList = () => (
    <List>
        <Datagrid rowClick={false} bulkActionButtons={false}>
            <TextField source="firstName"/>
            <TextField source="lastName"/>
        </Datagrid>
    </List>
)