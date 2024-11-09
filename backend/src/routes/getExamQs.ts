// src/routes/getExams.ts
import { Router } from "express";
import { getExamQs } from "../controllers/getExamQsController";

const router = Router();

router.post("/", getExamQs);

export default router;
