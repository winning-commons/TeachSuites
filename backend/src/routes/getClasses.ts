import { Router } from "express";
import { getClasses } from "../controllers/getClassesController";

const router = Router();

router.post("/", getClasses);

export default router;
