export const fetch = axios.create({
    baseURL: "/api",
    headers: { 'zuhot-key': 'abc123456' }
})