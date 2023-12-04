import { Show, SimpleShowLayout, TextField, DateField, RichTextField, ReferenceField } from 'react-admin';
import { VisitOwnerTelephoneField } from './VisitOwnerTelephoneField';

export const VisitShow = () => (
  <Show>
    <SimpleShowLayout>
      <ReferenceField source="petId" reference="pet"/>
      <DateField source="date" options={{dateStyle: 'long'}} />
      <TextField source="description"/>
      <ReferenceField source="assignedVetId" reference="vet" sortBy="assignedVet.firstName"/>
      <ReferenceField label="Owner name" source="petOwnerId" reference="owner" sortable={false} link={false}/>
      <VisitOwnerTelephoneField />
    </SimpleShowLayout>
  </Show>
);
