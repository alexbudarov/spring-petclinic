import React from "react";
import {Fragment} from "react";
import {
    Category,
    Component,
    Variant,
    Palette,
} from "@amplicode/ide-toolbox";
import MUIPalette from "@amplicode/palette-mui";
import {
    Authenticated,
    Button,
    CreateButton,
    Datagrid,
    EditGuesser,
    ExportButton,
    FilterButton,
    List,
    ListGuesser,
    Loading,
    Pagination,
    Resource,
    ShowGuesser,
    SimpleList,
    TextField,
    TextInput,
    TopToolbar,
    WithPermissions,
    useAuthProvider,
    useAuthState,
    useAuthenticated,
    useCreate,
    useDataProvider,
    useDelete,
    useDeleteMany,
    useGetIdentity,
    useGetList,
    useGetMany,
    useGetManyReference,
    useGetOne,
    useInfiniteGetList,
    useListContext,
    useLogin,
    useLogout,
    usePermissions,
    useRecordContext,
    useUpdate,
    useUpdateMany,
    EditButton,
    DatagridConfigurable,
    SimpleListConfigurable,
    SingleFieldList,
    ChipField,
    FilterList,
    FilterListItem,
    FilterLiveSearch,
    SavedQueriesList,
    SortButton,
    SelectColumnsButton,
    Count,
    useList,
    useListController,
    useUnselect,
    useUnselectAll,
    Create,
    SimpleForm,
    CreateBase,
    Edit,
    EditBase,
    TabbedForm,
    SaveButton,
    TabbedFormTabs,
    Form,
    Toolbar,
    useSaveContext,
    useCreateContext,
    useCreateController,
    useEditContext,
    useEditController,
    SimpleShowLayout,
    Show,
    ShowBase,
    TabbedShowLayout,
    Labeled,
    useShowContext,
    useShowController,
    WithRecord,
    useGetRecordId,
    useNotify,
    useRedirect,
    useRefresh,
    useStore,
    useRemoveFromStore,
    useResetStore,
    useStoreContext,
    Configurable,
    ToggleThemeButton,
    useTranslate,
    useLocaleState,
    LocalesMenuButton,
    Layout,
    AppBar,
    Menu,
    Title,
    Confirm,
    ListButton,
    ShowButton,
    BulkExportButton,
    BulkDeleteButton,
    DeleteButton,
    DeleteWithConfirmButton,
    CloneButton,
    ArrayField,
    BooleanField,
    DateField,
    EmailField,
    FileField,
    FunctionField,
    ImageField,
    NumberField,
    ReferenceField,
    ReferenceArrayField,
    ReferenceManyField,
    ReferenceManyCount,
    ReferenceOneField,
    RichTextField,
    SelectField,
    TranslatableFields,
    UrlField,
    WrapperField,
    ArrayInput,
    SimpleFormIterator,
    BooleanInput,
    CheckboxGroupInput,
    DateInput,
    minValue,
    DateTimeInput,
    FileInput,
    ImageInput,
    NullableBooleanInput,
    PasswordInput,
    RadioButtonGroupInput,
    ReferenceInput,
    SelectInput,
    ReferenceArrayInput,
    AutocompleteArrayInput,
    AutocompleteInput,
    SelectArrayInput,
    required,
    TimeInput,
    TranslatableInputs,
    useInput,
    NumberInput, RaRecord,
} from "react-admin";
import { useParams } from "react-router";
import { useState } from "react";

