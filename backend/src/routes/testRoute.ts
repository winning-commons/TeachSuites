import { Router } from 'express';
import { sayHello } from '../controllers/testController';

const router = Router();

router.get('/', sayHello);

export default router;
