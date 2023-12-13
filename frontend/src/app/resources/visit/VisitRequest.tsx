import { AutocompleteInput, DateInput, ReferenceInput, SimpleForm, TextInput, Title, minValue, required } from "react-admin"
import { Typography } from "@mui/material"
import { useFormContext } from "react-hook-form";
import { useEffect } from "react";
import dayjs from "dayjs";

export const VisitRequest = () => {
    return <>
        <Title title="Request Visit" />
        <SimpleForm 
          maxWidth="30em" 
          onSubmit={() => {}}
        >
            <Typography variant="h6">
                Enter visit details
            </Typography>
            <OwnerDropdown />
            <PetDropdown />
            <ReferenceInput source="specialtyId" reference="specialty">
                <AutocompleteInput
                    fullWidth
                    validate={required()}
                />
            </ReferenceInput>            
            <DateInput source="date" validate={[required(), minValue(tomorrowDateStr(), "Must be in the future")]} />
            <TextInput source="description" helperText="e.g. Vaccination" fullWidth validate={required()} />
        </SimpleForm>
    </>
};

const OwnerDropdown = () => {
    const ownerFilterToQuery = (searchText: string) => ({ telephone: searchText });
    const ownerOptionRenderer = (owner: any) => `${owner.firstName} ${owner.lastName} (${owner.telephone})`;

    return (
        <ReferenceInput source="ownerId" reference="owner">
            <AutocompleteInput 
              filterToQuery={ownerFilterToQuery} 
              optionText={ownerOptionRenderer} 
              validate={required()}
              fullWidth
            />
        </ReferenceInput>
    )
}

const PetDropdown = () => {
    const { watch, resetField } = useFormContext();
    const ownerIdValue = watch('ownerId', null);

    // filter list of pets by owner
    const petFilter = ownerIdValue ? { 'ownerId': ownerIdValue } : {};

    // reset pet if owner changes
    useEffect(() => {
        resetField('petId');
    }, [ownerIdValue, resetField]);

    return (
        <ReferenceInput
            source="petId"
            reference="pet"
            filter={petFilter}
        >
            <AutocompleteInput fullWidth validate={required()} />
        </ReferenceInput>
    );
}

const tomorrowDateStr = () => {
    const now = dayjs();
    const tomorrow = now.add(1, 'day');
    return tomorrow.format("YYYY-MM-DD");
}