export const PaletteTree = () => (
    <Palette>
        <Category name="App">
            <Component name="Loader">
                <Variant>
                    <ExampleLoaderComponent/>
                </Variant>
            </Component>
        </Category>
        <MUIPalette/>

        {/*React Admin*/}

        <Category name="Resources">
            <Component
                name="Resource"
                docURL="https://marmelab.com/react-admin/Resource.html"
            >
                <Variant>
                    <Resource
                        name="recordName"
                        edit={EditGuesser}
                        list={ListGuesser}
                        show={ShowGuesser}
                    />
                </Variant>
            </Component>
            <Component
                name="useDataProvider"
                docURL="https://marmelab.com/react-admin/useDataProvider.html"
            >
                <Variant proto={UseDataProviderProto} />
            </Component>
            <Component
                name="useGetList"
                docURL="https://marmelab.com/react-admin/useGetList.html"
            >
                <Variant proto={UseGetListProto} />
            </Component>
            <Component
                name="useInfiniteGetList"
                docURL="https://marmelab.com/react-admin/useInfiniteGetList.html"
            >
                <Variant proto={UseInfiniteGetListProto} />
            </Component>
            <Component
                name="useGetOne"
                docURL="https://marmelab.com/react-admin/useGetOne.html"
            >
                <Variant proto={UseGetOneProto} />
            </Component>
            <Component
                name="useGetMany"
                docURL="https://marmelab.com/react-admin/useGetMany.html"
            >
                <Variant proto={UseGetManyProto} />
            </Component>
            <Component
                name="useGetManyReference"
                docURL="https://marmelab.com/react-admin/useGetManyReference.html"
            >
                <Variant proto={UseGetManyReferenceProto} />
            </Component>
            <Component
                name="useCreate"
                docURL="https://marmelab.com/react-admin/useCreate.html"
            >
                <Variant proto={UseCreateProto} />
            </Component>
            <Component
                name="useUpdate"
                docURL="https://marmelab.com/react-admin/useUpdate.html"
            >
                <Variant proto={UseUpdateProto} />
            </Component>
            <Component
                name="useUpdateMany"
                docURL="https://marmelab.com/react-admin/useUpdateMany.html"
            >
                <Variant proto={UseUpdateManyProto} />
            </Component>
            <Component
                name="useDelete"
                docURL="https://marmelab.com/react-admin/useDelete.html"
            >
                <Variant proto={UseDeleteProto} />
            </Component>
            <Component
                name="useDeleteMany"
                docURL="https://marmelab.com/react-admin/useDeleteMany.html"
            >
                <Variant proto={UseDeleteManyProto} />
            </Component>
        </Category>
        <Category name="Security">
            <Component
                name="Authenticated"
                docURL="https://marmelab.com/react-admin/Authenticated.html"
            >
                <Variant>
                    <Authenticated>
                        {/* Use it as an alternative to the useAuthenticated() hook when you can’t use a hook  */}
                    </Authenticated>
                </Variant>
            </Component>
            <Component
                name="WithPermissions"
                docURL="https://marmelab.com/react-admin/WithPermissions.html"
            >
                <Variant>
                    <WithPermissions />
                </Variant>
            </Component>
            <Component
                name="useAuthProvider"
                docURL="https://marmelab.com/react-admin/useAuthProvider.html"
            >
                <Variant proto={UseAuthProviderProto} />
            </Component>
            <Component
                name="useAuthenticated"
                docURL="https://marmelab.com/react-admin/useAuthenticated.html"
            >
                <Variant proto={UseAuthenticatedProto} />
            </Component>
            <Component
                name="useAuthState"
                docURL="https://marmelab.com/react-admin/useAuthState.html"
            >
                <Variant proto={UseAuthStateProto} />
            </Component>
            <Component
                name="useGetIdentity"
                docURL="https://marmelab.com/react-admin/useGetIdentity.html"
            >
                <Variant proto={UseGetIdentityProto} />
            </Component>
            <Component
                name="useLogin"
                docURL="https://marmelab.com/react-admin/useLogin.html"
            >
                <Variant proto={UseLoginProto} />
            </Component>
            <Component
                name="useLogout"
                docURL="https://marmelab.com/react-admin/useLogout.html"
            >
                <Variant proto={UseLogoutProto} />
            </Component>
            <Component
                name="usePermissions"
                docURL="https://marmelab.com/react-admin/usePermissions.html"
            >
                <Variant proto={UsePermissionsProto} />
            </Component>
        </Category>
        <Category name="List">
            <Component
                name="List"
                docURL="https://marmelab.com/react-admin/List.html"
            >
                <Variant>
                    <List>
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="actions">
                    <List
                        actions={
                            <TopToolbar>
                                <FilterButton />
                                <CreateButton />
                                <ExportButton />
                                {/* Add your custom actions */}
                                <Button
                                    onClick={() => {
                                        alert("Your custom action");
                                    }}
                                    label="Custom button"
                                />
                            </TopToolbar>
                        }
                    >
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="aside">
                    <List aside={<div>Aside content</div>}>
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="empty">
                    <List empty={<div>Not found</div>}>
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="emptyWhileLoading">
                    <List emptyWhileLoading>
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="exporter">
                    <List exporter={(data) => {}}>
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="filters">
                    <List
                        filters={[
                            <TextInput label="Search" source="sourceName" alwaysOn />,
                        ]}
                        filterDefaultValues={{ sourceName: true }}
                    >
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="hasCreate">
                    <List hasCreate={false}>
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="pagination">
                    <List
                        pagination={<Pagination rowsPerPageOptions={[10, 25, 50, 100]} />}
                    >
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="perPage">
                    <List perPage={6}>
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="resource">
                    <List resource="recordName">
                        <Datagrid>
                            <TextField source="id" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="sort">
                    <List sort={{ field: "sourceName", order: "DESC" }}>
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
                <Variant name="title">
                    <List title="Title">
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </List>
                </Variant>
            </Component>
            <Component
                name="ListGuesser"
                docURL="https://marmelab.com/react-admin/ListGuesser.html"
            >
                <Variant>
                    <ListGuesser />
                </Variant>
            </Component>
            <Component
                name="Datagrid"
                docURL="https://marmelab.com/react-admin/Datagrid.html"
            >
                <Variant>
                    <Datagrid></Datagrid>
                </Variant>
                <Variant name="bulkActionButtons">
                    <Datagrid
                        bulkActionButtons={<>{/* Add your custom actions */}</>}
                    ></Datagrid>
                </Variant>
                <Variant name="empty">
                    <Datagrid empty={<div>Not found</div>}></Datagrid>
                </Variant>
                <Variant name="expand">
                    <Datagrid expand={<div>Expand content</div>}></Datagrid>
                </Variant>
                <Variant name="expandSingle">
                    <Datagrid expand={<div>Expand content</div>} expandSingle></Datagrid>
                </Variant>
                <Variant name="header">
                    <Datagrid header={<div>Header content</div>}></Datagrid>
                </Variant>
                <Variant name="hover">
                    <Datagrid hover={false}></Datagrid>
                </Variant>
                <Variant name="isRowExpandable">
                    <Datagrid
                        expand={<div>Expand content</div>}
                        isRowExpandable={(row) => row.sourceName}
                    ></Datagrid>
                </Variant>
                <Variant name="isRowSelectable">
                    <Datagrid isRowSelectable={(row) => row.sourceName}></Datagrid>
                </Variant>
                <Variant name="optimized">
                    <Datagrid optimized></Datagrid>
                </Variant>
                <Variant name="rowStyle">
                    <Datagrid
                        rowStyle={(record, index) => ({
                            backgroundColor: record.sourceName ? "red" : "blue",
                        })}
                    ></Datagrid>
                </Variant>
                <Variant name="rowClick">
                    <Datagrid rowClick="edit"></Datagrid>
                </Variant>
                <Variant name="size">
                    <Datagrid size="medium"></Datagrid>
                </Variant>
                <Variant name="DatagridConfigurable">
                    <DatagridConfigurable></DatagridConfigurable>
                </Variant>
            </Component>
            <Component
                name="SimpleList"
                docURL="https://marmelab.com/react-admin/SimpleList.html"
            >
                <Variant>
                    <SimpleList
                        primaryText={(record) => record.sourceName}
                        secondaryText={(record) => record.sourceName}
                    />
                </Variant>
                <Variant name="tertiaryText">
                    <SimpleList
                        primaryText={(record) => record.sourceName}
                        secondaryText={(record) => record.sourceName}
                        tertiaryText={(record) => record.sourceName}
                    />
                </Variant>
                <Variant name="linkType">
                    <SimpleList
                        primaryText={(record) => record.sourceName}
                        secondaryText={(record) => record.sourceName}
                        linkType={(record) => (record.sourceName ? "edit" : "show")}
                    />
                </Variant>
                <Variant name="rowStyle">
                    <SimpleList
                        primaryText={(record) => record.sourceName}
                        secondaryText={(record) => record.sourceName}
                        rowStyle={(record) => ({
                            backgroundColor: record.sourceName ? "red" : "blue",
                        })}
                    />
                </Variant>
                <Variant name="empty">
                    <SimpleList
                        primaryText={(record) => record.sourceName}
                        secondaryText={(record) => record.sourceName}
                        empty={<div>Empty content</div>}
                    />
                </Variant>
                <Variant name="SimpleListConfigurable">
                    <SimpleListConfigurable
                        primaryText={(record) => record.sourceName}
                        secondaryText={(record) => record.sourceName}
                    />
                </Variant>
            </Component>
            <Component
                name="SingleFieldList"
                docURL="https://marmelab.com/react-admin/SingleFieldList.html"
            >
                <Variant>
                    <SingleFieldList>
                        <ChipField source="sourceName" />
                    </SingleFieldList>
                </Variant>
                <Variant name="linkType">
                    <SingleFieldList linkType="show">
                        <ChipField source="sourceName" />
                    </SingleFieldList>
                </Variant>
            </Component>
            <Component
                name="FilterButton"
                docURL="https://marmelab.com/react-admin/FilterButton.html"
            >
                <Variant>
                    <FilterButton
                        filters={[
                            <TextInput label="Search" source="sourceName" alwaysOn />,
                        ]}
                    />
                </Variant>
                <Variant name="disableSaveQuery">
                    <FilterButton
                        filters={[
                            <TextInput label="Search" source="sourceName" alwaysOn />,
                        ]}
                        disableSaveQuery
                    />
                </Variant>
            </Component>
            <Component
                name="FilterList"
                docURL="https://marmelab.com/react-admin/FilterList.html"
            >
                <Variant>
                    <FilterList label="Label" icon={<span />}>
                        <FilterListItem label="Yes" value={{ sourceName: true }} />
                        <FilterListItem label="No" value={{ sourceName: false }} />
                    </FilterList>
                </Variant>
            </Component>
            <Component
                name="FilterLiveSearch"
                docURL="https://marmelab.com/react-admin/FilterLiveSearch.html"
            >
                <Variant>
                    <FilterLiveSearch source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="SavedQueriesList"
                docURL="https://marmelab.com/react-admin/SavedQueriesList.html"
            >
                <Variant>
                    <SavedQueriesList />
                </Variant>
            </Component>
            <Component
                name="Pagination"
                docURL="https://marmelab.com/react-admin/Pagination.html"
            >
                <Variant>
                    <Pagination rowsPerPageOptions={[10, 25, 50, 100]} />
                </Variant>
            </Component>
            <Component
                name="SortButton"
                docURL="https://marmelab.com/react-admin/SortButton.html"
            >
                <Variant>
                    <SortButton fields={["sourceName"]} label="Sort by" />
                </Variant>
            </Component>
            <Component
                name="SelectColumnsButton"
                docURL="https://marmelab.com/react-admin/SelectColumnsButton.html"
            >
                <Variant>
                    <SelectColumnsButton />
                </Variant>
                <Variant name="preferenceKey">
                    <SelectColumnsButton preferenceKey="sourceName" />
                </Variant>
            </Component>
            <Component
                name="Count"
                docURL="https://marmelab.com/react-admin/Count.html"
            >
                <Variant>
                    <Count />
                </Variant>
                <Variant name="filter">
                    <Count filter={{ sourceName: true }} />
                </Variant>
                <Variant name="link">
                    <Count link />
                </Variant>
                <Variant name="resource">
                    <Count resource="recordName" />
                </Variant>
                <Variant name="sort">
                    <Count sort={{ field: "sourceName", order: "ASC" }} />
                </Variant>
            </Component>
            <Component
                name="useList"
                docURL="https://marmelab.com/react-admin/useList.html"
            >
                <Variant proto={UseListProto} />
            </Component>
            <Component
                name="useListContext"
                docURL="https://marmelab.com/react-admin/useListContext.html"
            >
                <Variant proto={UseListContextProto} />
            </Component>
            <Component
                name="useListController"
                docURL="https://marmelab.com/react-admin/useListController.html"
            >
                <Variant proto={UseListControllerProto} />
            </Component>
            <Component
                name="useUnselect"
                docURL="https://marmelab.com/react-admin/useUnselect.html"
            >
                <Variant proto={UseUnselectProto} />
            </Component>
            <Component
                name="useUnselectAll"
                docURL="https://marmelab.com/react-admin/useUnselectAll.html"
            >
                <Variant proto={UseUnselectAllProto} />
            </Component>
        </Category>
        <Category name="Creation & Edition">
            <Component
                name="Create"
                docURL="https://marmelab.com/react-admin/Create.html"
            >
                <Variant>
                    <Create>
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Create>
                </Variant>
                <Variant name="actions">
                    <Create
                        actions={<TopToolbar>{/* Add your custom actions */}</TopToolbar>}
                    >
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Create>
                </Variant>
                <Variant name="aside">
                    <Create aside={<div>Aside content</div>}>
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Create>
                </Variant>
                <Variant name="redirect">
                    <Create redirect="list">
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Create>
                </Variant>
                <Variant name="resource">
                    <Create resource="recordName">
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Create>
                </Variant>
            </Component>
            <Component
                name="CreateBase"
                docURL="https://marmelab.com/react-admin/CreateBase.html"
            >
                <Variant>
                    <CreateBase>
                        <div>
                            <h1>Title</h1>
                            <SimpleForm>
                                <TextInput source="sourceName" />
                            </SimpleForm>
                        </div>
                    </CreateBase>
                </Variant>
            </Component>
            <Component
                name="Edit"
                docURL="https://marmelab.com/react-admin/Edit.html"
            >
                <Variant>
                    <Edit>
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Edit>
                </Variant>
                <Variant name="actions">
                    <Edit
                        actions={<TopToolbar>{/* Add your custom actions */}</TopToolbar>}
                    >
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Edit>
                </Variant>
                <Variant name="disableAuthentication">
                    <Edit disableAuthentication>
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Edit>
                </Variant>
                <Variant name="id">
                    <Edit id={1}>
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Edit>
                </Variant>
                <Variant name="mutationMode">
                    <Edit mutationMode="pessimistic">
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Edit>
                </Variant>
                <Variant name="redirect">
                    <Edit redirect="show">
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Edit>
                </Variant>
                <Variant name="resource">
                    <Edit resource="recordName">
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Edit>
                </Variant>
                <Variant name="title">
                    <Edit title="Title">
                        <SimpleForm>
                            <TextInput source="sourceName" />
                        </SimpleForm>
                    </Edit>
                </Variant>
            </Component>
            <Component
                name="EditBase"
                docURL="https://marmelab.com/react-admin/EditBase.html"
            >
                <Variant>
                    <EditBase>
                        <div>
                            <h1>Title</h1>
                            <SimpleForm>
                                <TextInput source="sourceName" />
                            </SimpleForm>
                        </div>
                    </EditBase>
                </Variant>
            </Component>
            <Component
                name="EditGuesser"
                docURL="https://marmelab.com/react-admin/EditGuesser.html"
            >
                <EditGuesser />
            </Component>
            <Component
                name="SimpleForm"
                docURL="https://marmelab.com/react-admin/SimpleForm.html"
            >
                <Variant>
                    <SimpleForm defaultValues={() => ({})}>
                        <TextInput source="sourceName" />
                    </SimpleForm>
                </Variant>
                <Variant name="id">
                    <>
                        <SimpleForm toolbar={false} id="form_id">
                            <TextInput source="title" />
                        </SimpleForm>
                        <SaveButton form="form_id" />
                    </>
                </Variant>
                <Variant name="noValidate">
                    <SimpleForm noValidate>
                        <TextInput source="sourceName" />
                    </SimpleForm>
                </Variant>
                <Variant name="onSubmit">
                    <SimpleForm onSubmit={() => {}}>
                        <TextInput source="sourceName" />
                    </SimpleForm>
                </Variant>
                <Variant name="sanitizeEmptyValues">
                    <SimpleForm sanitizeEmptyValues>
                        <TextInput source="sourceName" />
                    </SimpleForm>
                </Variant>
                <Variant name="validate">
                    <SimpleForm
                        validate={(values: any) => {
                            const errors = {};
                            return errors;
                        }}
                    >
                        <TextInput source="sourceName" />
                    </SimpleForm>
                </Variant>
                <Variant name="warnWhenUnsavedChanges">
                    <SimpleForm warnWhenUnsavedChanges>
                        <TextInput source="sourceName" />
                    </SimpleForm>
                </Variant>
            </Component>
            <Component
                name="TabbedForm"
                docURL="https://marmelab.com/react-admin/TabbedForm.html"
            >
                <Variant>
                    <TabbedForm>
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
                <Variant name="defaultValues">
                    <TabbedForm defaultValues={() => ({})}>
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
                <Variant name="id">
                    <>
                        <TabbedForm toolbar={false} id="form_id">
                            <TabbedForm.Tab label="Tab1">
                                <TextInput source="sourceName" />
                            </TabbedForm.Tab>
                            <TabbedForm.Tab label="Tab2">
                                <TextInput source="sourceName" />
                            </TabbedForm.Tab>
                        </TabbedForm>
                        <SaveButton form="form_id" />
                    </>
                </Variant>
                <Variant name="noValidate">
                    <TabbedForm noValidate>
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
                <Variant name="onSubmit">
                    <TabbedForm onSubmit={() => {}}>
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
                <Variant name="sanitizeEmptyValues">
                    <TabbedForm sanitizeEmptyValues>
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
                <Variant name="syncWithLocation">
                    <TabbedForm syncWithLocation>
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
                <Variant name="tabs">
                    <TabbedForm
                        tabs={<TabbedFormTabs variant="scrollable" scrollButtons="auto" />}
                    >
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
                <Variant name="toolbar">
                    <TabbedForm toolbar={<div>Toolbar content</div>}>
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
                <Variant name="validate">
                    <TabbedForm
                        validate={(values) => {
                            const errors = {};
                            return errors;
                        }}
                    >
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
                <Variant name="warnWhenUnsavedChanges">
                    <TabbedForm warnWhenUnsavedChanges>
                        <TabbedForm.Tab label="Tab1">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                        <TabbedForm.Tab label="Tab2">
                            <TextInput source="sourceName" />
                        </TabbedForm.Tab>
                    </TabbedForm>
                </Variant>
            </Component>
            <Component
                name="Form"
                docURL="https://marmelab.com/react-admin/Form.html"
            >
                <Variant>
                    <Form>
                        <TextInput source="sourceName" />
                    </Form>
                </Variant>
                <Variant name="defaultValues">
                    <Form defaultValues={() => ({})}>
                        <TextInput source="sourceName" />
                    </Form>
                </Variant>
                <Variant name="id">
                    <>
                        <Form defaultValues={() => ({})} id="form_id">
                            <TextInput source="sourceName" />
                        </Form>
                        <SaveButton form="post_create_form" />
                    </>
                </Variant>
                <Variant name="noValidate">
                    <Form noValidate>
                        <TextInput source="sourceName" />
                    </Form>
                </Variant>
                <Variant name="onSubmit">
                    <Form onSubmit={() => {}}>
                        <TextInput source="sourceName" />
                    </Form>
                </Variant>
                <Variant name="sanitizeEmptyValues">
                    <Form sanitizeEmptyValues>
                        <TextInput source="sourceName" />
                    </Form>
                </Variant>
                <Variant name="validate">
                    <Form
                        validate={(values) => {
                            const errors = {};
                            return errors;
                        }}
                    >
                        <TextInput source="sourceName" />
                    </Form>
                </Variant>
                <Variant name="warnWhenUnsavedChanges">
                    <Form warnWhenUnsavedChanges>
                        <TextInput source="sourceName" />
                    </Form>
                </Variant>
            </Component>
            <Component
                name="Toolbar"
                docURL="https://marmelab.com/react-admin/Toolbar.html"
            >
                <Variant>
                    <Toolbar>
                        <SaveButton />
                    </Toolbar>
                </Variant>
            </Component>
            <Component
                name="SaveButton"
                docURL="https://marmelab.com/react-admin/SaveButton.html"
            >
                <Variant>
                    <SaveButton />
                </Variant>
                <Variant name="label">
                    <SaveButton label="Save label" />
                </Variant>
                <Variant name="icon">
                    <SaveButton icon={<span />} />
                </Variant>
                <Variant name="onClick">
                    <SaveButton onClick={() => alert("Saving...")} />
                </Variant>
                <Variant name="alwaysEnable">
                    <SaveButton alwaysEnable />
                </Variant>
            </Component>
            <Component
                name="useCreateContext"
                docURL="https://marmelab.com/react-admin/useCreateContext.html"
            >
                <Variant proto={UseCreateContextProto} />
            </Component>
            <Component
                name="useCreateController"
                docURL="https://marmelab.com/react-admin/useCreateController.html"
            >
                <Variant proto={UseCreateControllerProto} />
            </Component>
            <Component
                name="useEditContext"
                docURL="https://marmelab.com/react-admin/useEditContext.html"
            >
                <Variant proto={UseEditContextProto} />
            </Component>
            <Component
                name="useEditController"
                docURL="https://marmelab.com/react-admin/useEditController.html"
            >
                <Variant proto={UseEditControllerProto} />
            </Component>
            <Component
                name="useSaveContext"
                docURL="https://marmelab.com/react-admin/useSaveContext.html"
            >
                <Variant proto={UseSaveContextProto} />
            </Component>
        </Category>
        <Category name="Show">
            <Component
                name="Show"
                docURL="https://marmelab.com/react-admin/Show.html"
            >
                <Variant>
                    <Show>
                        <SimpleShowLayout>
                            <TextField source="sourceName" />
                        </SimpleShowLayout>
                    </Show>
                </Variant>
                <Variant name="title">
                    <Show title="Title">
                        <SimpleShowLayout>
                            <TextField source="sourceName" />
                        </SimpleShowLayout>
                    </Show>
                </Variant>
                <Variant name="actions">
                    <Show
                        actions={
                            <TopToolbar>
                                <EditButton />
                                {/* Add your custom actions */}
                                <Button
                                    onClick={() => {
                                        alert("Your custom action");
                                    }}
                                    label="Custom button"
                                />
                            </TopToolbar>
                        }
                    >
                        <SimpleShowLayout>
                            <TextField source="sourceName" />
                        </SimpleShowLayout>
                    </Show>
                </Variant>
                <Variant name="aside">
                    <Show aside={<div>Aside content</div>}>
                        <SimpleShowLayout>
                            <TextField source="sourceName" />
                        </SimpleShowLayout>
                    </Show>
                </Variant>
                <Variant name="disableAuthentication">
                    <Show disableAuthentication>
                        <SimpleShowLayout>
                            <TextField source="sourceName" />
                        </SimpleShowLayout>
                    </Show>
                </Variant>
            </Component>
            <Component
                name="ShowBase"
                docURL="https://marmelab.com/react-admin/ShowBase.html"
            >
                <Variant>
                    <ShowBase resource="posts">
                        <div>
                            <h1>Title</h1>
                            <SimpleShowLayout>...</SimpleShowLayout>
                        </div>
                    </ShowBase>
                </Variant>
            </Component>
            <Component
                name="ShowGuesser"
                docURL="https://marmelab.com/react-admin/ShowGuesser.html"
            >
                <Variant>
                    <ShowGuesser />
                </Variant>
            </Component>
            <Component
                name="SimpleShowLayout"
                docURL="https://marmelab.com/react-admin/SimpleShowLayout.html"
            >
                <Variant>
                    <SimpleShowLayout>
                        <TextField source="sourceName" />
                    </SimpleShowLayout>
                </Variant>
                <Variant name="spacing">
                    <SimpleShowLayout spacing={2}>
                        <TextField source="sourceName" />
                    </SimpleShowLayout>
                </Variant>
                <Variant name="divider">
                    <SimpleShowLayout divider={<hr />}>
                        <TextField source="sourceName" />
                    </SimpleShowLayout>
                </Variant>
            </Component>
            <Component
                name="TabbedShowLayout"
                docURL="https://marmelab.com/react-admin/TabbedShowLayout.html"
            >
                <Variant>
                    <TabbedShowLayout>
                        <TabbedShowLayout.Tab label="TabOne">
                            <TextField source="sourceName" />
                        </TabbedShowLayout.Tab>
                        <TabbedShowLayout.Tab label="TabTwo">
                            <TextField source="sourceName" />
                        </TabbedShowLayout.Tab>
                    </TabbedShowLayout>
                </Variant>
                <Variant name="spacing">
                    <TabbedShowLayout spacing={2}>
                        <TabbedShowLayout.Tab label="TabOne">
                            <TextField source="sourceName" />
                        </TabbedShowLayout.Tab>
                        <TabbedShowLayout.Tab label="TabTwo">
                            <TextField source="sourceName" />
                        </TabbedShowLayout.Tab>
                    </TabbedShowLayout>
                </Variant>
                <Variant name="divider">
                    <TabbedShowLayout divider={<hr />}>
                        <TabbedShowLayout.Tab label="TabOne">
                            <TextField source="sourceName" />
                        </TabbedShowLayout.Tab>
                        <TabbedShowLayout.Tab label="TabTwo">
                            <TextField source="sourceName" />
                        </TabbedShowLayout.Tab>
                    </TabbedShowLayout>
                </Variant>
            </Component>
            <Component
                name="Labeled"
                docURL="https://marmelab.com/react-admin/Labeled.html"
            >
                <Variant>
                    <Labeled>
                        <TextField source="sourceName" />
                    </Labeled>
                </Variant>
            </Component>
            <Component
                name="useShowContext"
                docURL="https://marmelab.com/react-admin/useShowContext.html"
            >
                <Variant proto={UseShowContextProto} />
            </Component>
            <Component
                name="useShowController"
                docURL="https://marmelab.com/react-admin/useShowController.html"
            >
                <Variant proto={UseShowControllerProto} />
            </Component>
        </Category>
        <Category name="Common">
            <Component
                name="WithRecord"
                docURL="https://marmelab.com/react-admin/WithRecord.html"
            >
                <Variant>
                    <WithRecord
                        label="sourceName"
                        render={(record) => <span>{record.sourceName}</span>}
                    />
                </Variant>
            </Component>
            <Component
                name="useRecordContext"
                docURL="https://marmelab.com/react-admin/useRecordContext.html"
            >
                <Variant proto={UseRecordContextProto} />
            </Component>
            <Component
                name="useGetRecordId"
                docURL="https://marmelab.com/react-admin/useGetRecordId.html"
            >
                <Variant proto={UseGetRecordIdProto} />
            </Component>
            <Component
                name="useNotify"
                docURL="https://marmelab.com/react-admin/useNotify.html"
            >
                <Variant proto={UseNotifyProto} />
            </Component>
            <Component
                name="useRedirect"
                docURL="https://marmelab.com/react-admin/useRedirect.html"
            >
                <Variant proto={UseRedirectProto} />
            </Component>
            <Component
                name="useRefresh"
                docURL="https://marmelab.com/react-admin/useRefresh.html"
            >
                <Variant proto={UseRefreshProto} />
            </Component>
        </Category>
        <Category name="Fields">
            <Component
                name="ArrayField"
                docURL="https://marmelab.com/react-admin/ArrayField.html"
            >
                <Variant name="SingleFieldList">
                    <ArrayField source="sourceName">
                        <SingleFieldList>
                            <ChipField source="sourceName" />
                        </SingleFieldList>
                    </ArrayField>
                </Variant>
                <Variant name="Datagrid">
                    <ArrayField source="sourceName">
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </ArrayField>
                </Variant>
                <Variant name="SimpleList">
                    <ArrayField source="sourceName">
                        <SimpleList
                            primaryText={(record) => record.sourceName}
                            secondaryText={(record) => record.sourceName}
                        />
                    </ArrayField>
                </Variant>
            </Component>
            <Component
                name="BooleanField"
                docURL="https://marmelab.com/react-admin/BooleanField.html"
            >
                <Variant>
                    <BooleanField source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="ChipField"
                docURL="https://marmelab.com/react-admin/ChipField.html"
            >
                <Variant>
                    <ChipField source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="DateField"
                docURL="https://marmelab.com/react-admin/DateField.html"
            >
                <Variant>
                    <DateField source="sourceName" />
                </Variant>
                <Variant name="showTime">
                    <DateField source="sourceName" showTime />
                </Variant>
                <Variant name="showDate.false">
                    <DateField source="sourceName" showDate={false} />
                </Variant>
            </Component>
            <Component
                name="EmailField"
                docURL="https://marmelab.com/react-admin/EmailField.html"
            >
                <Variant>
                    <EmailField source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="FileField"
                docURL="https://marmelab.com/react-admin/FileField.html"
            >
                <Variant>
                    <FileField source="sourceName" title="title" />
                </Variant>
            </Component>
            <Component
                name="FunctionField"
                docURL="https://marmelab.com/react-admin/FunctionField.html"
            >
                <Variant>
                    <FunctionField
                        label="Name"
                        render={(record: any) => record.sourceName}
                    />
                </Variant>
            </Component>
            <Component
                name="ImageField"
                docURL="https://marmelab.com/react-admin/ImageField.html"
            >
                <Variant>
                    <ImageField source="sourceName" title="title" />
                </Variant>
            </Component>
            <Component
                name="NumberField"
                docURL="https://marmelab.com/react-admin/NumberField.html"
            >
                <Variant>
                    <NumberField source="sourceName" />
                </Variant>
                <Variant name="maximumFractionDigits">
                    <NumberField
                        source="sourceName"
                        options={{ maximumFractionDigits: 2 }}
                    />
                </Variant>
                <Variant name="percent">
                    <NumberField source="sourceName" options={{ style: "percent" }} />
                </Variant>
                <Variant name="price">
                    <NumberField
                        source="sourceName"
                        options={{ style: "currency", currency: "USD" }}
                    />
                </Variant>
                <Variant name="textAlign">
                    <NumberField source="score" textAlign="left" />
                </Variant>
            </Component>
            <Component
                name="ReferenceField"
                docURL="https://marmelab.com/react-admin/ReferenceField.html"
            >
                <Variant>
                    <ReferenceField source="sourceName" reference="resourceName" />
                </Variant>
                <Variant name="emptyText">
                    <ReferenceField
                        source="sourceName"
                        reference="resourceName"
                        emptyText="missingProp"
                    />
                </Variant>
                <Variant name="label">
                    <ReferenceField
                        label="Label"
                        source="sourceName"
                        reference="resourceName"
                    />
                </Variant>
                <Variant name="link">
                    <ReferenceField
                        source="sourceName"
                        reference="resourceName"
                        link={(record: RaRecord, reference: string) =>
                            `/my/path/to/${reference}/${record.id}`
                        }
                    />
                </Variant>
            </Component>
            <Component
                name="ReferenceArrayField"
                docURL="https://marmelab.com/react-admin/ReferenceArrayField.html"
            >
                <Variant>
                    <ReferenceArrayField source="sourceName" reference="resourceName" />
                </Variant>
                <Variant name="children">
                    <ReferenceArrayField reference="resourceName" source="sourceName">
                        <Datagrid>
                            <TextField source="id" />
                            <TextField source="name" />
                            <ShowButton />
                        </Datagrid>
                    </ReferenceArrayField>
                </Variant>
                <Variant name="filter">
                    <ReferenceArrayField
                        source="sourceName"
                        reference="resourceName"
                        filter={{ is_published: true }}
                    />
                </Variant>
                <Variant name="label">
                    <ReferenceArrayField
                        label="Label"
                        source="sourceName"
                        reference="resourceName"
                    />
                </Variant>
                <Variant name="pagination">
                    <ReferenceArrayField
                        source="sourceName"
                        reference="resourceName"
                        perPage={10}
                        pagination={<Pagination />}
                    />
                </Variant>
                <Variant name="sort">
                    <ReferenceArrayField
                        source="sourceName"
                        reference="resourceName"
                        sort={{ field: "sourceName", order: "ASC" }}
                    />
                </Variant>
            </Component>
            <Component
                name="ReferenceManyField"
                docURL="https://marmelab.com/react-admin/ReferenceManyField.html"
            >
                <Variant>
                    <ReferenceManyField reference="resourceName" target="sourceName">
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </ReferenceManyField>
                </Variant>
                <Variant name="filter">
                    <ReferenceManyField
                        reference="resourceName"
                        target="sourceName"
                        filter={{ is_published: true }}
                    >
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </ReferenceManyField>
                </Variant>
                <Variant name="label">
                    <ReferenceManyField
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                    >
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </ReferenceManyField>
                </Variant>
                <Variant name="pagination">
                    <ReferenceManyField
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                        perPage={10}
                        pagination={<Pagination />}
                    >
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </ReferenceManyField>
                </Variant>
                <Variant name="sort">
                    <ReferenceManyField
                        reference="resourceName"
                        target="sourceName"
                        sort={{ field: "sourceName", order: "DESC" }}
                    >
                        <Datagrid>
                            <TextField source="sourceName" />
                        </Datagrid>
                    </ReferenceManyField>
                </Variant>
            </Component>
            <Component
                name="ReferenceManyCount"
                docURL="https://marmelab.com/react-admin/ReferenceManyCount.html"
            >
                <Variant>
                    <ReferenceManyCount reference="resourceName" target="sourceName" />
                </Variant>
                <Variant name="label">
                    <ReferenceManyCount
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                    />
                </Variant>
                <Variant name="filter">
                    <ReferenceManyCount
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                        filter={{ is_published: true }}
                    />
                </Variant>
                <Variant name="link">
                    <ReferenceManyCount
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                        link
                    />
                </Variant>
            </Component>
            <Component
                name="ReferenceOneField"
                docURL="https://marmelab.com/react-admin/ReferenceOneField.html"
            >
                <Variant>
                    <ReferenceOneField
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                    >
                        <TextField source="genre" />
                    </ReferenceOneField>
                </Variant>
                <Variant name="combined">
                    <ReferenceOneField
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                    >
                        <TextField source="sourceNameOne" />
                        <TextField source="sourceNameTwo" />
                    </ReferenceOneField>
                </Variant>
                <Variant name="filter">
                    <ReferenceOneField
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                        filter={{ is_published: true }}
                    >
                        <TextField source="sourceName" />
                    </ReferenceOneField>
                </Variant>
                <Variant name="disableLink">
                    <ReferenceOneField
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                        link={false}
                    >
                        <TextField source="sourceName" />
                    </ReferenceOneField>
                </Variant>
                <Variant name="queryOptions">
                    <ReferenceOneField
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                        queryOptions={{ refetchOnWindowFocus: false }}
                    >
                        <TextField source="sourceName" />
                    </ReferenceOneField>
                </Variant>
                <Variant name="disableLink">
                    <ReferenceOneField
                        label="Label"
                        reference="resourceName"
                        target="sourceName"
                        sort={{ field: "sourceName", order: "DESC" }}
                    >
                        <TextField source="sourceName" />
                    </ReferenceOneField>
                </Variant>
            </Component>
            <Component
                name="RichTextField"
                docURL="https://marmelab.com/react-admin/RichTextField.html"
            >
                <Variant>
                    <RichTextField source="sourceName" />
                </Variant>
                <Variant name="stripTags">
                    <RichTextField source="sourceName" stripTags />
                </Variant>
            </Component>
            <Component
                name="SelectField"
                docURL="https://marmelab.com/react-admin/SelectField.html"
            >
                <Variant>
                    <SelectField
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="translateChoice">
                    <SelectField
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "customTranslate.nameOne" },
                            { id: "idTwo", name: "customTranslate.nameTwo" },
                        ]}
                        translateChoice={false}
                    />
                </Variant>
            </Component>
            <Component
                name="TextField"
                docURL="https://marmelab.com/react-admin/TextField.html"
            >
                <Variant>
                    <TextField source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="TranslatableFields"
                docURL="https://marmelab.com/react-admin/TranslatableFields.html"
            >
                <Variant>
                    <TranslatableFields
                        locales={["en", "fr"]}
                        groupKey="essential-fields"
                    >
                        <TextField source="sourceNameOne" />
                        <TextField source="sourceNameTwo" />
                    </TranslatableFields>
                </Variant>
            </Component>
            <Component
                name="UrlField"
                docURL="https://marmelab.com/react-admin/UrlField.html"
            >
                <Variant>
                    <UrlField source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="WrapperField"
                docURL="https://marmelab.com/react-admin/WrapperField.html"
            >
                <Variant>
                    <WrapperField label="Label" sortBy="sourceNameOne">
                        <TextField source="sourceNameOne" />
                        <TextField source="sourceNameTwo" />
                    </WrapperField>
                </Variant>
            </Component>
        </Category>
        <Category name="Inputs (RA)">
            <Component
                name="ArrayInput"
                docURL="https://marmelab.com/react-admin/ArrayInput.html"
            >
                <Variant>
                    <ArrayInput source="sourceName">
                        <SimpleFormIterator inline>
                            <TextInput source="sourceName" helperText={false} />
                        </SimpleFormIterator>
                    </ArrayInput>
                </Variant>
            </Component>
            <Component
                name="AutocompleteInput"
                docURL="https://marmelab.com/react-admin/AutocompleteInput.html"
            >
                <Variant>
                    <AutocompleteInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="debounce">
                    <ReferenceInput label="Author" source="author_id" reference="authors">
                        <AutocompleteInput debounce={500} />
                    </ReferenceInput>
                </Variant>
                <Variant name="emptyText">
                    <AutocompleteInput
                        emptyText="emptyText"
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="emptyValue">
                    <AutocompleteInput
                        emptyValue={0}
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="filterToQuery">
                    <ReferenceInput label="Author" source="author_id" reference="authors">
                        <AutocompleteInput
                            filterToQuery={(searchText) => ({
                                name_ilike: `%${searchText}%`,
                            })}
                        />
                    </ReferenceInput>
                </Variant>
                <Variant name="isLoading">
                    <AutocompleteInput
                        isLoading
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="optionText">
                    <AutocompleteInput
                        optionText="label"
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="optionValue">
                    <AutocompleteInput
                        source="sourceName"
                        choices={[
                            { _id: "idOne", name: "nameOne" },
                            { _id: "idTwo", name: "nameTwo" },
                        ]}
                        optionValue="_id"
                    />
                </Variant>
                <Variant name="shouldRenderSuggestions">
                    <AutocompleteInput
                        shouldRenderSuggestions={(val: any) => {
                            return val.trim().length > 2;
                        }}
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="suggestionLimit">
                    <AutocompleteInput
                        suggestionLimit={10}
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
            </Component>
            <Component
                name="AutocompleteArrayInput"
                docURL="https://marmelab.com/react-admin/AutocompleteArrayInput.html"
            >
                <Variant>
                    <AutocompleteArrayInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="debounce">
                    <ReferenceArrayInput source="sourceName" reference="resourceName">
                        <AutocompleteArrayInput debounce={500} />
                    </ReferenceArrayInput>
                </Variant>
                <Variant name="emptyText">
                    <AutocompleteArrayInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        emptyText="emptyText"
                    />
                </Variant>
                <Variant name="emptyValue">
                    <AutocompleteArrayInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        emptyValue={0}
                    />
                </Variant>
                <Variant name="filterToQuery">
                    <ReferenceArrayInput source="sourceName" reference="resourceName">
                        <AutocompleteArrayInput
                            filterToQuery={(searchText) => ({
                                name_ilike: `%${searchText}%`,
                            })}
                        />
                    </ReferenceArrayInput>
                </Variant>
                <Variant name="optionText">
                    <AutocompleteArrayInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        optionText="label"
                    />
                </Variant>
                <Variant name="optionValue">
                    <AutocompleteArrayInput
                        source="sourceName"
                        choices={[
                            { _id: "idOne", name: "nameOne" },
                            { _id: "idTwo", name: "nameTwo" },
                        ]}
                        optionValue="_id"
                    />
                </Variant>
                <Variant name="shouldRenderSuggestions">
                    <AutocompleteArrayInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        shouldRenderSuggestions={(val: any) => {
                            return val.trim().length > 2;
                        }}
                    />
                </Variant>
                <Variant name="suggestionLimit">
                    <AutocompleteArrayInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        suggestionLimit={10}
                    />
                </Variant>
            </Component>
            <Component
                name="BooleanInput"
                docURL="https://marmelab.com/react-admin/BooleanInput.html"
            >
                <Variant>
                    <BooleanInput label="Label" source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="CheckboxGroupInput"
                docURL="https://marmelab.com/react-admin/CheckboxGroupInput.html"
            >
                <Variant>
                    <CheckboxGroupInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="labelPlacement">
                    <CheckboxGroupInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        labelPlacement="bottom"
                    />
                </Variant>
                <Variant name="optionText">
                    <CheckboxGroupInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        optionText="label"
                    />
                </Variant>
                <Variant name="optionValue">
                    <CheckboxGroupInput
                        source="sourceName"
                        choices={[
                            { _id: "idOne", name: "nameOne" },
                            { _id: "idTwo", name: "nameTwo" },
                        ]}
                        optionValue="_id"
                    />
                </Variant>
                <Variant name="row">
                    <CheckboxGroupInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        row={false}
                    />
                </Variant>
                <Variant name="translateChoice">
                    <CheckboxGroupInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        translateChoice={false}
                    />
                </Variant>
            </Component>
            <Component
                name="DateInput"
                docURL="https://marmelab.com/react-admin/DateInput.html"
            >
                <Variant>
                    <DateInput source="sourceName" />
                </Variant>
                <Variant name="validate">
                    <DateInput source="published" validate={minValue("2022-10-26")} />
                </Variant>
            </Component>
            <Component
                name="DateTimeInput"
                docURL="https://marmelab.com/react-admin/DateTimeInput.html"
            >
                <Variant>
                    <DateTimeInput source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="FileInput"
                docURL="https://marmelab.com/react-admin/FileInput.html"
            >
                <Variant>
                    <FileInput source="sourceName">
                        <FileField source="sourceName" title="title" />
                    </FileInput>
                </Variant>
                <Variant name="accept">
                    <FileInput source="sourceName" accept="application/pdf">
                        <FileField source="sourceName" title="title" />
                    </FileInput>
                </Variant>
                <Variant name="minSize">
                    <FileInput source="sourceName" minSize={5000}>
                        <FileField source="sourceName" title="title" />
                    </FileInput>
                </Variant>
                <Variant name="minSize">
                    <FileInput source="sourceName" maxSize={5000000}>
                        <FileField source="sourceName" title="title" />
                    </FileInput>
                </Variant>
                <Variant name="multiple">
                    <FileInput source="sourceName" multiple>
                        <FileField source="sourceName" title="title" />
                    </FileInput>
                </Variant>
                <Variant name="placeholder">
                    <FileInput
                        source="sourceName"
                        placeholder={<p>Drop your file here</p>}
                    >
                        <FileField source="sourceName" title="title" />
                    </FileInput>
                </Variant>
            </Component>
            <Component
                name="ImageInput"
                docURL="https://marmelab.com/react-admin/ImageInput.html"
            >
                <Variant>
                    <ImageInput source="sourceName">
                        <ImageField source="sourceName" title="title" />
                    </ImageInput>
                </Variant>
                <Variant name="accept">
                    <ImageInput source="sourceName" accept="application/pdf">
                        <ImageField source="sourceName" title="title" />
                    </ImageInput>
                </Variant>
                <Variant name="minSize">
                    <ImageInput source="sourceName" minSize={5000}>
                        <ImageField source="sourceName" title="title" />
                    </ImageInput>
                </Variant>
                <Variant name="minSize">
                    <ImageInput source="sourceName" maxSize={5000000}>
                        <ImageField source="sourceName" title="title" />
                    </ImageInput>
                </Variant>
                <Variant name="multiple">
                    <ImageInput source="sourceName" multiple>
                        <ImageField source="sourceName" title="title" />
                    </ImageInput>
                </Variant>
                <Variant name="placeholder">
                    <ImageInput
                        source="sourceName"
                        placeholder={<p>Drop your file here</p>}
                    >
                        <ImageField source="sourceName" title="title" />
                    </ImageInput>
                </Variant>
            </Component>
            <Component
                name="NullableBooleanInput"
                docURL="https://marmelab.com/react-admin/NullableBooleanInput.html"
            >
                <Variant>
                    <NullableBooleanInput label="Label" source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="NumberInput"
                docURL="https://marmelab.com/react-admin/NumberInput.html"
            >
                <Variant>
                    <NumberInput source="sourceName" />
                </Variant>
                <Variant name="step">
                    <NumberInput source="sourceName" step={1} />
                </Variant>
                <Variant name="min">
                    <NumberInput source="sourceName" min={0} />
                </Variant>
                <Variant name="max">
                    <NumberInput source="sourceName" max={100} />
                </Variant>
            </Component>
            <Component
                name="PasswordInput"
                docURL="https://marmelab.com/react-admin/PasswordInput.html"
            >
                <Variant>
                    <PasswordInput source="sourceName" />
                </Variant>
                <Variant name="initiallyVisible">
                    <PasswordInput source="sourceName" initiallyVisible />
                </Variant>
            </Component>
            <Component
                name="RadioButtonGroupInput"
                docURL="https://marmelab.com/react-admin/RadioButtonGroupInput.html"
            >
                <Variant>
                    <RadioButtonGroupInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="isLoading">
                    <RadioButtonGroupInput
                        isLoading
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="optionText">
                    <RadioButtonGroupInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        optionText="label"
                    />
                </Variant>
                <Variant name="optionText">
                    <RadioButtonGroupInput
                        source="sourceName"
                        choices={[
                            { _id: "idOne", name: "nameOne" },
                            { _id: "idTwo", name: "nameTwo" },
                        ]}
                        optionValue="_id"
                    />
                </Variant>
                <Variant name="row">
                    <RadioButtonGroupInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        row={false}
                    />
                </Variant>
                <Variant name="translateChoice">
                    <RadioButtonGroupInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        translateChoice={false}
                    />
                </Variant>
            </Component>
            <Component
                name="ReferenceInput"
                docURL="https://marmelab.com/react-admin/ReferenceInput.html"
            >
                <Variant>
                    <ReferenceInput source="sourceName" reference="resourceName" />
                </Variant>
                <Variant name="children">
                    <ReferenceInput source="sourceName" reference="resourceName">
                        <SelectInput />
                    </ReferenceInput>
                </Variant>
                <Variant name="enableGetChoices">
                    <ReferenceInput
                        source="sourceName"
                        reference="resourceName"
                        enableGetChoices={({ q }) => q && q.length >= 2}
                    />
                </Variant>
                <Variant name="filter">
                    <ReferenceInput
                        source="sourceName"
                        reference="resourceName"
                        filter={{ is_published: true }}
                    />
                </Variant>
                <Variant name="format">
                    <ReferenceInput source="sourceName" reference="resourceName">
                        <AutocompleteInput
                            format={(value) => (value == null ? "not defined" : value)}
                        />
                    </ReferenceInput>
                </Variant>
                <Variant name="label">
                    <ReferenceInput source="sourceName" reference="resourceName">
                        <AutocompleteInput label="Label" />
                    </ReferenceInput>
                </Variant>
                <Variant name="perPage">
                    <ReferenceInput
                        source="sourceName"
                        reference="resourceName"
                        perPage={100}
                    />
                </Variant>
                <Variant name="queryOptions">
                    <ReferenceInput
                        source="sourceName"
                        reference="resourceName"
                        queryOptions={{ meta: { foo: "bar" } }}
                    />
                </Variant>
                <Variant name="sort">
                    <ReferenceInput
                        source="sourceName"
                        reference="resourceName"
                        sort={{ field: "sourceName", order: "ASC" }}
                    />
                </Variant>
            </Component>
            <Component
                name="ReferenceArrayInput"
                docURL="https://marmelab.com/react-admin/ReferenceArrayInput.html"
            >
                <Variant>
                    <ReferenceArrayInput source="sourceName" reference="resourceName" />
                </Variant>
                <Variant name="children">
                    <ReferenceArrayInput source="sourceName" reference="resourceName">
                        <AutocompleteArrayInput label="Label" />
                    </ReferenceArrayInput>
                </Variant>
                <Variant name="enableGetChoices">
                    <ReferenceArrayInput
                        source="sourceName"
                        reference="resourceName"
                        enableGetChoices={({ q }) => q && q.length >= 2}
                    />
                </Variant>
                <Variant name="filter">
                    <ReferenceArrayInput
                        source="sourceName"
                        reference="resourceName"
                        filter={{ is_published: true }}
                    />
                </Variant>
                <Variant name="format">
                    <ReferenceArrayInput source="sourceName" reference="resourceName">
                        <AutocompleteArrayInput
                            format={(value) => (value == null ? "not defined" : value)}
                        />
                    </ReferenceArrayInput>
                </Variant>
                <Variant name="label">
                    <ReferenceArrayInput source="sourceName" reference="resourceName">
                        <AutocompleteArrayInput label="Label" />
                    </ReferenceArrayInput>
                </Variant>
                <Variant name="parse">
                    <ReferenceArrayInput source="sourceName" reference="resourceName">
                        <AutocompleteArrayInput
                            parse={(value) => (value === "not defined" ? null : value)}
                        />
                    </ReferenceArrayInput>
                </Variant>
                <Variant name="perPage">
                    <ReferenceArrayInput
                        source="sourceName"
                        reference="resourceName"
                        perPage={100}
                    />
                </Variant>
                <Variant name="queryOptions">
                    <ReferenceArrayInput
                        source="sourceName"
                        reference="resourceName"
                        queryOptions={{ meta: { foo: "bar" } }}
                    />
                </Variant>
                <Variant name="sort">
                    <ReferenceArrayInput
                        source="sourceName"
                        reference="resourceName"
                        sort={{ field: "sourceName", order: "ASC" }}
                    />
                </Variant>
            </Component>
            {/* TODO Can't find RichTextInput in react-admin library */}
            {/* <Component
        name="RichTextInput"
        docURL="https://marmelab.com/react-admin/RichTextInput.html"
      >
        <Variant>
          <RichTextInput source="sourceName" />
        </Variant>
      </Component> */}
            <Component
                name="SelectInput"
                docURL="https://marmelab.com/react-admin/SelectInput.html"
            >
                <Variant>
                    <SelectInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="disableValue">
                    <SelectInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo", not_available: true },
                        ]}
                        disableValue="not_available"
                    />
                </Variant>
                <Variant name="emptyText">
                    <SelectInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        emptyText="NoAnythingSelected"
                    />
                </Variant>
                <Variant name="emptyValue">
                    <SelectInput
                        source="category"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        emptyValue={0}
                    />
                </Variant>
                <Variant name="isLoading">
                    <SelectInput
                        isLoading
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="optionText">
                    <SelectInput
                        optionText="label"
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="optionValue">
                    <SelectInput
                        source="sourceName"
                        choices={[
                            { _id: "idOne", name: "nameOne" },
                            { _id: "idTwo", name: "nameTwo" },
                        ]}
                        optionValue="_id"
                    />
                </Variant>
                <Variant name="resettable">
                    <SelectInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        resettable
                    />
                </Variant>
            </Component>
            <Component
                name="SelectArrayInput"
                docURL="https://marmelab.com/react-admin/SelectArrayInput.html"
            >
                <Variant>
                    <SelectArrayInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                    />
                </Variant>
                <Variant name="disableValue">
                    <SelectArrayInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo", not_available: true },
                        ]}
                        disableValue="not_available"
                    />
                </Variant>
                <Variant name="optionText">
                    <SelectArrayInput
                        source="sourceName"
                        choices={[
                            { id: "idOne", name: "nameOne" },
                            { id: "idTwo", name: "nameTwo" },
                        ]}
                        optionText="label"
                    />
                </Variant>
                <Variant name="optionValue">
                    <SelectArrayInput
                        source="sourceName"
                        choices={[
                            { _id: "idOne", name: "nameOne" },
                            { _id: "idTwo", name: "nameTwo" },
                        ]}
                        optionValue="_id"
                    />
                </Variant>
            </Component>
            <Component
                name="SimpleFormIterator"
                docURL="https://marmelab.com/react-admin/SimpleFormIterator.html"
            >
                <Variant>
                    <SimpleFormIterator>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
                <Variant name="addButton">
                    <SimpleFormIterator addButton={<button>Add</button>}>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
                <Variant name="disableAdd">
                    <SimpleFormIterator disableAdd>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
                <Variant name="disableClear">
                    <SimpleFormIterator disableClear>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
                <Variant name="disableRemove">
                    <SimpleFormIterator disableRemove>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
                <Variant name="disableReordering">
                    <SimpleFormIterator disableReordering>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
                <Variant name="fullWidth">
                    <SimpleFormIterator fullWidth>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
                <Variant name="getItemLabel">
                    <SimpleFormIterator getItemLabel={(index) => `#${index + 1}`}>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
                <Variant name="inline">
                    <SimpleFormIterator inline>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
                <Variant name="removeButton">
                    <SimpleFormIterator removeButton={<button>Remove</button>}>
                        <TextInput source="sourceName" />
                    </SimpleFormIterator>
                </Variant>
            </Component>
            <Component
                name="TextInput"
                docURL="https://marmelab.com/react-admin/TextInput.html"
            >
                <Variant>
                    <TextInput source="sourceName" />
                </Variant>
                <Variant name="multiline">
                    <TextInput multiline source="sourceName" />
                </Variant>
                <Variant name="fullWidth">
                    <TextInput source="sourceName" fullWidth />
                </Variant>
                <Variant name="resettable">
                    <TextInput source="sourceName" resettable />
                </Variant>
                <Variant name="validate">
                    <TextInput source="sourceName" validate={[required()]} />
                </Variant>
            </Component>
            <Component
                name="TimeInput"
                docURL="https://marmelab.com/react-admin/TimeInput.html"
            >
                <Variant>
                    <TimeInput source="sourceName" />
                </Variant>
            </Component>
            <Component
                name="TranslatableInputs"
                docURL="https://marmelab.com/react-admin/TranslatableInputs.html"
            >
                <Variant>
                    <TranslatableInputs locales={["en", "fr"]}>
                        <TextInput source="sourceName" />
                    </TranslatableInputs>
                </Variant>
                <Variant name="defaultLocale">
                    <TranslatableInputs locales={["en", "fr"]} defaultLocale="fr">
                        <TextInput source="sourceName" />
                    </TranslatableInputs>
                </Variant>
                <Variant name="groupKey">
                    <TranslatableInputs
                        locales={["en", "fr"]}
                        groupKey="essential-fields"
                    >
                        <TextInput source="sourceName" />
                    </TranslatableInputs>
                </Variant>
            </Component>
            <Component
                name="useInput"
                docURL="https://marmelab.com/react-admin/useInput.html"
            >
                <Variant proto={UseInputProto} />
            </Component>
        </Category>
        <Category name="Preference">
            <Component
                name="useStore"
                docURL="https://marmelab.com/react-admin/useStore.html"
            >
                <Variant proto={UseStoreProto} />
            </Component>
            <Component
                name="useRemoveFromStore"
                docURL="https://marmelab.com/react-admin/useRemoveFromStore.html"
            >
                <Variant proto={UseRemoveFromStoreProto} />
            </Component>
            <Component
                name="useResetStore"
                docURL="https://marmelab.com/react-admin/useResetStore.html"
            >
                <Variant proto={UseResetStoreProto} />
            </Component>
            <Component
                name="useStoreContext"
                docURL="https://marmelab.com/react-admin/useStoreContext.html"
            >
                <Variant proto={UseStoreContextProto} />
            </Component>
            <Component
                name="Configurable"
                docURL="https://marmelab.com/react-admin/Configurable.html"
            >
                <Variant>
                    <Configurable editor={<div />} preferenceKey="componentName">
                        <div />
                    </Configurable>
                </Variant>
            </Component>
            <Component
                name="ToggleThemeButton"
                docURL="https://marmelab.com/react-admin/ToggleThemeButton.html"
            >
                <Variant>
                    <ToggleThemeButton darkTheme={{}} />
                </Variant>
            </Component>
        </Category>
        <Category name="I18n">
            <Component
                name="useTranslate"
                docURL="https://marmelab.com/react-admin/useTranslate.html"
            >
                <Variant proto={UseTranslateProto} />
            </Component>
            <Component
                name="useLocaleState"
                docURL="https://marmelab.com/react-admin/useLocaleState.html"
            >
                <Variant proto={UseLocaleStateProto} />
            </Component>
            <Component
                name="LocalesMenuButton"
                docURL="https://marmelab.com/react-admin/LocalesMenuButton.html"
            >
                <Variant>
                    <LocalesMenuButton />
                </Variant>
            </Component>
        </Category>
        <Category name="React Admin UI">
            <Component
                name="Layout"
                docURL="https://marmelab.com/react-admin/Layout.html"
            >
                <Variant>
                    <Layout />
                </Variant>
            </Component>
            <Component
                name="AppBar"
                docURL="https://marmelab.com/react-admin/AppBar.html"
            >
                <Variant>
                    <AppBar />
                </Variant>
            </Component>
            <Component
                name="Menu"
                docURL="https://marmelab.com/react-admin/Menu.html"
            >
                <Variant>
                    <Menu></Menu>
                </Variant>
                <Variant name="Menu.DashboardItem">
                    <Menu.DashboardItem />
                </Variant>
                <Variant name="Menu.ResourceItem">
                    <Menu.ResourceItem name="resourceName" />
                </Variant>
                <Variant name="Menu.Item">
                    <Menu.Item to="/custom-route" primaryText="CustomRoute" />
                </Variant>
            </Component>
            <Component
                name="Title"
                docURL="https://marmelab.com/react-admin/Title.html"
            >
                <Variant>
                    <Title title="my.custom.page.title" />
                </Variant>
                <Variant name="i18n">
                    <Title title="my.custom.page.title" />
                </Variant>
            </Component>
            <Component
                name="Confirm"
                docURL="https://marmelab.com/react-admin/Confirm.html"
            >
                <Variant proto={ConfirmProto} />
            </Component>
            <Component
                name="Buttons"
                docURL="https://marmelab.com/react-admin/Buttons.html"
            >
                <Variant name="EditButton">
                    <EditButton label="Edit" />
                </Variant>
                <Variant name="ShowButton">
                    <ShowButton label="Show" />
                </Variant>
                <Variant name="CreateButton">
                    <CreateButton label="Create" />
                </Variant>
                <Variant name="ListButton">
                    <ListButton label="List" />
                </Variant>
                <Variant name="ExportButton">
                    <ExportButton />
                </Variant>
                <Variant name="BulkExportButton">
                    <BulkExportButton />
                </Variant>
                <Variant name="BulkDeleteButton">
                    <BulkDeleteButton />
                </Variant>
                <Variant name="FilterButton">
                    <FilterButton />
                </Variant>
                <Variant name="DeleteButton">
                    <DeleteButton />
                </Variant>
                <Variant name="DeleteWithConfirmButton">
                    <DeleteWithConfirmButton />
                </Variant>
                <Variant name="CloneButton">
                    <CloneButton />
                </Variant>
            </Component>
        </Category>

    </Palette>
);

