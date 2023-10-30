import { Button, DateField, EditButton, FunctionField, Link, RaRecord, RecordContextProvider, ReferenceField, ShowBase, SimpleShowLayout, TextField, Title, WithRecord, useCreatePath, useGetManyReference, useRecordContext } from "react-admin";
import { Card, Grid, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from '@mui/material';
import { ownerRecordRepresentation } from "../../functions/ownerRecordRepresentation";
import { Add } from '@mui/icons-material';

export const OwnerShow = () => {
  return <>
    <ShowBase>
      <Grid container spacing={1} sx={{marginTop: "1em"}}>
        <WithRecord render={owner => 
            <Title title={"Owner '" + ownerRecordRepresentation(owner) + "'"} />
              }
         />
        <Grid item xs={8}>
          <Typography variant="h6">
              Owner Information
          </Typography>
        </Grid>
        <Grid item xs={8}>
          <OwnerFields />
        </Grid>
        <Grid item xs={4}/>
        <Grid item xs={1}>
          <EditButton label="Edit Owner"/>
        </Grid>
        <Grid item xs={1}>
          <AddPetButton/>
        </Grid>
        <Grid item xs={10}/>
        <Grid item xs={8}>
          <Typography variant="h6">
            Pets and Visits
          </Typography>
        </Grid>
        <Grid item xs={8}>
          <PetCardsSection/>
        </Grid>
      </Grid>
      </ShowBase>
    </>
}

const OwnerFields = () => (
  <TableContainer component={Paper}>
      <Table aria-label="simple table">
        <TableBody>
            <TableRow sx={{backgroundColor: "#f0f0f0"}}>
              <TableCell component="th" scope="row" sx={{ fontWeight: "bold" }}>
                Name
              </TableCell>
              <TableCell align="left">
                <FunctionField source="name" render={(record: any) => `${record.firstName} ${record.lastName}`} />
              </TableCell>
            </TableRow>
            <TableRow>
              <TableCell component="th" scope="row" sx={{ fontWeight: "bold" }}>
                Address
              </TableCell>
              <TableCell align="left">
                <TextField source="address"/>
              </TableCell>
            </TableRow>            
            <TableRow sx={{backgroundColor: "#f0f0f0"}}>
              <TableCell component="th" scope="row" sx={{ fontWeight: "bold" }}>
                City
              </TableCell>
              <TableCell align="left">
                <TextField source="city"/>
              </TableCell>
            </TableRow>            
            <TableRow>
              <TableCell component="th" scope="row" sx={{ fontWeight: "bold" }}>
                Telephone
              </TableCell>
              <TableCell align="left">
                <TextField source="telephone"/>            
              </TableCell>
            </TableRow>
            </TableBody>
      </Table>
    </TableContainer>
)

const PetCardsSection = () => {  
  const owner = useRecordContext();
  return <>
    {owner && <PetCards owner={owner} />}
  </>
}

const PetCards = ({owner}: {owner: RaRecord}) => {  
  const {data: pets} = useGetManyReference('pet',
    {
      target: 'ownerId', 
      id: owner.id,
      sort: {field: 'name', order: 'ASC'}
    }
  );

return <>
    {(pets || []).map((pet: any) => (
        <RecordContextProvider value={pet} key={pet.id}>
          <PetCard owner={owner}/>
        </RecordContextProvider>
    ))}
  </>
}

const PetCard = ({owner}: {owner: RaRecord}) => {
  return <>
  <Card variant="outlined">
    <Grid container spacing={2}>
      <Grid item xs={4}>
      <SimpleShowLayout>
        <TextField source="name"/>
        <DateField source="birthDate" options={{dateStyle: 'long'}} />
        <ReferenceField source="typeId" reference="pet-type"/>
      </SimpleShowLayout>
      </Grid>
      <Grid item xs={8}>
        <VisitTable owner={owner}/>
      </Grid>
    </Grid>
  </Card>
  </>
}

const VisitTable = ({owner}: {owner: RaRecord}) => {
  const pet = useRecordContext();

  const {data: visits} = useGetManyReference(
    'visit',
    {
      target: 'petId',
      id: pet?.id || 0,
      sort: {field: 'date', order: 'ASC'}
    }
  );

  return <>
    <TableContainer >
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Visit Date</TableCell>
            <TableCell>Description</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
            {(visits || []).map((visit: any) => (
              <RecordContextProvider value={visit} key={visit.id}>
                <TableRow>
                  <TableCell scope="row">
                    <DateField source="date" options={{dateStyle: 'medium'}}/>
                  </TableCell>
                  <TableCell align="left">
                    <TextField source="description"/>
                  </TableCell>
                </TableRow>            
              </RecordContextProvider>
            ))}
            <TableRow>
              <TableCell scope="row" sx={{border: 0}}>
                <EditButton resource="pet" record={pet} label="Edit Pet" size="small"/>
              </TableCell>
              <TableCell align="left" sx={{border: 0}}>
                <AddVisitButton pet={pet} owner={owner}/>
              </TableCell>
            </TableRow>                        
            </TableBody>
      </Table>
    </TableContainer>
  </>
}

const AddVisitButton = ({pet, owner}: {pet: RaRecord, owner: RaRecord}) => {
  const createPath = useCreatePath();

  return <>
    <Button
      component={Link}
      to={{
        pathname: createPath({resource: 'visit', type: 'create'}),
        search: '?owner=' + owner.id,
        state: { record: { petId: pet.id } },
      }}
      label="Add visit"
      size="small"
    >
      <Add />
    </Button>
  </>
};

const AddPetButton = () => {
  const owner = useRecordContext();
  const createPath = useCreatePath();
  
  return <>
    <Button
      component={Link}
      to={{
        pathname: createPath({resource: 'pet', type: 'create'}),
        state: { record: { ownerId: owner?.id } },
      }}
      label="Add pet"
      size="small"
    >
      <Add />
    </Button>
  </>
};