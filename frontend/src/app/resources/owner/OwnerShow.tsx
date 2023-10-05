import { FunctionField, Show, ShowBase, SimpleShowLayout, TextField, Title, WithRecord, useRecordContext } from "react-admin";
import { Card, Grid, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from '@mui/material';
import { ownerRecordRepresentation } from "../../functions/ownerRecordRepresentation";

export const OwnerShow = () => {

  return <>
    <ShowBase>
      <Grid container spacing={2}>
        <WithRecord render={owner => 
            <Title title={"Owner '" + ownerRecordRepresentation(owner) + "'"} />
              }
         />
        <Grid item xs={8}>
          <Typography variant="h5">
              Owner Information
          </Typography>
        </Grid>
        <Grid item xs={8}>
          <OwnerFields />
        </Grid>
        <Grid item xs={8}>
          <Typography variant="h5">
            Pets and Visits
          </Typography>
        </Grid>
        <Grid item xs={8}>
          <Card>
          <SimpleShowLayout>
              <FunctionField source="pets" sortable={false} render={(record: any) => {
                return (record.pets || []).map((p: any) => p.name).join(", ");
              } } />
          </SimpleShowLayout>        
          </Card>
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
                <WithRecord render={owner => <span>{owner?.firstName + " " + owner?.lastName}</span>} />
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