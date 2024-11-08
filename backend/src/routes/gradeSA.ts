import { Router } from "express";
import { gradeSA } from "../controllers/gradeSAController";

const router = Router();

router.post("/", gradeSA);

export default router;
