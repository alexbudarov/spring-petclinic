import { Button, CreateButton, DateField, EditButton, FunctionField, Link, RecordContextProvider, Show, ShowBase, SimpleShowLayout, TextField, Title, WithRecord, useCreatePath, useRecordContext } from "react-admin";
import { Card, Divider, Grid, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from '@mui/material';
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
          <PetCards/>
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

const PetCards = () => {
  const owner = useRecordContext();
  const pets = owner?.pets ?? [];

return <>
    {pets.map((pet: any) => (
        <RecordContextProvider value={pet} key={pet.id}>
          <PetCard/>
        </RecordContextProvider>
    ))}
  </>
}

const PetCard = () => {
  return <>
  <Card variant="outlined">
    <Grid container spacing={2}>
      <Grid item xs={4}>
      <SimpleShowLayout>
        <TextField source="name"/>
        <DateField source="birthDate" options={{dateStyle: 'long'}} />
        <FunctionField source="type" render={(record: any) => `${record.type?.name}`} />
      </SimpleShowLayout>
      </Grid>
      <Grid item xs={8}>
        <VisitTable/>
      </Grid>
    </Grid>
  </Card>
  </>
}

const VisitTable = () => {
  const pet = useRecordContext();
  const visits = pet?.visits ?? [];

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
            {visits.map((visit: any) => (
              <RecordContextProvider value={visit} key={visit.id}>
                <TableRow>
                  <TableCell scope="row">
                    <TextField source="date"/>
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
                <AddVisitButton pet={pet} />
              </TableCell>
            </TableRow>                        
            </TableBody>
      </Table>
    </TableContainer>
  </>
}

const AddVisitButton = (pet: any) => {
  const createPath = useCreatePath();
  
  return <>
    <Button
      component={Link}
      to={{
        pathname: '/visit/create',
        state: { record: { pet: pet.id } },
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
  
  return <>
    <Button
      component={Link}
      to={{
        pathname: '/pet/create',
        state: { record: { owner: owner?.id } },
      }}
      label="Add pet"
      size="small"
    >
      <Add />
    </Button>
  </>
};