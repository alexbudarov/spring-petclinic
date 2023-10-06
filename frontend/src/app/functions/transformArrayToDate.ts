
// convert [2013,1,2] to js Date
export const transformArrayToDate = (value: any) => {
    const array: Array<number> = value;
    const year = array[0];
    const month = array[1];
    const day = array[2];
    return new Date(year, month - 1, day);
}
