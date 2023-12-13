import { SimpleForm, Title } from "react-admin"
import { Typography } from "@mui/material"

export const VisitRequest = () => {
    return <>
        <Title title="Request Visit" />
        <SimpleForm 
          onSubmit={() => {}}
        >
            <Typography variant="h6">
                Enter visit details
            </Typography>
        </SimpleForm>
    </>
};
