import { AutocompleteInput, Button, DateInput, Identifier, ReferenceInput, SaveButton, SimpleForm, TextInput, Title, Toolbar, minValue, required, useCreatePath, useDataProvider, useNotify } from "react-admin"
import { Typography, Stack, Tooltip, Alert, Chip } from "@mui/material"
import { useFormContext } from "react-hook-form"
import { useCallback, useEffect, useState } from "react";
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import EventBusyIcon from '@mui/icons-material/EventBusy';
import { CustomDataProvider, httpClient } from "../../../dataProvider";
import { useMutation } from 'react-query';
import { Link } from "react-router-dom";
import TFieldValues from "react-hook-form/dist/types/form";

type NewVisitRequest = {
    petId: number;
    specialtyId: number | null;
    vetId: number | null;
    date: string;
    description: string;
}

export const VisitRequest = () => {
    const [creationResult, setCreationResult] = useState<VisitCreationResult>(
        { success: false, createdVisitId: null }
    );
    const notify = useNotify();

    const { mutate: invokeRequestNewVisit } = useMutation(
        (request: NewVisitRequest) => {
            return httpClient(`/rest/visit/request`, {
                method: "POST",
                body: JSON.stringify(request)
            })
                .then(({ json }) => (json));
        },
        {
            onSuccess: (result) => {
                setCreationResult({
                    success: result.success,
                    createdVisitId: result.visitId
                });
                if (!result.success) {
                    notify(result.errorMessage || 'Error', { type: "warning" });
                }
                // reset form would be great
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

    const onSubmitForm = useCallback((data: Record<string, any>) => {
        const req: NewVisitRequest = {
            petId: data.petId,
            specialtyId: data.specialtyId,
            vetId: data.vetId,
            date: data.date, // date format is the same
            description: data.description
        }

        invokeRequestNewVisit(req);
    }, [invokeRequestNewVisit]);

    return <>
        <Title title="Request Visit" />
        <SimpleForm 
            onSubmit={onSubmitForm} 
            maxWidth="30em" 
            toolbar={<CustomToolbar {...creationResult} />}
            >
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

    const additionalValidation = (specialtyId: any, allValues: any) => {
        if (!specialtyId && !allValues.vetId) {
            return "Either Specialty or Specific Vet should be selected";
        }
        return undefined;
    };

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
                validate={additionalValidation}
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
        (args: {vetId: number, date: Date}) => { return dataProvider.checkAvailability(args.vetId, args.date).then(a => a); },
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
                doCheckAvailability({ vetId: vetIdValue, date: parsedDate })
            }
        } else {
            setCheckStatus(null);
        }
    }, [vetIdValue, dateValue, setCheckStatus]);

    return (
        <Stack direction="row" spacing={2} sx={{ alignItems: "center" }}>
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

type VisitCreationResult = {
    success: boolean;
    createdVisitId: Identifier | null;
}

const CustomToolbar = (creationResult: VisitCreationResult) => {
    const { resetField } = useFormContext();

    const resetAllValues = useCallback(() => {
        ['ownerId', 'petId', 'specialtyId', 'vetId', 'date', 'description'].forEach(element => {
            resetField(element);
        });
    }, [resetField]);

    return (
        <Toolbar>
            <SaveButton
                label="Submit"
            />
            <Button
                label="Reset"
                size="medium"
                sx={{ marginLeft: "1em" }}
                variant="outlined"
                onClick={resetAllValues}
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
            <Alert severity="success">Visit #{createdVisitId} created, <Link to={{ pathname: visitUrl }}>click to view.</Link></Alert>
        }
    </>
}

export default VisitCreationResult;
