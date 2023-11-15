interface OidcConfig {
    authority: string;
    client_id: string;
    scope: string;
  }
  
  export const oidcConfig: OidcConfig = {
    authority: import.meta.env.VITE_OIDC_CONFIG_AUTHORITY as string,
    client_id: import.meta.env.VITE_OIDC_CONFIG_CLIENT_ID as string,
    scope: import.meta.env.VITE_OIDC_CONFIG_SCOPE as string,
  };
  
  export const ID_TOKEN_STORAGE_KEY = `oidc.user:${oidcConfig.authority}:${oidcConfig.client_id}`;
  