import { Grid, Typography, CircularProgress } from "@mui/material";
import { Fragment, useCallback, useState } from "react";
import { AutocompleteInput, BulkUpdateButton, Datagrid, DateField, DateInput, DeleteButton, Identifier, List, RaRecord, RecordContextProvider, ReferenceField, ReferenceInput, RowClickFunction, SimpleShowLayout, TextField, TextInput, useGetOne, useListContext } from "react-admin"

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

export const VisitList = () => {
    return (
      <List filters={filters}>
        <VisitListInternal/>
      </List>
    )
}

const VisitListInternal = () => {
    const { data } = useListContext();
    const [selectedVisit, setSelectedVisit] = useState<RaRecord | null>(null);

    const onRowClick: RowClickFunction = useCallback((id: Identifier) => {
        const visit = (data || []).find(v => v.id === id);
        setSelectedVisit(visit);
        return false;
    }, [data, setSelectedVisit]);

    return (
        <Grid container spacing={2}>
        <Grid item xs={8}>
          <Datagrid bulkActionButtons={<BulkActionButtons />} rowClick={onRowClick}>
            <TextField source="id" />
            <DateField source="date" options={{dateStyle: 'medium'}} />
            <TextField source="description"/>
            <ReferenceField source="petId" reference="pet" sortable={false}/>
            <DeleteButton/>
          </Datagrid>
        </Grid>
        <Grid item xs={4}>
          <Typography variant="h6">Pet details</Typography>
          {selectedVisit && <PetDetails visit={selectedVisit} />}
        </Grid>
      </Grid>
    )
}

const PetDetails = ({visit} : {visit: RaRecord}) => {
    const petId = visit.petId;
    const { data: pet, isLoading, error } = useGetOne('pet', { id: petId });
    if (isLoading) {
        return <CircularProgress />
    }
    return (
      <RecordContextProvider value={pet}>
        <SimpleShowLayout>
          <TextField source="name"/>
          <DateField source="birthDate" options={{dateStyle: 'long'}} />
          <ReferenceField source="typeId" reference="pet-type"/>
        </SimpleShowLayout>
      </RecordContextProvider>
    )
}
