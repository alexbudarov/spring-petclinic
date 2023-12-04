import { ReferenceField, TextField } from "react-admin";

export function VisitOwnerTelephoneField({label} : {label: string}) {
  return (
    <ReferenceField label={label} source="petOwnerId" reference="owner" sortable={false} link={false}>
      <TextField source="telephone" />
    </ReferenceField>
  );
}

VisitOwnerTelephoneField.defaultProps = { label: 'Owner telephone' };
