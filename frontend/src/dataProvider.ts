import simpleRestProvider from "ra-data-simple-rest";
import { DataProvider, fetchUtils } from "react-admin";

const baseDataProvider = simpleRestProvider(
  import.meta.env.VITE_SIMPLE_REST_URL
);

const apiUrl = import.meta.env.VITE_SIMPLE_REST_URL;
const httpClient = fetchUtils.fetchJson;

export interface CustomDataProvider extends DataProvider {
    // custom endpoints 
}

export const dataProvider: CustomDataProvider = {
    ...baseDataProvider,

    // custom endpoints
};
