import React from "react";
import ReactDOM from "react-dom/client";
import { App } from "./App";
import {DevSupport} from "@amplicode/ide-toolbox";
import {ComponentPreviews, useInitial} from "./dev";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <DevSupport ComponentPreviews={ComponentPreviews}
                    useInitialHook={useInitial}
        >
      <App />
    </DevSupport>
  </React.StrictMode>
);
