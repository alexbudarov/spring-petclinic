import { Dayjs } from "dayjs";
import { DataProvider, fetchUtils } from "react-admin";
import springDataProvider from "./springDataProvider";

const baseDataProvider = springDataProvider(
  import.meta.env.VITE_SIMPLE_REST_URL
);

const httpClient = fetchUtils.fetchJson;

export interface CustomDataProvider extends DataProvider {
  checkAvailability: (specialtyId: number, date: Dayjs) => Promise<boolean>;
  requestVisit: (request: NewVisitRequest) => Promise<RequestVisitResponse>;
    // custom endpoints 
}

type NewVisitRequest = {
  petId: number;
  specialtyId: number;
  date: string;
  description: string;
}

type RequestVisitResponse = {
  success: boolean;
  visitId: number | null;
  errorMessage: string | null;
}

export const dataProvider: CustomDataProvider = {
  ...baseDataProvider,

    checkAvailability: function (specialtyId: number, date: Dayjs): Promise<boolean> {
      return httpClient(`/rest/visit/check-availability?specialtyId=${specialtyId}&date=${formatDate(date)}`)
        .then(({ json }) => (json));
    },

    requestVisit: function (request: NewVisitRequest): Promise<RequestVisitResponse> {
      return httpClient(`/rest/visit/request`, {
          method: "POST",
          body: JSON.stringify(request)
      })
      .then(({ json }) => (json));
    }

    // custom endpoints
};

function formatDate(date: Dayjs) {
  return date.format("YYYY-MM-DD");
}
