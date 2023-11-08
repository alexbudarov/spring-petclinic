import { AutocompleteInput, BulkUpdateButton, Datagrid, DateField, DateInput, DeleteButton, List, ReferenceField, ReferenceInput, TextField, TextInput } from "react-admin"

const filters = [
    <TextInput label="Description" source="description" />,
    <DateInput label="Date after" source="dateAfter" />,
    <DateInput label="Date before" source="dateBefore" />,
    <ReferenceInput label="Pet" source="petId" reference="pet" sort={{ field: 'namelength', order: 'ASC' }}>
        <AutocompleteInput />
    </ReferenceInput>
];

const BulkActionButtons = () => (
    <BulkUpdateButton label="Postpone Tomorrow" 
        mutationMode="pessimistic" 
        confirmTitle="Postpone Visits"
        confirmContent="Selected visits will be postponed till tomorrow."
        data={{
            date: getTomorrow()
        }}
    />
)

function getTomorrow(): Date {
    let date = new Date();
    date.setDate(date.getDate() + 1);
    return date;
}

export const VisitList = () => (
    <List filters={filters}>
        <Datagrid bulkActionButtons={<BulkActionButtons />}>
            <DateField source="date" options={{dateStyle: 'medium'}} />
            <TextField source="description"/>
            <ReferenceField source="petId" reference="pet" sortable={false}/>
            <DeleteButton/>
        </Datagrid>
    </List>
)