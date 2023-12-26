import { stringify } from 'query-string';
import { DataProvider, fetchUtils } from "react-admin";

export default (
    apiUrl: string,
    httpClient = fetchUtils.fetchJson
): DataProvider => ({
    getList: (resource, params) => {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;

        const query = {
            sort: field + "," + order,
            page: page - 1,
            size: perPage,
            ...params.filter
        };
        const url = `${apiUrl}/${resource}?${stringify(query)}`;
        return httpClient(url).then(({ json }) => {
            return {
                data: json.content,
                total: json.totalElements
            };
        });
    },

    getOne: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`).then(({ json }) => ({
            data: json,
        })),

    getMany: (resource, params) => {
        const ids = params.ids.join(",");
        const url = `${apiUrl}/${resource}/by-ids?ids=${ids}`;
        return httpClient(url).then(({ json }) => ({ data: json }));
    },

    getManyReference: (resource, params) => {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;

        const query = {
            sort: field + "," + order,
            page: page - 1,
            size: perPage,
            ...params.filter,
            [params.target]: params.id
        };
        const url = `${apiUrl}/${resource}?${stringify(query)}`;
        return httpClient(url).then(({ json }) => {
            return {
                data: json.content,
                total: json.totalElements
            };
        });
    },

    update: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`, {
            method: 'PUT',
            body: JSON.stringify(params.data),
        }).then(({ json }) => ({ data: json })),

    updateMany: (resource, params) => {
        const idsValue = params.ids.join(",");
        return httpClient(`${apiUrl}/${resource}?ids=${idsValue}`, {
            method: 'PUT',
            body: JSON.stringify(params.data),
        }).then(({ json }) => ({ data: json }))
    },

    create: (resource, params) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'POST',
            body: JSON.stringify(params.data),
        }).then(({ json }) => ({ data: json })),

    delete: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'text/plain',
            }),
        }).then(({ json }) => ({ data: json })),

    deleteMany: (resource, params) => {
        const idsValue = params.ids.join(",");
        return httpClient(`${apiUrl}/${resource}?ids=${idsValue}`, {
            method: 'DELETE'
        }).then(({ json }) => ({ data: json }))
    }
});
