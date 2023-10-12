import { RedirectionSideEffect } from "react-admin";

export const redirectFromPetToOwner: RedirectionSideEffect = (resource, id, data) => {
  const ownerId = data?.ownerId
  if (ownerId) {
    return 'owner/' + ownerId + '/show';
  }
  return 'owner'
}