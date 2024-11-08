import { Router } from "express";
import { createMC } from "../controllers/createMCController";

const router = Router();

router.post("/", createMC);

export default router;
