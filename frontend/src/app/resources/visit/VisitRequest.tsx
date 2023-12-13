import { AutocompleteInput, DateInput, ReferenceInput, SaveButton, SimpleForm, TextInput, Title, Toolbar, minValue, required, useCreatePath, useDataProvider } from "react-admin"
import { Typography, Stack, Chip, Alert } from "@mui/material"
import { useFormContext } from "react-hook-form";
import { useEffect, useState } from "react";
import dayjs from "dayjs";
import { CustomDataProvider } from "../../../dataProvider";
import { useQuery } from 'react-query';
import { Link } from "react-router-dom";

export const VisitRequest = () => {
    return <>
        <Title title="Request Visit" />
        <SimpleForm
            maxWidth="30em"
            onSubmit={() => { }}
            toolbar={<CustomToolbar />}
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
            <DateBlock />
            <TextInput source="description" helperText="e.g. Vaccination" fullWidth validate={required()} />
        </SimpleForm>
    </>;
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

function DateBlock() {
    const { watch } = useFormContext();
    const specialtyIdValue = watch('specialtyId', null);
    const dateValue = watch('date', null);
    const parsedDate = dateValue ? parseDate(dateValue) : null;

    const [checkStatus, setCheckStatus] = useState<'available' | 'unavailable' | null>(null);

    const dataProvider = useDataProvider<CustomDataProvider>();

    useQuery({
        queryKey: ['checkAvailability', specialtyIdValue, parsedDate],
        queryFn: async () => {
            if (specialtyIdValue && parsedDate) {
                return await dataProvider.checkAvailability(specialtyIdValue, parsedDate!!);
            } else {
                return null;
            }
        },

        onSuccess: (result) => {
            setCheckStatus(result === null ? null : (result ? 'available' : 'unavailable'));
        },
        onError: () => {
            setCheckStatus(null);
        }
    });
    
    return (
        <Stack direction="row" spacing={2} sx={{ alignItems: "center" }}>
            <DateInput 
                source="date" 
                validate={[required(), minValue(tomorrowDateStr(), "Must be in the future")]} 
            />
            {checkStatus === 'available' &&
                <Chip label="Doctor is available" color="success"/>
            }
            {checkStatus === 'unavailable' &&
                <Chip label="Doctor isn't available for this date" color="warning"/>
            }
        </Stack>
    );
}

function parseDate(dateStr: string) {
    return dayjs(dateStr);
}

const CustomToolbar = () => {
    return (
        <Toolbar>
            <SaveButton
                label="Submit"
            />
            <RequestResultPanel />
        </Toolbar>
    );
}

const RequestResultPanel = () => {
    const createdVisitId = 12345;
    const createPath = useCreatePath();

    const visitUrl = createPath({ resource: 'visit', type: 'show', id: createdVisitId || 0 });
    return <>
        <Alert severity="success">
            Visit #{createdVisitId} created, <Link to={{ pathname: visitUrl }}>click to view.</Link>
        </Alert>
    </>
}
