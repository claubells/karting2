import axios from 'axios';

const host = import.meta.env.VITE_API_BACKEND_SERVER;
const port = import.meta.env.VITE_API_BACKEND_PORT;

export default axios.create({
  baseURL: `http://${host}:${port}/api`,
  headers: { 'Content-Type': 'application/json' }
});