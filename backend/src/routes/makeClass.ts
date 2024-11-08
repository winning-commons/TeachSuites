import { Router } from "express";
import { makeClass } from "../controllers/makeClassController";

const router = Router();

router.post("/", makeClass);

export default router;
