import { Fragment } from "react";
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
    <Fragment>
        <BulkUpdateButton label="Postpone Tomorrow" 
            mutationMode="pessimistic" 
            confirmTitle="Postpone Visits"
            confirmContent="Selected visits will be postponed till tomorrow."
            data={{
                date: getTomorrow()
            }}
        />
        <BulkUpdateButton label="Clear Date" 
            mutationMode="pessimistic" 
            confirmTitle="Clear Date"
            confirmContent="Date for selected visits will be cleared"
            data={{
                date: null
            }}
        />
        <BulkUpdateButton label="Change description" 
            mutationMode="pessimistic" 
            confirmTitle="Change Description"
            confirmContent="Description for selected visits will be changed"
            data={{
                description: ("Description " + new Date().toTimeString())
            }}
        />
    </Fragment>
)

function getTomorrow(): Date {
    let date = new Date();
    date.setDate(date.getDate() + 1);
    return date;
}

export const VisitList = () => (
    <List filters={filters}>
        <Datagrid bulkActionButtons={<BulkActionButtons />}>
            <TextField source="id" />
            <DateField source="date" options={{dateStyle: 'medium'}} />
            <TextField source="description"/>
            <ReferenceField source="petId" reference="pet" sortable={false}/>
            <DeleteButton/>
        </Datagrid>
    </List>
)