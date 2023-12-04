import { AutocompleteInput, DateInput, ReferenceInput, SimpleForm, TextInput, Title, minValue, required, useCheckForApplicationUpdate } from "react-admin"
import { Typography } from "@mui/material"
import { useFormState, useFormContext } from "react-hook-form"
import { useEffect } from "react";

export const VisitRequest = () => {
    return <>
      <Title title="Request Visit" />
      <SimpleForm onSubmit={() => {}} maxWidth="30em">
        <Typography variant="h6">
          Enter visit details
        </Typography>
        <OwnerDropdown />
        <PetDropdown />
        <DateInput source="date" validate={[required(), minValue(tomorrowDate())]} />
        <TextInput source="description" validate={required()} helperText="e.g. Vaccination" fullWidth />
      </SimpleForm>
    </>
};

const OwnerDropdown = () => {
    const ownerFilterToQuery = (searchText: string) => ({ telephone: searchText });
    const ownerOptionRenderer = (owner: any) => `${owner.firstName} ${owner.lastName} (${owner.telephone})`;

    return (
        <ReferenceInput source="ownerId" reference="owner">
          <AutocompleteInput filterToQuery={ownerFilterToQuery} optionText={ownerOptionRenderer} fullWidth={true} validate={required()}/>
        </ReferenceInput>
    )
}

const PetDropdown = () => {
    const { watch, resetField } = useFormContext();    
    const ownerIdValue = watch('ownerId', null);

    // filter list of pets by owner
    const petFilter = ownerIdValue ? {'ownerId': ownerIdValue} : {};

    // reset pet if owner changes
    useEffect(() => {
        resetField('petId');
    }, [ownerIdValue]);

    return (
        <ReferenceInput 
            source="petId" 
            reference="pet"
            filter={petFilter}
        >
          <AutocompleteInput fullWidth={true} validate={required()} />
        </ReferenceInput>
    );
}

const tomorrowDate = () => {
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);

    const isoString = tomorrow.toISOString();
    const dateString = isoString.substring(0, isoString.indexOf('T')); // strip away time part
    return dateString;
}