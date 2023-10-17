export class DateUtil {
    static formatDate = (value) => {
        let d = new Date(value);
        return ("0" + d.getDate()).slice(-2) + "/" + ("0" + (d.getMonth() + 1)).slice(-2) + "/" +
            d.getFullYear();
    }
}