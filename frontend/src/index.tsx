import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { App } from "./App";
import { OidcKeycloakAuthProvider } from "./core/security/OidcKeycloakAuthProvider";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <BrowserRouter>
      <OidcKeycloakAuthProvider>
        <App />
      </OidcKeycloakAuthProvider>
    </BrowserRouter>
  </React.StrictMode>
);