export function ExampleLoaderComponent() {
    return (
        <Fragment>Loading...</Fragment>
    );
}

// Data

const UseDataProviderProto = () => {
    const dataProvider = useDataProvider();
};

const UseGetListProto = () => {
    const [page, setPage] = useState(1);
    const {
        data,
        total,
        pageInfo = {},
        isLoading,
        error,
    } = useGetList("posts", {
        pagination: { page, perPage: 10 },
        sort: { field: "published_at", order: "DESC" },
    });
    if (isLoading) {
        return <Loading />;
    }
    if (error) {
        return <p>ERROR</p>;
    }
    const { hasNextPage, hasPreviousPage } = pageInfo;
};

const UseInfiniteGetListProto = () => {
    const {
        data,
        total,
        isLoading,
        error,
        hasNextPage,
        isFetchingNextPage,
        fetchNextPage,
    } = useInfiniteGetList("posts", {
        pagination: { page: 1, perPage: 10 },
        sort: { field: "published_at", order: "DESC" },
    });
    if (isLoading) {
        return <p>Loading</p>;
    }
    if (error) {
        return <p>ERROR</p>;
    }
};

const UseGetOneProto = () => {
    const record = useRecordContext();
    const {
        data: user,
        isLoading,
        error,
    } = useGetOne("users", { id: record.userId });
    if (isLoading) {
        return <Loading />;
    }
    if (error) {
        return <p>ERROR</p>;
    }
};

