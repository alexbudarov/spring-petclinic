export function ownerRecordRepresentation(owner: any) {
    return (owner?.firstName ?? "") + " " + (owner?.lastName ?? "");
}
