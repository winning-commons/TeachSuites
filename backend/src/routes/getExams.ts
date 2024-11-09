// src/routes/getExams.ts
import { Router } from "express";
import { getExams } from "../controllers/getExamsController";

const router = Router();

router.post("/", getExams);

export default router;
