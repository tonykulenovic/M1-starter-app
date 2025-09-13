import { Router } from 'express';

import { authenticateToken } from '../middlewares/auth.middleware';
import authRoutes from '../routes/auth.routes';
import hobbiesRoutes from '../routes/hobbies.routes';
import mediaRoutes from '../routes/media.routes';
import usersRoutes from '../routes/user.routes';

const router = Router();

router.use('/auth', authRoutes);

router.use('/hobbies', authenticateToken, hobbiesRoutes);

router.use('/user', authenticateToken, usersRoutes);

router.use('/media', authenticateToken, mediaRoutes);

export default router;
