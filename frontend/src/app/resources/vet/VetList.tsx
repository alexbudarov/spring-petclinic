import { Card, Typography } from "@mui/material";
import { useState } from "react";
import { AutocompleteInput, Datagrid, FunctionField, List, ListContextProvider, ReferenceInput, TextField, Title, useDataProvider } from "react-admin"
import { useQuery } from 'react-query';
import { useForm, FormProvider } from 'react-hook-form';


export const VetList = () => {
    const dataProvider = useDataProvider();
    const filterForm = useForm();
    const specialtyId = filterForm.watch("specialtyId");

    const { data: vets, isLoading } = useQuery(
        ['specialty', 'vetsBySpecialties', { ids: specialtyId ? [specialtyId]: [] }], 
        () => {
          return dataProvider.vetsBySpecialties(specialtyId ? [specialtyId]: [])
        }
    );

    return <>
      <div>
        <Title title="Veterinarian list" />
        <FormProvider {...filterForm}>
          <ReferenceInput label="Specialty" source="specialtyId" 
              reference="specialty"
          >
            <AutocompleteInput />
          </ReferenceInput>
        </FormProvider>
        <Card>
            <Datagrid 
              data={vets || []}
              isLoading={isLoading}
              sort={{ field: 'id', order: "ASC" }}
              bulkActionButtons={false}>
                <TextField source="firstName" sortable={false}/>
                <TextField source="lastName" sortable={false}/>
                <FunctionField source="specialties" sortable={false} render={(record: any) => {
                    return (record.specialties || []).map((s: any) => s.name).join(", ")
                }}
                />
            </Datagrid>
        </Card>
      </div>
    </>
}