import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import basicSsl from "@vitejs/plugin-basic-ssl";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    basicSsl({
      /** name of certification */
      name: "elevators",
      /** custom trust domains */
      domains: ["*.reptilianeye.com"],
      /** custom certification directory */
      certDir: "cert",
    }),
  ],
  server: {
    open: true,
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        // rewrite: (path: string) => path.replace(/^\/api/, ""),
      },
    },
  },
});
