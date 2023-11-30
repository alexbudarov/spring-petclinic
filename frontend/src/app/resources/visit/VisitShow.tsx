import { Show, SimpleShowLayout, TextField, DateField, RichTextField, ReferenceField } from 'react-admin';

export const VisitShow = () => (
  <Show>
    <SimpleShowLayout>
      <ReferenceField source="petId" reference="pet"/>
      <DateField source="date" options={{dateStyle: 'long'}} />
      <TextField source="description"/>
      <ReferenceField source="assignedVetId" reference="vet" sortBy="assignedVet.firstName"/>
    </SimpleShowLayout>
  </Show>
);
