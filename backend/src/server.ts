import express, { Application } from "express";
import dotenv from "dotenv";
import testRoute from "./routes/testRoute";
import gradeSARoute from "./routes/gradeSA";
import createMC from "./routes/createMC";
import makeClass from "./routes/makeClass";

import { getClasses } from "./controllers/getClassesController";
import getStudentClasses from "./routes/getStudentClasses";

dotenv.config();

const app: Application = express();
const PORT = 8443;

app.use(express.json());

app.use("/api/hello", testRoute);
app.use("/api/grade-sa", gradeSARoute);
app.use("/api/create-mc", createMC);
app.use("/api/get-classes", getClasses);
app.use("/api/make-class", makeClass);
app.use("/api/get-student-classes", getStudentClasses);

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