const UseGetManyProto = () => {
    const record = useRecordContext();
    const { data, isLoading, error } = useGetMany("tags", { ids: record.tagIds });
    if (isLoading) {
        return <Loading />;
    }
    if (error) {
        return <p>ERROR</p>;
    }
};

const UseGetManyReferenceProto = () => {
    const record = useRecordContext();
    // fetch all comments related to the current record
    const { data, isLoading, error } = useGetManyReference("comments", {
        target: "post_id",
        id: record.id,
        pagination: { page: 1, perPage: 10 },
        sort: { field: "published_at", order: "DESC" },
    });
    if (isLoading) {
        return <Loading />;
    }
    if (error) {
        return <p>ERROR</p>;
    }
};

const UseCreateProto = () => {
    const record = useRecordContext();
    const like = { postId: record.id };
    const [create, { isLoading, error }] = useCreate("likes", { data: like });
    const handleClick = () => {
        create();
    };
    if (error) {
        return <p>ERROR</p>;
    }
};

const UseUpdateProto = () => {
    const record = useRecordContext();
    const diff = { likes: record.likes + 1 };
    const [update, { isLoading, error }] = useUpdate("likes", {
        id: record.id,
        data: diff,
        previousData: record,
    });
    const handleClick = () => {
        update();
    };
    if (error) {
        return <p>ERROR</p>;
    }
};

