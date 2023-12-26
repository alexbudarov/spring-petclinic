import { BulkUpdateButton, Datagrid, DateField, DateInput, List, NumberField, ReferenceField, ReferenceManyField, ReferenceOneField, ShowButton, TextField, TextInput } from "react-admin"

const filters = [
  <TextInput label="Description" source="description" />,
  <DateInput label="Date after" source="dateAfter" />,
  <DateInput label="Date before" source="dateBefore" />,
]

const BulkActionButtons = () => {
    return <>
        <BulkUpdateButton label="Postpone Tomorrow"
            mutationMode="pessimistic"
            confirmTitle="Postpone Visits"
            confirmContent="Selected visits will be postponed till tomorrow."
            data={{
                date: getTomorrow()
            }}
        />
        <BulkUpdateButton label="Clear Date"
            mutationMode="pessimistic"
            confirmTitle="Clear Date"
            confirmContent="Date for selected visits will be cleared"
            data={{
                date: null
            }}
        />
        <BulkUpdateButton label="Change description"
            mutationMode="pessimistic"
            confirmTitle="Change Description"
            confirmContent="Description for selected visits will be changed"
            data={{
                description: ("Description " + new Date().toTimeString())
            }}
        />
    </>
}

export const VisitList = () => {
  return (
      <List filters={filters}>
        <Datagrid bulkActionButtons={<BulkActionButtons />}>
          <TextField source="id" />
          <ReferenceField source="petId" reference="pet" sortBy="pet.name"/>
          <DateField source="date" options={{ dateStyle: 'medium' }} />
          <TextField source="description" />
          <ReferenceField source="assignedVetId" reference="vet" sortBy="assignedVet.firstName"/>
          {/* <ReferenceOneField label="Owner name" reference="owner" target="visitId">
            <TextField source="lastName" />
          </ReferenceOneField>
          <ReferenceOneField label="Owner address" reference="owner" target="visitId">
            <TextField source="address" />
          </ReferenceOneField>
          <ReferenceOneField label="Owner city" reference="owner" target="visitId">
            <TextField source="city" />
          </ReferenceOneField> */}
          <ReferenceField label="Owner name" source="petOwnerId" reference="owner" sortable={false} link={false} />
          <ReferenceField label="Owner telephone" source="petOwnerId" reference="owner" sortable={false} link={false}>
            <TextField source="telephone" />
          </ReferenceField>
          <ShowButton />
        </Datagrid>
      </List>
  );
}

function getTomorrow(): Date {
  let date = new Date();
  date.setDate(date.getDate() + 1);
  return date;
}
