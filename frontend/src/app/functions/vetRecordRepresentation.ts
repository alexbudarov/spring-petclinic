export function vetRecordRepresentation(vet: any) {
    if (!vet) {
        return "";
    }
    return (vet?.firstName ?? "") + " " + (vet?.lastName ?? "");
}
