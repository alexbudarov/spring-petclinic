import { Dayjs } from "dayjs";
import simpleRestProvider from "ra-data-simple-rest";
import { DataProvider, fetchUtils } from "react-admin";

const baseDataProvider = simpleRestProvider(
  import.meta.env.VITE_SIMPLE_REST_URL
);

const apiUrl = import.meta.env.VITE_SIMPLE_REST_URL;
export const httpClient = fetchUtils.fetchJson;

export interface CustomDataProvider extends DataProvider {
    checkAvailability: (vetId: number, date: Dayjs) => Promise<boolean>;
    // custom endpoints 
}

export const dataProvider: CustomDataProvider = {
  ...baseDataProvider,

  checkAvailability: function (vetId: number, date: Dayjs): Promise<boolean> {
    return httpClient(`/rest/visit/check-availability?vetId=${vetId}&date=${formatDate(date)}`)
      .then(({ json }) => (json));
  }
};

function formatDate(date: Dayjs) {
  return date.format("YYYY-MM-DD");
}
