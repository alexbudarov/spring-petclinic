import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material"
import { useCallback } from "react";
import { Create, DateField, DateInput, RecordContextProvider, ReferenceField, SaveButton, SimpleForm, TextField, TextInput, Toolbar, useGetList, useGetOne, useRecordContext } from "react-admin"
import { useLocation } from "react-router-dom";

export const VisitCreate = () => {
    const location = useLocation();
    const ownerId = new URLSearchParams(location.search).get("owner");

    const redirectToOwner = useCallback((resource: any, id: any, data: any) => {
        if (ownerId) {
          return 'owner/' + ownerId + '/show';
        }
        return ''
    }, [ownerId]);

    return <>
        <Create redirect={redirectToOwner}>
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
            filter: {petId: petId},
            sort: {field: 'date', order: 'ASC'}
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
