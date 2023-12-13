import { Show, SimpleShowLayout, TextField, DateField, NumberField, ReferenceField } from 'react-admin';

export const VisitShow = () => (
  <Show>
    <SimpleShowLayout>
      <ReferenceField source="petId" reference="pet"/>
      <DateField source="date" options={{dateStyle: 'long'}} />
      <TextField source="description" />
      <NumberField source="assignedVetId" />
    </SimpleShowLayout>
  </Show>
);
