import axios from 'axios';

const host = import.meta.env.VITE_RACK_BACKEND_HOST;
const port = import.meta.env.VITE_RACK_BACKEND_PORT;

export default axios.create({
  baseURL: `http://${host}:${port}/api/rack`,
  headers: { 'Content-Type': 'application/json' }
});