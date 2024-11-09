import { Router } from "express";
import { getStudentClasses } from "../controllers/getStudentClassesController";

const router = Router();

router.post("/", getStudentClasses);

export default router;
