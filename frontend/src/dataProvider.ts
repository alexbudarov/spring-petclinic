import simpleRestProvider from "ra-data-simple-rest";
import { DataProvider, Options, RaRecord, fetchUtils } from "react-admin";
import { oidcConfig } from "./core/security/oidcConfig";

const apiUrl = import.meta.env.VITE_SIMPLE_REST_URL;
//const httpClient = fetchUtils.fetchJson;

const httpClient = (url: string, options: Options = {}) => {
    const token = localStorage.getItem(`oidc.user:${oidcConfig.authority}:${oidcConfig.client_id}`);
    // console.log("JWT token: " + token);
    const optionsWithToken: Options = {
        ...options,
        user: {
            authenticated: token !== null && token !== undefined,
            token: token ? `Bearer ${token}` : ""
        }
    }
    return fetchUtils.fetchJson(url, optionsWithToken)
}

const inheritedDataProvider = simpleRestProvider(
    import.meta.env.VITE_SIMPLE_REST_URL,
    httpClient
);

export interface CustomDataProvider extends DataProvider {
    vetsBySpecialties: (ids: number[]) => Promise<RaRecord[]>;
}

export const dataProvider: CustomDataProvider = {
    ...inheritedDataProvider,

    vetsBySpecialties: (ids: number[]): Promise<RaRecord[]> => {
        return httpClient(`${apiUrl}/vet/byspec`, {
            method: 'POST',
            body: JSON.stringify(ids),
        }).then(({ json }) => (json));
    }
};
