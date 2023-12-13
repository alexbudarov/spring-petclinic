import { AutocompleteInput, ReferenceInput, SimpleForm, Title, required } from "react-admin"
import { Typography } from "@mui/material"

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
