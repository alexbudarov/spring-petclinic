import { Card } from "@mui/material";
import { AutocompleteArrayInput, ChipField, Datagrid, FilterForm, GetListParams, ListContextProvider, ListControllerResult, ReferenceArrayInput, ReferenceManyField, SingleFieldList, SortPayload, TextField, Title, useDataProvider } from "react-admin"
import { useQuery } from 'react-query';
import { CustomDataProvider } from "../../../dataProvider";
import { useState } from "react";


export const VetList = () => {
    const dataProvider = useDataProvider<CustomDataProvider>();

    const [filterValues, setFilterValues] = useState<any>({});
    const specialtyIds = filterValues.specialtyIds as number[];

    const { data: vets, isLoading, refetch } = useQuery(
        ['specialty', 'vetsBySpecialties', { ids: specialtyIds}], 
        () => {
          if (specialtyIds && specialtyIds.length > 0) {
            return dataProvider.vetsBySpecialties(specialtyIds)
          } else {
            return dataProvider.getList('vet', defaultGetListParams).then(a => a.data);
          }
        }
    );

    const filters = [
      <ReferenceArrayInput         
        source="specialtyIds"         
        reference="specialty"
        alwaysOn
        >
            <AutocompleteArrayInput label="Specialties"/>
        </ReferenceArrayInput>
    ];

    return <>
      <ListContextProvider value={{
                ...defaultListControllerResult,
                data: vets || [],
                total: vets?.length || 0,
                filterValues: filterValues,
                setFilters: setFilterValues,
                isLoading: isLoading,
                refetch: refetch,
                resource: 'vet',
            }}>
        <div>
          <Title title="Veterinarians" />
          <FilterForm filters={filters} />
          <Card>
            <Datagrid 
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
      </ListContextProvider>
    </>
}

const defaultGetListParams: GetListParams = {
    filter: {}, 
    pagination: {page: 1, perPage: 100}, 
    sort: {field: 'id', order: 'ASC'} as SortPayload
};

const defaultListControllerResult: ListControllerResult = {
    sort: { field: 'id', order: "ASC" },
    data: [],
    total: 0,
    filterValues: [],
    setFilters: () => {},
    isLoading: false,
    isFetching: false,
    displayedFilters: [],
    hideFilter: (f) => {},
    onSelect: (ids) => {},
    onToggleItem: (id) => {},
    onUnselectItems: () => {},
    page: 1,
    perPage: 100,
    refetch: () => {},
    resource: '',
    selectedIds: [],
    setPage: (p) => {},
    setPerPage: (pp) => {},
    setSort: (s) => {},
    showFilter: (fv, dv) => {},
    hasNextPage: false,
    hasPreviousPage: false
}
