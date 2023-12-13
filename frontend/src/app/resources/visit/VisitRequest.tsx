import { AutocompleteInput, DateInput, Identifier, ReferenceInput, SaveButton, SimpleForm, TextInput, Title, Toolbar, minValue, required, useCreatePath, useDataProvider, useNotify } from "react-admin"
import { Typography, Stack, Chip, Alert } from "@mui/material"
import { useFormContext } from "react-hook-form";
import { useEffect, useState } from "react";
import dayjs from "dayjs";
import { CustomDataProvider } from "../../../dataProvider";
import { useQuery, useMutation } from 'react-query';
import { Link } from "react-router-dom";

type VisitCreationResult = {
    success: boolean;
    createdVisitId: Identifier | null;
}

export const VisitRequest = () => {
    const [creationResult, setCreationResult] = useState<VisitCreationResult>(
        { success: false, createdVisitId: null }
    );

    const notify = useNotify();
    const dataProvider = useDataProvider<CustomDataProvider>();

    const { mutate: invokeRequestNewVisit } = useMutation(
        dataProvider.requestVisit,
        {
            onSuccess: (result) => {
                setCreationResult({
                    success: result.success,
                    createdVisitId: result.visitId
                });
                if (!result.success) {
                    notify(result.errorMessage || 'Error', { type: "warning" });
                }
            },
            onError: (error: any) => {
                setCreationResult({
                    success: false,
                    createdVisitId: null
                });
                notify('Error', { type: "error" });
            }
        }
    );    

    function doSubmitForm(data: Record<string, any>) {
        invokeRequestNewVisit({
                petId: data.petId,
                specialtyId: data.specialtyId,
                date: data.date, // date format is the same
                description: data.description
        });
    }

    return <>
        <Title title="Request Visit" />
        <SimpleForm
            maxWidth="30em"
            onSubmit={doSubmitForm}
            toolbar={<CustomToolbar {...creationResult} />}
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

const CustomToolbar = (creationResult: VisitCreationResult) => {
    return (
        <Toolbar>
            <SaveButton
                label="Submit"
            />
            <RequestResultPanel {...creationResult} />
        </Toolbar>
    );
}

const RequestResultPanel = ({ success, createdVisitId }: VisitCreationResult) => {
    const createPath = useCreatePath();

    const visitUrl = createPath({ resource: 'visit', type: 'show', id: createdVisitId || 0 });
    return <>
        {success && createdVisitId &&
            <Alert severity="success">
                Visit #{createdVisitId} created, <Link to={{ pathname: visitUrl }}>click to view.</Link>
            </Alert>
        }
    </>
}

