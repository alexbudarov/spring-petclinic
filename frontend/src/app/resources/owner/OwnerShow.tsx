import { Show, SimpleShowLayout, TextField, DateField, RichTextField } from 'react-admin';

export const OwnerShow = () => (
  <Show>
    <SimpleShowLayout>
      <TextField source="firstName"/>
      <TextField source="lastName"/>
      <TextField source="address"/>
      <TextField source="city"/>
      <TextField source="telephone"/>
      <TextField source="petIds"/>
    </SimpleShowLayout>
  </Show>
);
