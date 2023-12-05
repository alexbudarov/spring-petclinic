import { AutocompleteInput, DateInput, ReferenceInput, SimpleForm, TextInput, Title, minValue, required } from "react-admin"
import { Typography, Chip, Stack, Tooltip } from "@mui/material"
import { useFormContext } from "react-hook-form"
import { useEffect, useState } from "react";
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import EventBusyIcon from '@mui/icons-material/EventBusy';

export const VisitRequest = () => {
    return <>
        <Title title="Request Visit" />
        <SimpleForm onSubmit={() => { }} maxWidth="30em">
            <Typography variant="h6">
                Enter visit details
            </Typography>
            <OwnerDropdown />
            <PetDropdown />
            <SpecialtyDropdown />
            <VetDropdown />
            <DateBlock></DateBlock>
            <TextInput source="description" helperText="e.g. Vaccination" fullWidth validate={required()} />
        </SimpleForm>
    </>;
};

const OwnerDropdown = () => {
    const ownerFilterToQuery = (searchText: string) => ({ telephone: searchText });
    const ownerOptionRenderer = (owner: any) => `${owner.firstName} ${owner.lastName} (${owner.telephone})`;

    return (
        <ReferenceInput source="ownerId" reference="owner">
            <AutocompleteInput filterToQuery={ownerFilterToQuery} optionText={ownerOptionRenderer} fullWidth={true} validate={required()} />
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

function SpecialtyDropdown() {
    const { watch, resetField } = useFormContext();
    const vetIdValue = watch('vetId', null);

    // reset specialty if vet becomes set
    useEffect(() => {
        if (vetIdValue) {
            resetField('specialtyId');
        }
    }, [vetIdValue, resetField]);

    return (
        <ReferenceInput source="specialtyId" reference="specialty">
            <AutocompleteInput
                fullWidth
                helperText="If selected: request any available vet with this specialty"
            />
        </ReferenceInput>
    );
}

const VetDropdown = () => {
    const { watch, resetField } = useFormContext();
    const specialtyIdValue = watch('specialtyId', null);

    // reset vet if specialty becomes set
    useEffect(() => {
        if (specialtyIdValue) {
            resetField('vetId');
        }
    }, [specialtyIdValue, resetField]);

    return (
        <ReferenceInput
            source="vetId"
            reference="vet"
        >
            <AutocompleteInput fullWidth label="Specific Vet" />
        </ReferenceInput>
    );
}

function DateBlock() {
    const { watch } = useFormContext();
    const specialtyIdValue = watch('specialtyId', null);
    const dateValue = watch('date', null);

    const [available, setAvailable] = useState<boolean>(false);
    const [unavailable, setUnavailable] = useState<boolean>(false);

    useEffect(() => {
        // todo use proper logic
        const newAvailable: boolean = specialtyIdValue && dateValue;
        setAvailable(newAvailable);
    }, [specialtyIdValue, dateValue, setAvailable]);

    return (
      <Stack direction="row" spacing={2} sx={{alignItems: "center"}}>
        <DateInput source="date" validate={[required(), minValue(tomorrowDate())]} />
        {available && 
          <Tooltip title="Vets are available">
            <CheckBoxIcon color="success" />
          </Tooltip>
        }
        
        {unavailable && 
          <Tooltip title="No vets available for this date">
            <EventBusyIcon color="warning" />
          </Tooltip>
        }
       </Stack>
    );
}
