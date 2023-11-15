import { useEffect, useState } from "react";
import { AuthProvider } from "react-admin";
import { useAuth } from "react-oidc-context";
import { oidcKeycloakAuthProvider } from "./oidcKeycloakAuthProvider";

type UseAuthProvider = {
  loading: boolean;
  authProvider?: AuthProvider;
};

export function useAuthProvider(): UseAuthProvider {
  const auth = useAuth();
  const [authProvider, setAuthProvider] = useState<AuthProvider>();

  useEffect(() => {
    setAuthProvider(oidcKeycloakAuthProvider(auth));
  }, [auth]);

  return {
    authProvider,
    loading: auth.isLoading,
  };
}
