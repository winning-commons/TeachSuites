import { Router } from 'express';
import { gradeSAInput } from '../controllers/gradeSAController';

const router = Router();

router.post('/', gradeSAInput);

export default router;