const UseUpdateManyProto = () => {
    const { selectedIds } = useListContext();
    const [updateMany, { isLoading, error }] = useUpdateMany("posts", {
        ids: selectedIds,
        data: { views: 0 },
    });
    const handleClick = () => {
        updateMany();
    };
    if (error) {
        return <p>ERROR</p>;
    }
};

const UseDeleteProto = () => {
    const record = useRecordContext();
    const [deleteOne, { isLoading, error }] = useDelete("likes", {
        id: record.id,
        previousData: record,
    });
    const handleClick = () => {
        deleteOne();
    };
    if (error) {
        return <p>ERROR</p>;
    }
};

const UseDeleteManyProto = () => {
    const [deleteMany, { isLoading, error }] = useDeleteMany("posts", {
        ids: [],
    });
    const handleClick = () => {
        deleteMany();
    };
    if (error) {
        return <p>ERROR</p>;
    }
};

// Auth

const UseAuthProviderProto = () => {
    const authProvider = useAuthProvider();
};

const UseAuthenticatedProto = () => {
    useAuthenticated();
};

const UseAuthStateProto = () => {
    const { isLoading, authenticated } = useAuthState();
};

const UseGetIdentityProto = () => {
    const { data, isLoading, error } = useGetIdentity();
};

const UseLoginProto = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const login = useLogin();

    const handleSubmit = () => {
        // will call authProvider.login({ email, password })
        login({ email, password }).catch(() =>
            console.error("Invalid email or password")
        );
    };
};

