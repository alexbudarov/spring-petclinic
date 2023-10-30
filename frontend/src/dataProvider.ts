import simpleRestProvider from "ra-data-simple-rest";
import { DataProvider, RaRecord, fetchUtils } from "react-admin";

const inheritedDataProvider = simpleRestProvider(
  import.meta.env.VITE_SIMPLE_REST_URL
);

const apiUrl = import.meta.env.VITE_SIMPLE_REST_URL;
const httpClient = fetchUtils.fetchJson;

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
