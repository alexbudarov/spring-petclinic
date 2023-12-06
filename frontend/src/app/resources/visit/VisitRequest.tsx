import { AutocompleteInput, Button, DateInput, ReferenceInput, SaveButton, SimpleForm, TextInput, Title, Toolbar, minValue, required, useDataProvider } from "react-admin"
import { Typography, Chip, Stack, Tooltip } from "@mui/material"
import { useFormContext } from "react-hook-form"
import { useCallback, useEffect, useState } from "react";
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import EventBusyIcon from '@mui/icons-material/EventBusy';
import { CheckAvailabilityArguments, CustomDataProvider } from "../../../dataProvider";
import { useMutation } from 'react-query';

export const VisitRequest = () => {
    return <>
        <Title title="Request Visit" />
        <SimpleForm onSubmit={() => { }} maxWidth="30em" toolbar={<CustomToolbar />}>
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
        if (ownerIdValue) {
          resetField('petId');
        }
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
    const vetIdValue = watch('vetId', null);
    const dateValue = watch('date', null);

    const [checkStatus, setCheckStatus] = useState<'available' | 'unavailable' | null>(null);

    const dataProvider = useDataProvider<CustomDataProvider>();
    const { mutate: doCheckAvailability } = useMutation(
        (args: CheckAvailabilityArguments) => { return dataProvider.checkAvailability(args).then(a => a); },
        {
            onSuccess: (result) => {
                setCheckStatus(result ? 'available' : 'unavailable');
            },
            onError: () => {
                setCheckStatus(null);
            }
        }
    );
    
    useEffect(() => {
        if (vetIdValue && dateValue) {
            const parsedDate = parseDate(dateValue);
            if (parsedDate) {
                doCheckAvailability({vetId: vetIdValue, date: parsedDate})
            }
        } else {
            setCheckStatus(null);
        }
    }, [vetIdValue, dateValue, setCheckStatus]);

    return (
      <Stack direction="row" spacing={2} sx={{alignItems: "center"}}>
        <DateInput source="date" validate={[required(), minValue(tomorrowDate())]} />
        {checkStatus === 'available' && 
          <Tooltip title="Doctor is available">
            <CheckBoxIcon color="success" />
          </Tooltip>
        }
        
        {checkStatus === 'unavailable' && 
          <Tooltip title="Doctor isn't available for this date">
            <EventBusyIcon color="warning" />
          </Tooltip>
        }
       </Stack>
    );
}

function parseDate(dateStr: string) {
    const timestamp = Date.parse(dateStr);
    if (isNaN(timestamp)) {
        return null;
    }
    const date = new Date();
    date.setTime(timestamp);
    return date;
}

const CustomToolbar = () => {
    const { resetField } = useFormContext();

    const resetAllValues = useCallback(() => {
        ['ownerId', 'petId', 'specialtyId', 'vetId', 'date', 'description'].forEach(element => {
            resetField(element);
        });
    }, [resetField]);

    return (
        <Toolbar>
            <SaveButton label="Submit" />
            <Button 
              label="Reset" 
              size="medium" 
              sx={{marginLeft: "1em"}} 
              variant="outlined"
              onClick={resetAllValues}
              />
        </Toolbar>
     );
}
 