const UseLogoutProto = () => {
    const logout = useLogout();
};

const UsePermissionsProto = () => {
    const { isLoading, permissions } = usePermissions();
};

// List

const UseListProto = () => {
    const { data, isLoading } = useGetList("recordName", {
        pagination: { page: 1, perPage: 10 },
    });
    const listContext = useList({ data, isLoading });
};

const UseListContextProto = () => {
    const { data, isLoading } = useListContext();
};

const UseListControllerProto = () => {
    const listContext = useListController();
};

const UseUnselectProto = () => {
    const { resource, selectedIds } = useListContext();
    const unselect = useUnselect(resource);

    const handleClick = () => {
        unselect(selectedIds);
    };
};

const UseUnselectAllProto = () => {
    const unselectAll = useUnselectAll("recordName");
};

// Edition & Creation

const UseCreateContextProto = () => {
    const { save } = useCreateContext();
};

const UseCreateControllerProto = () => {
    const { save } = useCreateController({ resource: "recordName" });
};

const UseEditContextProto = () => {
    const { record, isLoading } = useEditContext();
};

const UseEditControllerProto = () => {
    const { id } = useParams();
    const { record, save, isLoading } = useEditController({
        resource: "recordName",
        id,
    });
};

const UseSaveContextProto = () => {
    const { save, saving, mutationMode } = useSaveContext();
};

