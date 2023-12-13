import { Show, SimpleShowLayout, TextField, DateField, NumberField } from 'react-admin';

export const VisitShow = () => (
  <Show>
    <SimpleShowLayout>
      <NumberField source="petId" />
      <DateField source="date" />
      <TextField source="description" />
      <NumberField source="assignedVetId" />
    </SimpleShowLayout>
  </Show>
);
