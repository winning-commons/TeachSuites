import express, { Application } from 'express';
import testRoute from './routes/testRoute';
import gradeSARoute from './routes/gradeSA';
import dotenv from 'dotenv';

dotenv.config();

const app: Application = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());

app.use('/api/hello', testRoute);
app.use('/api/grade-sa', gradeSARoute);

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
