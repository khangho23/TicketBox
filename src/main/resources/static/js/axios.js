export const fetch = axios.create({
    baseURL: "https://test.zuhot-api.id.vn/api",
    headers: { 'zuhot-key': 'abc123456' }
})