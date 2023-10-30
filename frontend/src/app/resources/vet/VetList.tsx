import { Card, Typography } from "@mui/material";
import { useState } from "react";
import { AutocompleteInput, ChipField, Datagrid, FunctionField, List, ListContextProvider, ReferenceInput, ReferenceManyField, SingleFieldList, SortPayload, TextField, Title, useDataProvider } from "react-admin"
import { useQuery } from 'react-query';
import { useForm, FormProvider } from 'react-hook-form';
import { CustomDataProvider } from "../../../dataProvider";


export const VetList = () => {
    const dataProvider = useDataProvider<CustomDataProvider>();
    const filterForm = useForm();
    const specialtyId = filterForm.watch("specialtyId");

    const { data: vets, isLoading } = useQuery(
        ['specialty', 'vetsBySpecialties', { ids: specialtyId ? [specialtyId]: [] }], 
        () => {
          if (specialtyId) {
            return dataProvider.vetsBySpecialties(specialtyId ? [specialtyId]: [])
          } else {
            const params = {filter: {}, pagination: {page: 1, perPage: 100}, sort: {field: 'id', order: 'ASC'} as SortPayload};
            return dataProvider.getList('vet', params).then(a => a.data);
          }
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
                <ReferenceManyField label="Specialties" reference="specialty" target="vetId">
                <SingleFieldList linkType={false}>
                    <ChipField source="name" />
                </SingleFieldList>
                </ReferenceManyField>
            </Datagrid>
        </Card>
      </div>
    </>
}