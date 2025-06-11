import axios from 'axios';

const host = import.meta.env.VITE_API_GATEWAY_HOST;
const port = import.meta.env.VITE_API_GATEWAY_PORT;

export default axios.create({
  baseURL: `http://${host}:${port}/api/rack`,
  headers: { 'Content-Type': 'application/json' }
});