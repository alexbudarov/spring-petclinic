import { AuthProvider } from "react-admin";
import { AuthContextProps } from "react-oidc-context";
import { ID_TOKEN_STORAGE_KEY } from "../core/security/oidcConfig";

export const oidcKeycloakAuthProvider = (auth: AuthContextProps): AuthProvider => ({
  login: () => {
    // console.log("login");
    return auth.signinRedirect();
  },
  logout: async () => {
    // console.log("logout");
    const post_logout_redirect_uri = window.location.href;
    await localStorage.removeItem(ID_TOKEN_STORAGE_KEY);
    await auth.signoutRedirect({ post_logout_redirect_uri });
  },
  checkError: async (error) => {
    // console.log("checkError: " + Object.keys(error));

    const status = error.status;
    if (status === 401) {
        // console.log("Error 401. Going to logout");
        await localStorage.removeItem(ID_TOKEN_STORAGE_KEY);
        return Promise.reject();
    }
    // other error code (404, 500, etc): no need to log out
    return Promise.resolve();
  },
  checkAuth: async () => {
    const token = localStorage.getItem(ID_TOKEN_STORAGE_KEY);
    // console.log("checkauth. Token: " + token);
    if (token !== null) {
      return Promise.resolve();
    }

    await localStorage.removeItem(ID_TOKEN_STORAGE_KEY);
    await auth.signinRedirect();
  },
  getPermissions: () => {
    const token = localStorage.getItem(ID_TOKEN_STORAGE_KEY);
    // console.log("getPermissions. Token: " + token);

    if (!token) {
      return Promise.reject();
    }
    return Promise.resolve();
  },
  getUserIdentity: () => {
    const token = localStorage.getItem(ID_TOKEN_STORAGE_KEY);
    // console.log("getUserIdentity. Token: " + token);

    if (token !== null) {
      return Promise.resolve({ id: 1 });
    }
    return Promise.reject("Failed to get identity.");
  },
});
