export function ownerRecordRepresentation(owner: any) {
    if (!owner) {
        return "";
    }
    return (owner?.firstName ?? "") + " " + (owner?.lastName ?? "");
}
