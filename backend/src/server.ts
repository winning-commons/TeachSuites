import express, { Application } from "express";
import https from "https";
import fs from "fs";
import dotenv from "dotenv";
import testRoute from "./routes/testRoute";
import gradeSARoute from "./routes/gradeSA";
import createMC from "./routes/createMC";
import makeClass from "./routes/makeClass";
import { getClasses } from "./controllers/getClassesController";

dotenv.config();

const app: Application = express();
const PORT = process.env.NEW_BACKEND_PORT || 8443; // Use a different port for this backend

app.use(express.json());

// Define your routes
app.use("/api/hello", testRoute);
app.use("/api/grade-sa", gradeSARoute);
app.use("/api/create-mc", createMC);
app.use("/api/get-classes", getClasses);
app.use("/api/make-class", makeClass);

// Load SSL/TLS certificate files
const privateKey = fs.readFileSync("privkey.pem"); // Update with your key path
const certificate = fs.readFileSync("fullchain.pem"); // Update with your certificate path

const credentials = { key: privateKey, cert: certificate };

// Create HTTPS server with SSL certificates
const httpsServer = https.createServer(credentials, app);

// Listen on the specified port
httpsServer.listen(PORT, () => {
  console.log(`New HTTPS Server running on https://localhost:${PORT}`);
});
