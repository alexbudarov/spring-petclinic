import simpleRestProvider from "ra-data-simple-rest";
import { DataProvider, fetchUtils } from "react-admin";

const baseDataProvider = simpleRestProvider(
  import.meta.env.VITE_SIMPLE_REST_URL
);

const apiUrl = import.meta.env.VITE_SIMPLE_REST_URL;
const httpClient = fetchUtils.fetchJson;

export interface CheckAvailabilityArguments {
  vetId: number, 
  date: Date
}
export interface CustomDataProvider extends DataProvider {
    checkAvailability: (args: CheckAvailabilityArguments) => Promise<boolean>;
    // custom endpoints 
}

export const dataProvider: CustomDataProvider = {
  ...baseDataProvider,

  checkAvailability: function (args: CheckAvailabilityArguments): Promise<boolean> {
    return httpClient(`${apiUrl}/visit/check-availability?vetId=${args.vetId}&date=${formatDate(args.date)}`)
      .then(({ json }) => (json));
  }
};

function formatDate(date: Date) {
  const isoString = date.toISOString();
  return isoString.substring(0, isoString.indexOf('T'));
}
