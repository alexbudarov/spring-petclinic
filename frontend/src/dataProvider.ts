import { Dayjs } from "dayjs";
import simpleRestProvider from "ra-data-simple-rest";
import { DataProvider, fetchUtils } from "react-admin";

const baseDataProvider = simpleRestProvider(
  import.meta.env.VITE_SIMPLE_REST_URL
);

const apiUrl = import.meta.env.VITE_SIMPLE_REST_URL;
const httpClient = fetchUtils.fetchJson;

export interface CustomDataProvider extends DataProvider {
  checkAvailability: (specialtyId: number, date: Dayjs) => Promise<boolean>;
    // custom endpoints 
}

export const dataProvider: CustomDataProvider = {
    ...baseDataProvider,

    checkAvailability: function (specialtyId: number, date: Dayjs): Promise<boolean> {
      return httpClient(`/rest/visit/check-availability?specialtyId=${specialtyId}&date=${formatDate(date)}`)
        .then(({ json }) => (json));
    }

    // custom endpoints
};

function formatDate(date: Dayjs) {
  return date.format("YYYY-MM-DD");
}
