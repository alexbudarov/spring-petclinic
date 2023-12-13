import { AutocompleteInput, ReferenceInput, SimpleForm, Title, required } from "react-admin"
import { Typography } from "@mui/material"
import { useFormContext } from "react-hook-form";
import { useEffect } from "react";

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