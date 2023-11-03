import { AutocompleteInput, Datagrid, DateField, DateInput, List, ReferenceField, ReferenceInput, TextField, TextInput } from "react-admin"

const filters = [
    <TextInput label="Description" source="description" />,
    <DateInput label="Date after" source="dateAfter" />,
    <DateInput label="Date before" source="dateBefore" />,
    <ReferenceInput label="Pet" source="petId" reference="pet" sort={{ field: 'namelength', order: 'ASC' }}>
        <AutocompleteInput />
    </ReferenceInput>
];

export const VisitList = () => (
    <List filters={filters}>
        <Datagrid bulkActionButtons={false}>
            <DateField source="date" options={{dateStyle: 'medium'}} />
            <TextField source="description"/>
            <ReferenceField source="petId" reference="pet" sortable={false}/>
        </Datagrid>
    </List>
)