// Show

const UseShowContextProto = () => {
    const { defaultTitle, error, isLoading, record } = useShowContext();
};

const UseShowControllerProto = () => {
    const { defaultTitle, error, isLoading, record } = useShowController();
};

// Common

const UseRecordContextProto = () => {
    const record = useRecordContext();
};

const UseGetRecordIdProto = () => {
    const recordId = useGetRecordId();
};

const UseNotifyProto = () => {
    const notify = useNotify();
    const handleClick = () => {
        notify(`Comment approved`, { type: "success" });
    };
};

const UseRedirectProto = () => {
    const redirect = useRedirect();
};

const UseRefreshProto = () => {
    const refresh = useRefresh();
};

// Inputs

const UseInputProto = () => {
    const { id, field, fieldState } = useInput({ source: "sourceName" });
};

// Preferences

const UseStoreProto = () => {
    const [value, setValue] = useStore("key", "defaultValue");
};

const UseRemoveFromStoreProto = () => {
    const remove = useRemoveFromStore();
    remove("key");
};

const UseResetStoreProto = () => {
    const reset = useResetStore();
    reset();
};

const UseStoreContextProto = () => {
    const store = useStoreContext();
};

// i18n

const UseTranslateProto = () => {
    const translate = useTranslate();
    const translatedMessage = translate("translationKey", {});
};

const UseLocaleStateProto = () => {
    const [locale, setLocale] = useLocaleState();
};

// Other UI

const ConfirmProto = () => {
    const [open, setOpen] = useState(false);

    const handleClick = () => setOpen(true);
    const handleDialogClose = () => setOpen(false);
    const handleConfirm = () => {
        setOpen(false);
    };

    return (
        <>
            <Button label="Click" onClick={handleClick} />
            <Confirm
                isOpen={open}
                title="Title"
                content="Content"
                onConfirm={handleConfirm}
                onClose={handleDialogClose}
            />
        </>
    );
};
