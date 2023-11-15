import { User } from "oidc-client-ts";
import { PropsWithChildren, useCallback } from "react";
import { AuthProvider } from "react-oidc-context";
import { resolvePath, useLocation, useNavigate } from "react-router-dom";
import { ID_TOKEN_STORAGE_KEY, oidcConfig } from "./oidcConfig";

export function OidcKeycloakAuthProvider({ children }: PropsWithChildren) {
  const location = useLocation();
  const returnPath = encodeURIComponent(location.pathname + location.search + location.hash);
  const authPath = resolvePath("auth").pathname; // TODO `auth` is hardcoded
  const redirect_uri = `${window.location.origin}${authPath}?return_path=${returnPath}`;
  // console.log(`OidcKeycloakAuthProvider redirect_uri '${redirect_uri}'`);

  const navigate = useNavigate();
  const onSigninCallback = useCallback(
    async (user: void | User) => {
      if (user == null || user?.id_token == null) {
        throw new Error(`onSigninCallback: id token not found for user ${user}`);
      }
      // save token
      await localStorage.setItem(ID_TOKEN_STORAGE_KEY, user.id_token);
      console.log(
        `onSigninCallback: save token to local storage with key '${ID_TOKEN_STORAGE_KEY}', navigate to "/"`
      );
      navigate("/");
    },
    [navigate]
  );

  return (
    <AuthProvider {...{ ...oidcConfig, onSigninCallback, redirect_uri }}>{children}</AuthProvider>
  );
}
