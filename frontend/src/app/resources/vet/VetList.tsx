import { Card } from "@mui/material";
import { ChipField, Datagrid, ReferenceArrayInput, ReferenceManyField, SingleFieldList, SortPayload, TextField, Title, useDataProvider } from "react-admin"
import { useQuery } from 'react-query';
import { useForm, FormProvider } from 'react-hook-form';
import { CustomDataProvider } from "../../../dataProvider";


export const VetList = () => {
    const dataProvider = useDataProvider<CustomDataProvider>();
    const filterForm = useForm();
    const specialtyIds = filterForm.watch("specialtyIds");

    const { data: vets, isLoading } = useQuery(
        ['specialty', 'vetsBySpecialties', { ids: specialtyIds}], 
        () => {
          if (specialtyIds && specialtyIds.length > 0) {
            return dataProvider.vetsBySpecialties(specialtyIds)
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
          <ReferenceArrayInput 
            source="specialtyIds" 
            reference="specialty"/>
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