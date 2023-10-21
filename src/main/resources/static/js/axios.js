export const fetch = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: { 'zuhot-key': 'abc123456' }
})