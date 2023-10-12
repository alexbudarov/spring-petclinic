import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material"
import { Create, DateField, DateInput, Labeled, RecordContextProvider, ReferenceField, SaveButton, SimpleForm, TextField, TextInput, Toolbar, useGetList, useGetOne, useRecordContext } from "react-admin"

export const VisitCreate = () => {
    return <>
        <Create redirect="list">
            <Typography variant="subtitle1" sx={{marginLeft: '1em', fontWeight: 'bold'}}>Pet</Typography>
            <PetInfoTable />
            <SimpleForm toolbar={<CustomToolbar/>}>
                <DateInput source="date" autoFocus/>
                <TextInput source="description" required/>
            </SimpleForm>
            <Typography variant="subtitle1" sx={{marginLeft: '1em', fontWeight: 'bold'}}>Previous Visits</Typography>
            <PreviousVisitsTable />
        </Create>
    </>
}

const PetInfoTable = () => {
    const visit = useRecordContext();
    
    const { data: pet} = useGetOne('pet', { id: visit?.petId });

    return <>
        <RecordContextProvider value={pet}>
            <TableContainer >
                <Table size="small">
                    <TableHead>
                    <TableRow>
                        <TableCell>Name</TableCell>
                        <TableCell>Birth Date</TableCell>
                        <TableCell>Type</TableCell>
                        <TableCell>Owner</TableCell>
                    </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableRow>
                            <TableCell>
                                <TextField source="name"/>
                            </TableCell>
                            <TableCell>
                                <DateField source="birthDate"  options={{dateStyle: 'long'}}/>
                            </TableCell>
                            <TableCell>
                                <ReferenceField source="typeId" reference="pet-type" link={false}/>
                            </TableCell>
                            <TableCell>
                                <ReferenceField source="ownerId" reference="owner" link={false}/>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        </RecordContextProvider>
    </>
}

const PreviousVisitsTable = () => {
    const visit = useRecordContext();
    const petId = visit?.petId;

    const { data, total, isLoading, error } = useGetList(
        'visit',
        { 
            filter: {petId: petId}
        }
    );

    const previousVisits = data || [];

    return <>
        <TableContainer >
            <Table size="small">
                <TableHead>
                    <TableRow>
                        <TableCell>Date</TableCell>
                        <TableCell>Description</TableCell>
                    </TableRow>
                </TableHead>
                {previousVisits.map((visit: any) => (
                    <RecordContextProvider value={visit} key={visit.id}>
                        <TableBody>
                            <TableRow>
                            <TableCell>
                                <DateField source="date" options={{dateStyle: 'medium'}} />
                            </TableCell>
                            <TableCell>
                                <TextField source="description"/>
                            </TableCell>
                            </TableRow>            
                        </TableBody>
                    </RecordContextProvider>
                ))}
            </Table>
        </TableContainer>
    </>
}

const CustomSaveButton = () => (
    <SaveButton label="Add Visit"/>
);

const CustomToolbar = () => (
    <Toolbar>
        <CustomSaveButton />
    </Toolbar>
);
